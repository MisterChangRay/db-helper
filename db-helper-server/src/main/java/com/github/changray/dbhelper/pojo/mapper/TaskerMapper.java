package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.DbServers;
import com.github.changray.dbhelper.pojo.po.TaskGroup;
import com.github.changray.dbhelper.pojo.po.Tasker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @description task_groupMapper
 * @author ray
 * @date 2022-10-09
 */
@Mapper
public interface TaskerMapper extends BaseMapper<Tasker> {


    @Update("<script>" +
            "update tasker set status = 1 where status in (2, 4) and task_id = #{taskId}" +
            " </script>")
    int startByTaskId(@Param("taskId") Integer taskId);


    @Update("<script>" +
            "update tasker set status = 3 where status in (1, 4) and id = #{taskerId}" +
            " </script>")
    void markStatusRunning(@Param("taskerId") Integer taskerId);


}