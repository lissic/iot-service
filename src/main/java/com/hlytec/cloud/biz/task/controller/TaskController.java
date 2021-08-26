package com.hlytec.cloud.biz.task.controller;

import com.hlytec.cloud.common.entity.CommonParamVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hlytec.cloud.biz.task.model.entity.Task;
import com.hlytec.cloud.biz.task.model.vo.QueryTaskVo;
import com.hlytec.cloud.biz.task.service.TaskService;
import com.hlytec.cloud.common.controller.BaseController;
import com.hlytec.cloud.common.result.CommonResult;
import com.hlytec.cloud.common.result.PageResult;

/**
 * @description: TaskController
 * @author: zero
 * @date: 2021/6/2 17:46
 */
@RestController
@RequestMapping("/net/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/save")
    public CommonResult<Object> saveTask(@RequestBody @Validated Task task) {
        String taskId = taskService.save(task);
        return success(taskId);
    }

    @DeleteMapping("/delete")
    public CommonResult<Object> deleteTask(@RequestBody CommonParamVo deleteTaskVo) {
        taskService.batchDelete(deleteTaskVo.getIds());
        return success();
    }

    @PutMapping("/update")
    public CommonResult<Object> updateTask(@RequestBody Task task) {
        if (StringUtils.isNotEmpty(task.getId())) {
            taskService.updateById(task);
            return success();
        } else {
            return fail("任务ID不能为空.");
        }
    }

    @PostMapping("/page")
    public CommonResult<Object> findPage(@RequestBody QueryTaskVo queryTaskVo) {
        PageResult<Task> result = taskService.findTasKPage(queryTaskVo);
        return success(result);
    }

    @GetMapping("/{taskId}")
    public CommonResult<Object> getTaskDetail(@PathVariable("taskId") String taskId) {
        Task task = taskService.get(taskId);
        return success(task);
    }

    @GetMapping("/startTask")
    public CommonResult<Object> startTask(String taskId) {
        taskService.startTask(taskId);
        return success();
    }

    @GetMapping("/stopTask")
    public CommonResult<Object> stopTask(String taskId) {
        taskService.stopTask(taskId);
        return success();
    }
}
