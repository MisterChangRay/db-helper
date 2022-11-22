package com.github.changray.dbhelper.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.changray.dbhelper.pojo.dto.BaseResult;
import com.github.changray.dbhelper.pojo.dto.dbinfo.TableInfo;
import com.github.changray.dbhelper.pojo.dto.request.TaskDTO;
import com.github.changray.dbhelper.pojo.dto.request.TaskerDTO;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskerMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import com.github.changray.dbhelper.pojo.po.Tasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * @description aa
 * @author ray
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    static Logger logger = LoggerFactory.getLogger(TaskController.class.getName());
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskerMapper taskerMapper;


    /**
     * 新增或编辑
     */
    @PostMapping("/start/{id}")
    public BaseResult start(@PathVariable String id){
        logger.info("start task:"+ id);


        return BaseResult.success();
    }


    /**
     * 新增或编辑
     */
    @PostMapping("/stop")
    public BaseResult stop(@RequestBody Task task){
        logger.info("task:"+ JSON.toJSONString(task));
        return BaseResult.success();
    }



    /**
     * 新增或编辑
     *
     * 已经开始执行的任务不允许编辑， 只能删除重建
     *
     */
    @PostMapping("/save")
    @Transactional
    public BaseResult save(@RequestBody TaskDTO task){
        logger.info("task:"+ JSON.toJSONString(task));

        TaskerDTO taskerDTO = task.getTasker();
        if(taskerDTO.getSourceDBId().equals(taskerDTO.getTargetDBId()) &&
                taskerDTO.getSourceDatabase().equals(taskerDTO.getTargetDatabase())) {
            return BaseResult.fail(999, "源库和目标库不能相同");
        }

        if(Objects.isNull(taskerDTO.getTables()) || taskerDTO.getTables().length == 0) {
            return BaseResult.fail(999, "没有需要迁移表");
        }

        int taskerCount = task.getTasker().getTables().length;
        Task row = null;
        if(Objects.nonNull(task.getId())) {
            row = taskMapper.selectById(task.getId());
            if(row.getStatus() != 0) {
                return BaseResult.success().setMsg("已开始的任务不允许修改！");
            }

            buildTasker(task);
            row.setName(task.getName());
            row.setDesc(task.getDesc());
            row.setTaskerCount(taskerCount);
            taskMapper.updateById(row);
            return BaseResult.success().setMsg("修改成功");
        } else {
            row = new Task();
            row.setName(task.getName());
            row.setDesc(task.getDesc());
            row.setCreateTime(new Date());
            row.setTaskerCount(0);
            row.setTaskerRanCount(0);
            row.setStatus(0);
            row.setTaskerCount(taskerCount);

            taskMapper.insert(row);
            task.setId(row.getId());
        }

        buildTasker(task);


        return BaseResult.success().setMsg("新增成功");
    }

    private void buildTasker(TaskDTO task) {
        TaskerDTO taskerDTO = task.getTasker();

        for (TableInfo table : taskerDTO.getTables()) {
            Tasker tasker = new Tasker();
            tasker.setTaskId(task.getId());
            tasker.setCreateTime(new Date());
            tasker.setUpdateTime(new Date());
            tasker.setDesc(taskerDTO.getDesc());
            tasker.setName(taskerDTO.getName());
            tasker.setSql(String.format("select * from %s", table.getName()));
            tasker.setStatus(3);
            tasker.setCron(taskerDTO.getCron());
            tasker.setBatchSize(1000);
            tasker.setSleepTime(1);
            taskerMapper.insert(tasker);
        }
    }


    /**
     * 自动分页查询
     */
    @GetMapping("/list")
    public Object list(String searchParams,
                       @RequestParam(required = false, defaultValue = "0") int page,
                       @RequestParam(required = false, defaultValue = "10") int limit) {
        logger.info("page:"+page+"-limit:"+limit+"-json:"+ JSON.toJSONString(searchParams));
        //分页构造器
        Page<Task> buildPage = new Page<Task>(page,limit);
        //条件构造器
        QueryWrapper<Task> queryWrapper = new QueryWrapper<Task>();
        //执行分页
        IPage<Task> pageList = taskMapper.selectPage(buildPage, queryWrapper);
        //返回结果
        return BaseResult.page(pageList.getRecords(), (int) pageList.getTotal());
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public BaseResult del(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        Task task = taskMapper.selectById(id);
        if(task!=null){
            taskMapper.deleteById(id);
        }
        return BaseResult.success();
    }


}