package com.github.changray.dbhelper.listeners;

import com.github.changray.dbhelper.events.TaskStartEvent;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskerMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


/**
 * 进程保护服务
 *
 * 这里主要是使用远程SSH 方式进行进程保护
 */
@Service
public class TaskListener {
    static Logger logger = LoggerFactory.getLogger(TaskListener.class.getName());
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskerMapper taskerMapper;

    @EventListener(value = {TaskStartEvent.class})
    public void taskStart(TaskStartEvent taskStartEvent) {
        Task task = taskMapper.selectById(taskStartEvent.getTaskId());
        if(2 == task.getStatus() ) {
            // 运行中
            return;
        }

        taskerMapper.startByTaskId(task.getId());

    }

}
