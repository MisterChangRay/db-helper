package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.TaskExecutor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description task_executerMapper
 * @author ray
 * @date 2022-10-10
 */
@Mapper
public interface TaskExecutorMapper extends BaseMapper<TaskExecutor> {

    @Select(
            "<script>select t0.* from task_executer t0 " +
                    //add here if need left join
                    "where 1=1" +
                    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
                    "<when test='taskId!=null and taskId!=&apos;&apos; '> and t0.task_id=#{taskId}</when> " +
                    "<when test='status!=null and status!=&apos;&apos; '> and t0.status=#{status}</when> " +
                    "<when test='keys!=null and keys!=&apos;&apos; '> and t0.keys=#{keys}</when> " +
                    "<when test='createTime!=null and createTime!=&apos;&apos; '> and t0.create_time=#{createTime}</when> " +
                    //add here if need page limit
                    //" limit ${page},${limit} " +
                    " </script>")
    List<TaskExecutor> pageAll(TaskExecutor queryParamDTO, int page, int limit);

    @Select("<script>select count(1) from task_executer t0 " +
            //add here if need left join
            "where 1=1" +
            "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
            "<when test='taskId!=null and taskId!=&apos;&apos; '> and t0.task_id=#{taskId}</when> " +
            "<when test='status!=null and status!=&apos;&apos; '> and t0.status=#{status}</when> " +
            "<when test='keys!=null and keys!=&apos;&apos; '> and t0.keys=#{keys}</when> " +
            "<when test='createTime!=null and createTime!=&apos;&apos; '> and t0.create_time=#{createTime}</when> " +
            " </script>")
    int countAll(TaskExecutor queryParamDTO);

}