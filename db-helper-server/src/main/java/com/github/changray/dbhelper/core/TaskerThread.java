package com.github.changray.dbhelper.core;

import com.github.changray.dbhelper.pojo.po.Tasker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public  class TaskerThread extends Thread {
    private Tasker tasker;


    public TaskerThread(@Nullable ThreadGroup group, Runnable target, @NotNull String name, long stackSize, Tasker tasker) {
        super(group, target, name, stackSize);
        this.tasker = tasker;
    }


}
