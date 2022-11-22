package com.github.changray.dbhelper.core;

import com.github.changray.dbhelper.pojo.po.Tasker;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskExecutor {
    // cpu core
    private static final int MAX_THREAD = Runtime.getRuntime().availableProcessors() * 2;

    // taskid ->  taskThread
    private ConcurrentHashMap<Integer, TaskerThread[]> taskThreads = new ConcurrentHashMap<>();
    private DefaultThreadFactory threadFactory =  new DefaultThreadFactory();


    public void shutdownTask(Integer taskId) {
        TaskerThread[] taskerThreads = taskThreads.get(taskId);
        if(Objects.nonNull(taskerThreads ) || taskerThreads.length == 0) {
            return;
        }

        for (TaskerThread taskerThread : taskerThreads) {
            taskerThread.interrupt();
        }
    }

    public void shutdownTasker(Integer taskId, Integer taskerId) {
        TaskerThread[] taskerThreads = taskThreads.get(taskId);
        if(Objects.nonNull(taskerThreads ) || taskerThreads.length == 0) {
            return;
        }
        for (TaskerThread taskerThread : taskerThreads) {
            if(taskerThread.getId() != taskerId) {
                continue;
            }

            taskerThread.interrupt();
        }

    }


    public boolean execute(TaskRunnable runnable) {
        synchronized (taskThreads) {
            Tasker tasker = runnable.getTasker();
            TaskerThread[] taskThreads = this.taskThreads.get(tasker.getTaskId());
            if(taskThreads.length > MAX_THREAD) {
                return false;
            }

            TaskerThread thread =  threadFactory.newThread(runnable, tasker);
            taskThreads = Arrays.copyOf(taskThreads, taskThreads.length + 1);
            taskThreads[taskThreads.length - 1] = thread;

            thread.start();
            return true;
        }
    }


    /**
     * The default thread factory.
     */
    private static class DefaultThreadFactory  {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                    poolNumber.getAndIncrement() +
                    "-db-helper-taskthread-";
        }

        public TaskerThread newThread(Runnable r, Tasker tasker) {
            TaskerThread t = new TaskerThread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0, tasker);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
