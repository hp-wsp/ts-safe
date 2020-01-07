package com.ts.server.safe.task.controller.man.vo;

import com.ts.server.safe.task.domain.CheckContent;
import com.ts.server.safe.task.domain.CheckTask;

import java.util.List;

/**
 * 检查任务输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckTaskVo {
    private final CheckTask task;
    private final List<CheckContent> contents;

    /**
     * 构造{@link CheckTaskVo}
     *
     * @param task {@link CheckTask}
     * @param contents {@link CheckContent}集合
     */
    public CheckTaskVo(CheckTask task, List<CheckContent> contents) {
        this.task = task;
        this.contents = contents;
    }

    public CheckTask getTask() {
        return task;
    }

    public List<CheckContent> getContents() {
        return contents;
    }
}