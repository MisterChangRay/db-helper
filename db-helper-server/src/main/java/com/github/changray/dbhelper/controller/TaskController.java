package com.github.changray.dbhelper.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.changray.dbhelper.pojo.dto.BaseResult;
import com.github.changray.dbhelper.pojo.dto.dbinfo.TableInfo;
import com.github.changray.dbhelper.pojo.dto.request.AddTasks;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskVariableMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import com.github.changray.dbhelper.pojo.po.TaskVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * 新增或编辑
     */
    @PostMapping("/save")
    public BaseResult save(@RequestBody Task task){
        logger.info("task:"+ JSON.toJSONString(task));
        Task oldTaskVariable = taskMapper.selectOne(new QueryWrapper<Task>().eq("name",task.getName()));
        if(oldTaskVariable!=null){
            task.setUpdateTime(new Date());
            taskMapper.updateById(task);
        }else{
            task.setCreateTime(new Date());
            task.setUpdateTime(new Date());
            taskMapper.insert(task);
        }
        return BaseResult.success();
    }


    /**
     * 新增或编辑
     */
    @PostMapping("/createTask")
    @Transactional
    public BaseResult createTask(@RequestBody AddTasks addTasks){
        logger.info("创建批量任务:"+ JSON.toJSONString(addTasks));

        if(Objects.isNull(addTasks)) {
            return BaseResult.fail(999, "请求错误");
        }

        if(addTasks.getSourceDBId().equals(addTasks.getTargetDBId())) {
            return BaseResult.fail(999, "源库和目标库不能相同");
        }

        if(Objects.isNull(addTasks.getTables()) || addTasks.getTables().length == 0) {
            return BaseResult.fail(999, "没有需要迁移表");
        }

        for (TableInfo table : addTasks.getTables()) {
            Task task = new Task();
            task.setCreateTime(new Date());
            task.setUpdateTime(new Date());
            task.setDesc(addTasks.getDesc());
            task.setName(addTasks.getName());
            task.setGroupId(addTasks.getGroupId());
            task.setSql(String.format("select * from %s", table));
            task.setStatus(2);
            task.setCron(addTasks.getCron());
            task.setParams(JSON.toJSONString(addTasks));
            taskMapper.insert(task);
        }

        return BaseResult.success();
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