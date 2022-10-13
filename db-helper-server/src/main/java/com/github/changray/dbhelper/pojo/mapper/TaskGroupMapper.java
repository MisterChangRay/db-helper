package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.TaskGroup;
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
public interface TaskGroupMapper extends BaseMapper<TaskGroup> {

    @Select(
            "<script>select t0.* from task_group t0 " +
                    //add here if need left join
                    "where 1=1" +
                    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
                    "<when test='name!=null and name!=&apos;&apos; '> and t0.name=#{name}</when> " +
                    "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
                    "<when test='createTime!=null and createTime!=&apos;&apos; '> and t0.create_time=#{createTime}</when> " +
                    //add here if need page limit
                    //" limit ${page},${limit} " +
                    " </script>")
    List<TaskGroup> pageAll(TaskGroup queryParamDTO, int page, int limit);

    @Select("<script>select count(1) from task_group t0 " +
            //add here if need left join
            "where 1=1" +
            "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
            "<when test='name!=null and name!=&apos;&apos; '> and t0.name=#{name}</when> " +
            "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
            "<when test='createTime!=null and createTime!=&apos;&apos; '> and t0.create_time=#{createTime}</when> " +
            " </script>")
    int countAll(TaskGroup queryParamDTO);

}