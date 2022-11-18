package com.github.changray.dbhelper.service;

import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskTriggerService {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TaskMapper taskMapper;

    /**
     * 每5s执行一次同步分解任务
     */
    @Scheduled(fixedRate = 5000)
    public void generatorTaskExecutorEvent() {
//        List<Task> allShouldGeneratorTaskRow = taskMapper.getAllShouldGeneratorTaskRow();
//        if(Objects.isNull(allShouldGeneratorTaskRow) || allShouldGeneratorTaskRow.size() == 0) {
//            return;
//        }
//
//        allShouldGeneratorTaskRow.forEach(task -> {
//            GeneratorTaskExecutorEvent generatorTaskExecutorEvent = new GeneratorTaskExecutorEvent(task);
//            applicationContext.publishEvent(generatorTaskExecutorEvent);
//        });

    }

}

