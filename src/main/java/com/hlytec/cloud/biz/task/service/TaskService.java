package com.hlytec.cloud.biz.task.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.task.mapper.TaskMapper;
import com.hlytec.cloud.biz.task.model.entity.Task;
import com.hlytec.cloud.biz.task.model.vo.QueryTaskVo;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.common.service.BaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: TaskService
 * @author: zero
 * @date: 2021/6/2 17:52
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TaskService extends BaseService<TaskMapper, Task> {

    private final ConcurrentHashMap<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public PageResult<Task> findTasKPage(QueryTaskVo queryTaskVo) {
        Page<Task> page = new Page<>(queryTaskVo.getPageNo(), queryTaskVo.getPageSize());
        QueryWrapper<Task> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryTaskVo.getTaskName())) {
            queryWrapper.like("name", queryTaskVo.getTaskName());
        }
        if (Objects.nonNull(queryTaskVo.getStatus())) {
            queryWrapper.eq("status", queryTaskVo.getStatus());
        }
        queryWrapper.orderByDesc("create_time");
        Page<Task> taskPage = taskMapper.selectPage(page, queryWrapper);
        PageResult<Task> result = new PageResult<>(taskPage);
        return result;
    }

    @Transactional(readOnly = false)
    public void startTask(String taskId) {
        Task task = get(taskId);
        if (Objects.nonNull(task)) {
            String invokeTarget = task.getInvokeTarget();
            String cron = task.getExpression();
            try {
                updateTaskStatus(task, cron, 1);
                Class<?> target = Class.forName(invokeTarget);
                Object o = target.newInstance();
                // 约定执行类中只能有一个方法
                Method method = target.getDeclaredMethods()[0];
                ScheduledFuture<?> schedule = threadPoolTaskScheduler.schedule(() -> {
                    try {
                        //执行任务
                        method.invoke(o);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        updateTaskStatus(task, cron, 0);
                        log.error("执行定时任务发生异常：{}", e.getMessage());
                    }
                }, new Trigger() {
                    @Override
                    public Date nextExecutionTime(TriggerContext triggerContext) {
                        return new CronTrigger(cron).nextExecutionTime(triggerContext);
                    }
                });
                // 加入执行容器
                tasks.put(taskId, schedule);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                log.error("获取定时任务执行类发生异常：{}", e.getMessage());
                throw new NetServiceException(ResultEnum.INVOKE_TARGET_INVALIDATE);
            }
        }
    }

    private void updateTaskStatus(Task task, String cron, int status) {
        task.setLastExecTime(new Date());
        task.setStatus(status);
        //刷新下次执行时间
        refreshNextExecTime(task, cron);
        taskMapper.updateById(task);
    }

    @Transactional(readOnly = false)
    public void stopTask(String taskId) {
        Task task = get(taskId);
        String cron = task.getExpression();
        ScheduledFuture<?> scheduledFuture = tasks.get(taskId);
        if (Objects.nonNull(scheduledFuture)) {
            scheduledFuture.cancel(true);
        }
        refreshNextExecTime(task, cron);
        task.setStatus(0);
        taskMapper.updateById(task);
    }

    private void refreshNextExecTime(Task task, String cron) {
        //刷新下次执行时间
        CronExpression parse = CronExpression.parse(cron);
        LocalDateTime next = parse.next(LocalDateTime.now());
        if (Objects.nonNull(next)) {
            Instant instant = next.atZone(ZoneId.systemDefault()).toInstant();
            Date nextDate = Date.from(instant);
            task.setNextExecTime(nextDate);
        } else {
            log.error("任务定时表达式错误！");
        }
    }
}
