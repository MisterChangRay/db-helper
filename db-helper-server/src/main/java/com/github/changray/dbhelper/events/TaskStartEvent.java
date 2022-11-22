package com.github.changray.dbhelper.events;

import org.springframework.context.ApplicationEvent;

public class TaskStartEvent extends ApplicationEvent {
    private String taskId;
    public TaskStartEvent(Object source) {
        super(source);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
