package com.github.changray.dbhelper.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskExecutor extends AbstractExecutorService {
    private static final int MAX_THREAD = 10;
    private Integer taskId;

    private Thread[] threads = {};
    private AtomicInteger taskerCount;
    private ThreadFactory threadFactory =  Executors.defaultThreadFactory();
    public static TaskExecutor  build() {
        TaskExecutor taskExecutor = new TaskExecutor();
        return taskExecutor;
    }


    @Override
    public void shutdown() {
        for (Thread thread : this.threads) {
            thread.interrupt();
        }
    }

    @NotNull
    @Override
    public List<Runnable> shutdownNow() {
        this.shutdown();
        return null;
    }

    @Override
    public boolean isShutdown() {
        return this.threads.length == 0;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
        return false;
    }
    public boolean isFull() {
        return taskerCount.get() > MAX_THREAD;
    }
    
    @Override
    public void execute(@NotNull Runnable command) {
        if(taskerCount.get() > MAX_THREAD) {
            return;
        }

        this.taskerCount.incrementAndGet();
        Thread thread = threadFactory.newThread(command);
        thread.start();
        this.taskerCount.decrementAndGet();
    }

}
