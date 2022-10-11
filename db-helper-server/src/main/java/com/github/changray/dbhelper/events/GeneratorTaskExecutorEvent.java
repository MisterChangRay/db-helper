package com.github.changray.dbhelper.events;

import com.github.changray.dbhelper.pojo.po.Task;
import org.hibernate.annotations.Tables;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class GeneratorTaskExecutorEvent extends ApplicationEvent {
    private Task task;

    public GeneratorTaskExecutorEvent(Task source) {
        super(source);
        this.task = source;

    }
}
