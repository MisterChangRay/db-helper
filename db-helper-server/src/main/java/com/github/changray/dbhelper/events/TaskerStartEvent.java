package com.github.changray.dbhelper.events;

import com.github.changray.dbhelper.pojo.po.Tasker;
import org.springframework.context.ApplicationEvent;

public class TaskerStartEvent extends ApplicationEvent {
    private Tasker tasker;

    public Tasker getTasker() {
        return tasker;
    }

    public void setTasker(Tasker tasker) {
        this.tasker = tasker;
    }

    public TaskerStartEvent(Tasker source) {
        super(source);
    }
}
