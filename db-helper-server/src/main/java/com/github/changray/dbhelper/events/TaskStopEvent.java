package com.github.changray.dbhelper.events;

import org.springframework.context.ApplicationEvent;

public class TaskStopEvent extends ApplicationEvent {
    public TaskStopEvent(Object source) {
        super(source);
    }
}
