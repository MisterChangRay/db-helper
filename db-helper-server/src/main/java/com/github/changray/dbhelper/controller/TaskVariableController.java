package com.github.changray.dbhelper.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.changray.dbhelper.pojo.dto.BaseResult;
import com.github.changray.dbhelper.pojo.mapper.TaskVariableMapper;
import com.github.changray.dbhelper.pojo.po.TaskVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description aa
 * @author ray
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/task_variable")
public class TaskVariableController {
    static Logger logger = LoggerFactory.getLogger(TaskVariableController.class.getName());
    @Autowired
    private TaskVariableMapper taskVariableMapper;

    /**
     * 新增或编辑
     */
    @PostMapping("/save")
    public BaseResult save(@RequestBody TaskVariable taskVariable){
        logger.info("dbServers:"+ JSON.toJSONString(taskVariable));
        TaskVariable oldTaskVariable = taskVariableMapper.selectOne(new QueryWrapper<TaskVariable>().eq("name",taskVariable.getName()));
        if(oldTaskVariable!=null){
            taskVariable.setUpdateTime(new Date());
            taskVariableMapper.updateById(taskVariable);
        }else{
            taskVariable.setCreateTime(new Date());
            taskVariable.setUpdateTime(new Date());
            taskVariableMapper.insert(taskVariable);
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
        Page<TaskVariable> buildPage = new Page<TaskVariable>(page,limit);
        //条件构造器
        QueryWrapper<TaskVariable> queryWrapper = new QueryWrapper<TaskVariable>();
        //执行分页
        IPage<TaskVariable> pageList = taskVariableMapper.selectPage(buildPage, queryWrapper);
        //返回结果
        return BaseResult.page(pageList.getRecords(), (int) pageList.getTotal());
    }



    /**
     * 删除
     */
    @GetMapping("/del")
    public BaseResult del(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        TaskVariable taskVariable = taskVariableMapper.selectById(id);
        if(taskVariable!=null){
            taskVariableMapper.deleteById(id);
        }
        return BaseResult.success();
    }


}