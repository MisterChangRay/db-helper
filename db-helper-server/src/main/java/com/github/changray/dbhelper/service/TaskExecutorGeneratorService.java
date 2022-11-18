package com.github.changray.dbhelper.service;

import com.github.changray.dbhelper.databases.MySqlDBSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutorGeneratorService {
    @Autowired
    MySqlDBSourceService mySqlDbSourceService;
    static Logger logger = LoggerFactory.getLogger(TaskExecutorGeneratorService.class.getName());
//
//    @EventListener(value = {GeneratorTaskExecutorEvent.class})
//    @Transactional
//    public void generatorTaskExecutorEvent(Task task) {
//        AddTasks addTasks = JSON.parseObject(task.getParams(), AddTasks.class);
//        DataSource sourceDB = mySqlDbSourceService.getDataSource(addTasks.getSourceDBId());
//
//        StopWatch stopWatch = new StopWatch("任务分解耗时");
//        ArrayList ids = new ArrayList(1001);
//        try(Connection connection = sourceDB.getConnection();
//        Statement statement = connection.createStatement();) {
//            for (TableInfo table : addTasks.getTables()) {
//               long index =0;
//               stopWatch.start("分解表 " + table);
//               while (true) {
//                   ids = new ArrayList(1001);
//                   statement.execute(String.format("select `%s` from `%s` limit %s,1000", table.getPk(), table.getName(), index));
//                   ResultSet resultSet = statement.getResultSet();
//                   while (resultSet.next()) {
//                       ids.add(resultSet.getString(0));
//                   }
//
//                   if(ids.size() == 0) {
//                       break;
//                   }
//
//                   Tasker tasker = new Tasker();
//                   tasker.setCreateTime(new Date());
//                   tasker.setKeys(JSON.toJSONString(ids));
//                   tasker.setTaskId(task.getId());
//                   tasker.setStatus(0);
//                   taskExecutorMapper.insert(tasker);
//                   index += 1000;
//               }
//               stopWatch.stop();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        logger.info("分解任务完成 \n", stopWatch.prettyPrint());
//    }

}
