package com.ts.server.safe.task.controller.man.vo;

import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.domain.TaskCheck;

import java.util.List;

/**
 * 检查任务输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckTaskVo {
    private final TaskCheck task;
    private final List<TaskItem> contents;

    /**
     * 构造{@link CheckTaskVo}
     *
     * @param task {@link TaskCheck}
     * @param contents {@link TaskItem}集合
     */
    public CheckTaskVo(TaskCheck task, List<TaskItem> contents) {
        this.task = task;
        this.contents = contents;
    }

    public TaskCheck getTask() {
        return task;
    }

    public List<TaskItem> getContents() {
        return contents;
    }
}
