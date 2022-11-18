package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.TaskGroup;
import com.github.changray.dbhelper.pojo.po.Tasker;
import org.apache.ibatis.annotations.Mapper;
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

}