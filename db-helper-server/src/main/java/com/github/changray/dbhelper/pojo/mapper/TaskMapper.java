package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description taskMapper
 * @author ray
 * @date 2022-10-09
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    @Select(
            "<script>select t0.* from task t0 " +
                    //add here if need left join
                    "where 1=1" +
                    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
                    "<when test='name!=null and name!=&apos;&apos; '> and t0.name=#{name}</when> " +
                    "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
                    "<when test='sql!=null and sql!=&apos;&apos; '> and t0.sql=#{sql}</when> " +
                    "<when test='createTime!=null and createTime!=&apos;&apos; '> and t0.create_time=#{createTime}</when> " +
                    "<when test='endTime!=null and endTime!=&apos;&apos; '> and t0.end_time=#{endTime}</when> " +
                    "<when test='lastRunTime!=null and lastRunTime!=&apos;&apos; '> and t0.last_run_time=#{lastRunTime}</when> " +
                    "<when test='runCounter!=null and runCounter!=&apos;&apos; '> and t0.run_counter=#{runCounter}</when> " +
                    "<when test='pagerType!=null and pagerType!=&apos;&apos; '> and t0.pager_type=#{pagerType}</when> " +
                    "<when test='indexColumn!=null and indexColumn!=&apos;&apos; '> and t0.index_column=#{indexColumn}</when> " +
                    "<when test='groupId!=null and groupId!=&apos;&apos; '> and t0.group_id=#{groupId}</when> " +
                    "<when test='pagerExt!=null and pagerExt!=&apos;&apos; '> and t0.pager_ext=#{pagerExt}</when> " +
                    "<when test='updateTime!=null and updateTime!=&apos;&apos; '> and t0.update_time=#{updateTime}</when> " +
                    "<when test='script!=null and script!=&apos;&apos; '> and t0.script=#{script}</when> " +
                    "<when test='status!=null and status!=&apos;&apos; '> and t0.status=#{status}</when> " +
                    //add here if need page limit
                    //" limit ${page},${limit} " +
                    " </script>")
    List<Task> pageAll(Task queryParamDTO, int page, int limit);

    @Select("<script>select count(1) from task t0 " +
            //add here if need left join
            "where 1=1" +
            "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
            "<when test='name!=null and name!=&apos;&apos; '> and t0.name=#{name}</when> " +
            "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
            "<when test='sql!=null and sql!=&apos;&apos; '> and t0.sql=#{sql}</when> " +
            "<when test='createTime!=null and createTime!=&apos;&apos; '> and t0.create_time=#{createTime}</when> " +
            "<when test='endTime!=null and endTime!=&apos;&apos; '> and t0.end_time=#{endTime}</when> " +
            "<when test='lastRunTime!=null and lastRunTime!=&apos;&apos; '> and t0.last_run_time=#{lastRunTime}</when> " +
            "<when test='runCounter!=null and runCounter!=&apos;&apos; '> and t0.run_counter=#{runCounter}</when> " +
            "<when test='pagerType!=null and pagerType!=&apos;&apos; '> and t0.pager_type=#{pagerType}</when> " +
            "<when test='indexColumn!=null and indexColumn!=&apos;&apos; '> and t0.index_column=#{indexColumn}</when> " +
            "<when test='groupId!=null and groupId!=&apos;&apos; '> and t0.group_id=#{groupId}</when> " +
            "<when test='pagerExt!=null and pagerExt!=&apos;&apos; '> and t0.pager_ext=#{pagerExt}</when> " +
            "<when test='updateTime!=null and updateTime!=&apos;&apos; '> and t0.update_time=#{updateTime}</when> " +
            "<when test='script!=null and script!=&apos;&apos; '> and t0.script=#{script}</when> " +
            "<when test='status!=null and status!=&apos;&apos; '> and t0.status=#{status}</when> " +
            " </script>")
    int countAll(Task queryParamDTO);


    // status 1待执行,2正在执行,3暂停,4已完成
    @Select("<script>select * from task  where status = 1  </script>")
    List<Task> getAllShouldGeneratorTaskRow();

}