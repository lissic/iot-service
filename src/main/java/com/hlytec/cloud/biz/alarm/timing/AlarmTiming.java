package com.hlytec.cloud.biz.alarm.timing;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cn.hutool.json.JSONUtil;
import com.hlytec.cloud.biz.report.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hlytec.cloud.biz.alarm.model.entity.Alarm;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmItem;
import com.hlytec.cloud.biz.alarm.model.entity.AlarmStrategy;
import com.hlytec.cloud.biz.alarm.service.AlarmItemService;
import com.hlytec.cloud.biz.alarm.service.AlarmService;
import com.hlytec.cloud.biz.alarm.service.AlarmStrategyService;
import com.hlytec.cloud.biz.alarm.thread.SendAlarmThread;
import com.hlytec.cloud.biz.report.model.po.CpuInfo;
import com.hlytec.cloud.biz.report.model.po.JvmInfo;
import com.hlytec.cloud.biz.report.model.po.MemInfo;
import com.hlytec.cloud.message.rabbitmq.client.Sender;
import com.hlytec.cloud.message.rabbitmq.config.RabbitConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: AlarmTiming
 * @author: zero
 * @date: 2021/6/23 13:33
 */
@Slf4j
@Component
@EnableScheduling
@EnableAsync
public class AlarmTiming {
    /**
     * 异步线程集合: 时间间隔类型
     */
    private final ConcurrentHashMap<String, ScheduledFuture<?>> threadList = new ConcurrentHashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private AlarmStrategyService alarmStrategy;

    @Autowired
    private AlarmItemService alarmItemService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private ServerInfoService serverInfoService;

    @Autowired
    private Sender sender;

    @Async
    @Scheduled(fixedDelay = 30 * 1000)
    public void getJvmAlarm() {
        // 告警项编码可在配置文件中配置
        AlarmItem alarmItem = alarmItemService.get(AlarmItem.builder().itemCode("3001").build());
        if (Objects.nonNull(alarmItem)) {
            JvmInfo jvmInfo = serverInfoService.getJvmInfo();
            saveAndSendAlarm(alarmItem, jvmInfo.getUsage());
        }
    }

    @Async
    @Scheduled(fixedDelay = 40 * 1000)
    public void getCpuAlarm() {
        // 告警项编码可在配置文件中配置
        AlarmItem alarmItem = alarmItemService.get(AlarmItem.builder().itemCode("3002").build());
        if (Objects.nonNull(alarmItem)) {
            CpuInfo cpuInfo = serverInfoService.getCpuInfo();
            saveAndSendAlarm(alarmItem, cpuInfo.getUsed());
        }
    }

    @Async
    @Scheduled(fixedDelay = 50 * 1000)
    public void getMemAlarm() {
        // 告警项编码可在配置文件中配置
        AlarmItem alarmItem = alarmItemService.get(AlarmItem.builder().itemCode("3003").build());
        if (Objects.nonNull(alarmItem)) {
            MemInfo memInfo = serverInfoService.getMemInfo();
            saveAndSendAlarm(alarmItem, memInfo.getUsage());
        }
    }

    @Async
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void stopSendAlarm() {
        // 查询所有已处理的告警
        List<Alarm> handledList = alarmService.findList(Alarm.builder().status(1).build());
        handledList.forEach(alarm -> {
            ScheduledFuture<?> thread = threadList.get(alarm.getAlarmObject());
            if (Objects.nonNull(thread)) {
                thread.cancel(true);
                threadList.remove(alarm.getAlarmObject());
            }
        });
    }

    private synchronized void saveAndSendAlarm(AlarmItem alarmItem, double used) {
        if (used > alarmItem.getThresholdVal()) {
            // 查找关联的告警策略
            List<AlarmStrategy> strategyList = alarmStrategy.getStrategyByItem(alarmItem.getId());
            // 创建告警信息
            strategyList.forEach(strategy -> {
                if (strategy.getStatus()) {
                    Alarm alarm = Alarm.builder().alarmObject(alarmItem.getItemName())
                            .alarmStrategyId(strategy.getId()).alarmItemId(alarmItem.getId())
                            .alarmStrategyName(strategy.getName()).alarmVal(used + "%").alarmTime(new Date()).alarmType(0)
                            .deviceId(alarmItem.getItemCode()).status(0).build();
                    alarmService.save(alarm);
                    JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(alarm));
                    List<String> alarmMethods = Arrays.asList(strategy.getAlarmMethod().split(","));
                    jsonObject.put("alarmMethod", alarmMethods);
                    // 重复次数
                    if (!alarmMethods.contains("0") && strategy.getAlarmRule() == 0) {
                        for (int i = 0; i < strategy.getAlarmRepeatNum(); i++) {
                            try {
                                threadPoolTaskScheduler
                                    .execute(new SendAlarmThread(sender, RabbitConfig.DEFAULT_EXCHANGE,
                                        RabbitConfig.SYSTEM_ALARM, jsonObject.toJSONString()));
                                if (strategy.getAlarmRepeatNum() > 1) {
                                    TimeUnit.SECONDS.sleep(10);
                                }
                            } catch (InterruptedException e) {
                                log.error(e.getMessage());
                            }
                        }
                    }
                    // 时间间隔
                    if (!alarmMethods.contains("0") && strategy.getAlarmRule() != 0) {
                        ScheduledFuture<?> scheduledFuture =
                                threadPoolTaskScheduler.scheduleAtFixedRate(
                                    new SendAlarmThread(sender, RabbitConfig.DEFAULT_EXCHANGE,
                                        RabbitConfig.SYSTEM_ALARM, jsonObject.toJSONString()),
                                    strategy.getAlarmRepeatTime() * 60 * 1000);
                        threadList.put(alarmItem.getItemName(),scheduledFuture);
                    }
                }
            });
        }
    }
}
