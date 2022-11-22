package com.github.changray.dbhelper.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.changray.dbhelper.events.TaskerStartEvent;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskerMapper;
import com.github.changray.dbhelper.pojo.po.Tasker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTriggerService {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    TaskerMapper taskerMapper;

    /**
     * 每5s执行一次同步分解任务
     */
    @Scheduled(fixedRate = 5000)
    public void generatorTaskExecutorEvent() {

        Tasker tasker = new Tasker();
        tasker.setStatus(5);

        QueryWrapper<Tasker> queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(tasker);
        queryWrapper.last(" limit 10");
        List<Tasker> taskers = taskerMapper.selectList(queryWrapper);


        for (Tasker tasker1 : taskers) {
            TaskerStartEvent taskerStartEvent = new TaskerStartEvent(tasker1);
            applicationContext.publishEvent(taskerStartEvent);
        }

    }

}

