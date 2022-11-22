package com.github.changray.dbhelper.core;

import com.github.changray.dbhelper.pojo.po.Tasker;

public class TaskRunnable implements Runnable{
    private Tasker tasker;


    public void setTasker(Tasker tasker) {
        this.tasker = tasker;
    }

    @Override
    public void run() {
        
    }

    public Tasker getTasker() {
        return tasker;
    }
}
