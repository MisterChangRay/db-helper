package com.github.changray.dbhelper.listeners;

import com.github.changray.dbhelper.core.TaskExecutor;
import com.github.changray.dbhelper.events.TaskStartEvent;
import com.github.changray.dbhelper.events.TaskerStartEvent;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskerMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import com.github.changray.dbhelper.pojo.po.Tasker;
import com.github.changray.dbhelper.service.executor.MySqlDataTransferTaskerExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 进程保护服务
 *
 * 这里主要是使用远程SSH 方式进行进程保护
 */
@Service
public class TaskerListener implements ApplicationContextAware {
    static Logger logger = LoggerFactory.getLogger(TaskerListener.class.getName());
    static ApplicationContext applicationContext;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskerMapper taskerMapper;
    @Autowired
    private TaskExecutor taskExecutor;

    @EventListener(value = {TaskerStartEvent.class})
    public void taskStart(TaskerStartEvent taskerStartEvent) {
        if(Objects.isNull(applicationContext)) {
            return;
        }

        Tasker tasker = taskerStartEvent.getTasker();
        boolean startSuccess = false;
        switch (tasker.getType()) {
            case 1:
                // 1Mysql数据迁移
                MySqlDataTransferTaskerExecutor bean = applicationContext.getBean(MySqlDataTransferTaskerExecutor.class);
                bean.setTasker(tasker);
                startSuccess = taskExecutor.execute(bean);
                break;
            default:
                // do nothing
        }
        if(startSuccess) {
            taskerMapper.markStatusRunning(tasker.getTaskId());
            taskMapper.markStatusRunning(tasker.getTaskId());
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }
}
