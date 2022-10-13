package com.github.changray.dbhelper.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.changray.dbhelper.pojo.dto.BaseResult;
import com.github.changray.dbhelper.pojo.mapper.TaskGroupMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.po.TaskGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RequestMapping("/tasksgroup")
@RestController
public class GroupController {
    static Logger logger = LoggerFactory.getLogger(GroupController.class.getName());
    @Autowired
    private TaskGroupMapper taskGroupMapper;

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 新增或编辑
     */
    @PostMapping("/save")
    public BaseResult save(@RequestBody TaskGroup taskGroup){
        logger.info("dbServers:"+ JSON.toJSONString(taskGroup));
        TaskGroup oldTaskGroup = taskGroupMapper.selectOne(new QueryWrapper<TaskGroup>().eq("name",taskGroup.getName()));
        if(oldTaskGroup!=null){
            taskGroupMapper.updateById(taskGroup);
        }else{
            taskGroup.setCreateTime(new Date());
            taskGroupMapper.insert(taskGroup);
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
        Page<TaskGroup> buildPage = new Page<TaskGroup>(page,limit);
        //条件构造器
        QueryWrapper<TaskGroup> queryWrapper = new QueryWrapper<TaskGroup>();
        //执行分页
        IPage<TaskGroup> pageList = taskGroupMapper.selectPage(buildPage, queryWrapper);
        //返回结果
        return BaseResult.page(pageList.getRecords(), (int) pageList.getTotal());
    }



    /**
     * 删除
     */
    @GetMapping("/del")
    public BaseResult del(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        TaskGroup oldTaskGroup = taskGroupMapper.selectById(id);
        if(oldTaskGroup!=null){
            taskGroupMapper.deleteById(id);
        }
        return BaseResult.success();
    }



    /**
     * 启动所有任务
     */
    @GetMapping("/startall")
    public BaseResult startall(@RequestParam (value = "id") String id){
        logger.info("startall groupId:"+ JSON.toJSONString(id));
        taskMapper.changeStatus(null, Integer.valueOf(id), 1);
        return BaseResult.success();
    }



    /**
     * 停止分组内所有任务
     */
    @GetMapping("/stopall")
    public BaseResult stopall(@RequestParam (value = "id") String id){
        logger.info("stopall groupId:"+ JSON.toJSONString(id));
        taskMapper.changeStatus(null, Integer.valueOf(id), 3);
        return BaseResult.success();
    }


}
