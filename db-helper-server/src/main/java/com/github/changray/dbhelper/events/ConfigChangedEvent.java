package com.github.changray.dbhelper.events;

import org.springframework.context.ApplicationEvent;

public class ConfigChangedEvent extends ApplicationEvent {
    public ConfigChangedEvent(Object source) {
        super(source);
    }
}
