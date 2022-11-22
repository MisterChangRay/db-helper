package com.github.changray.dbhelper.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.changray.dbhelper.pojo.dto.BaseResult;
import com.github.changray.dbhelper.pojo.mapper.TaskMapper;
import com.github.changray.dbhelper.pojo.mapper.TaskerMapper;
import com.github.changray.dbhelper.pojo.po.Tasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description aa
 * @author ray
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/tasker")
public class TaskerController {
    static Logger logger = LoggerFactory.getLogger(TaskerController.class.getName());
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskerMapper taskerMapper;



    /**
     * 自动分页查询
     */
    @GetMapping("/list")
    public Object list(String searchParams,
                       @RequestParam(required = false, defaultValue = "0") int page,
                       @RequestParam(required = false, defaultValue = "10") int limit) {
        logger.info("page:"+page+"-limit:"+limit+"-json:"+ JSON.toJSONString(searchParams));
        //分页构造器
        Page<Tasker> buildPage = new Page<Tasker>(page,limit);
        //条件构造器
        QueryWrapper<Tasker> queryWrapper = new QueryWrapper<Tasker>();
        //执行分页
        IPage<Tasker> pageList = taskerMapper.selectPage(buildPage, queryWrapper);
        //返回结果
        return BaseResult.page(pageList.getRecords(), (int) pageList.getTotal());
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public BaseResult del(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        Tasker task = taskerMapper.selectById(id);
        if(task!=null){
            taskerMapper.deleteById(id);
        }
        return BaseResult.success();
    }

    /**
     *
     */
    @GetMapping("/start")
    public BaseResult start(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        Tasker task = taskerMapper.selectById(id);
        if(task!=null){
            taskerMapper.deleteById(id);
        }
        return BaseResult.success();
    }

    /**
     *
     */
    @GetMapping("/stop")
    public BaseResult stop(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        Tasker task = taskerMapper.selectById(id);
        if(task!=null){
            taskerMapper.deleteById(id);
        }
        return BaseResult.success();
    }

}