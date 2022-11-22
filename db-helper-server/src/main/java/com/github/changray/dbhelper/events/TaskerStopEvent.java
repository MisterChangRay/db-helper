package com.github.changray.dbhelper.events;

import org.springframework.context.ApplicationEvent;

public class TaskerStopEvent extends ApplicationEvent {
    public TaskerStopEvent(Object source) {
        super(source);
    }
}
