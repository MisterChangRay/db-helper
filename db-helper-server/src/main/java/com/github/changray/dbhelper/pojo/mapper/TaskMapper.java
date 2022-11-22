package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @description taskMapper
 * @author ray
 * @date 2022-10-09
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    @Update("<script>" +
            "update task set status = 2 where id = #{taskId} limit 1" +
            " </script>")
    void markStatusRunning(Integer taskId);
}