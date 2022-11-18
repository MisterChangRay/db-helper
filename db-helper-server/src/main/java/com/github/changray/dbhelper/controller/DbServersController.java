package com.github.changray.dbhelper.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.changray.dbhelper.databases.MySqlDBSourceService;
import com.github.changray.dbhelper.pojo.dto.BaseResult;
import com.github.changray.dbhelper.pojo.dto.dbinfo.TableInfo;
import com.github.changray.dbhelper.pojo.mapper.DbServersMapper;
import com.github.changray.dbhelper.pojo.po.DbServers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description db_servers控制器
 * @author ray
 * @date 2022-10-08
 */
@RestController
@RequestMapping("/dbServers")
public class DbServersController {
    static Logger logger = LoggerFactory.getLogger(DbServersController.class.getName());
    @Autowired
    private DbServersMapper dbServersMapper;
    @Autowired
    private MySqlDBSourceService mySqlDbSourceService;



    /**
     * 显示链接所有数据库
     */
    @GetMapping("/loadDbs")
    public BaseResult loadDbs(@RequestParam("dbId") String dbId){
        logger.info("loadDbs:"+ JSON.toJSONString(dbId));
        List<String> allDatabases = mySqlDbSourceService.getAllDatabases(dbId);
        return BaseResult.success().setData(allDatabases);
    }

    /**
     * 显示链接数据库所有表
     */
    @GetMapping("/loadTables")
    public BaseResult loadTables(@RequestParam("dbId") String dbId, @RequestParam("dbName") String dbName){
        logger.info("dbId: {}, dbName: {}", dbId, dbName);
        List<TableInfo> allDatabases = mySqlDbSourceService.getAllTablels(dbId, dbName);
        return BaseResult.success().setData(allDatabases);
    }

    /**
     * 新增或编辑
     */
    @PostMapping("/save")
    public BaseResult save(@RequestBody DbServers dbServers){
        logger.info("dbServers:"+ JSON.toJSONString(dbServers));
        DbServers oldDbServers = dbServersMapper.selectOne(new QueryWrapper<DbServers>().eq("ip",dbServers.getIp()).eq("port", dbServers.getPort()));
        if(oldDbServers!=null){
            dbServersMapper.updateById(dbServers);
        }else{
            dbServersMapper.insert(dbServers);
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
        Page<DbServers> buildPage = new Page<DbServers>(page,limit);
        //条件构造器
        QueryWrapper<DbServers> queryWrapper = new QueryWrapper<DbServers>();
        //执行分页
        IPage<DbServers> pageList = dbServersMapper.selectPage(buildPage, queryWrapper);
        //返回结果
        return BaseResult.page(pageList.getRecords(), (int) pageList.getTotal());
    }



    /**
     * 删除
     */
    @GetMapping("/del")
    public BaseResult del(@RequestParam (value = "id") String id){
        logger.info("dbServers del:"+ JSON.toJSONString(id));
        DbServers oldTaskGroup = dbServersMapper.selectById(id);
        if(oldTaskGroup!=null){
            dbServersMapper.deleteById(id);
        }
        return BaseResult.success();
    }


}