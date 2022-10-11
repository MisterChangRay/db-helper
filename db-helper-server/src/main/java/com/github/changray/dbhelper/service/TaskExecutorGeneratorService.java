package com.github.changray.dbhelper.service;

import com.alibaba.fastjson.JSON;
import com.github.changray.dbhelper.databases.MySqlDBSourceService;
import com.github.changray.dbhelper.events.GeneratorTaskExecutorEvent;
import com.github.changray.dbhelper.pojo.dto.dbinfo.TableInfo;
import com.github.changray.dbhelper.pojo.dto.request.AddTasks;
import com.github.changray.dbhelper.pojo.mapper.TaskExecutorMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import com.github.changray.dbhelper.pojo.po.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TaskExecutorGeneratorService {
    @Autowired
    MySqlDBSourceService mySqlDbSourceService;
    @Autowired
    TaskExecutorMapper taskExecutorMapper;
    static Logger logger = LoggerFactory.getLogger(TaskExecutorGeneratorService.class.getName());

    @EventListener(value = {GeneratorTaskExecutorEvent.class})
    @Transactional
    public void generatorTaskExecutorEvent(Task task) {
        AddTasks addTasks = JSON.parseObject(task.getParams(), AddTasks.class);
        DataSource sourceDB = mySqlDbSourceService.getDataSource(addTasks.getSourceDBId());

        StopWatch stopWatch = new StopWatch("任务分解耗时");
        ArrayList ids = new ArrayList(1001);
        try(Connection connection = sourceDB.getConnection();
        Statement statement = connection.createStatement();) {
            for (TableInfo table : addTasks.getTables()) {
               long index =0;
               stopWatch.start("分解表 " + table);
               while (true) {
                   ids = new ArrayList(1001);
                   statement.execute(String.format("select `%s` from `%s` limit %s,1000", table.getPk(), table.getName(), index));
                   ResultSet resultSet = statement.getResultSet();
                   while (resultSet.next()) {
                       ids.add(resultSet.getString(0));
                   }

                   if(ids.size() == 0) {
                       break;
                   }

                   TaskExecutor taskExecutor = new TaskExecutor();
                   taskExecutor.setCreateTime(new Date());
                   taskExecutor.setKeys(JSON.toJSONString(ids));
                   taskExecutor.setTaskId(task.getId());
                   taskExecutor.setStatus(0);
                   taskExecutorMapper.insert(taskExecutor);
                   index += 1000;
               }
               stopWatch.stop();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("分解任务完成 \n", stopWatch.prettyPrint());
    }

}
