package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.TaskVariable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description task_variableMapper
 * @author ray
 * @date 2022-10-09
 */
@Mapper
public interface TaskVariableMapper extends BaseMapper<TaskVariable> {

    @Select(
            "<script>select t0.* from task_variable t0 " +
                    //add here if need left join
                    "where 1=1" +
                    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
                    "<when test='name!=null and name!=&apos;&apos; '> and t0.name=#{name}</when> " +
                    "<when test='value!=null and value!=&apos;&apos; '> and t0.value=#{value}</when> " +
                    "<when test='createDate!=null and createDate!=&apos;&apos; '> and t0.create_date=#{createDate}</when> " +
                    "<when test='updateDate!=null and updateDate!=&apos;&apos; '> and t0.update_date=#{updateDate}</when> " +
                    "<when test='groupId!=null and groupId!=&apos;&apos; '> and t0.group_id=#{groupId}</when> " +
                    "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
                    //add here if need page limit
                    //" limit ${page},${limit} " +
                    " </script>")
    List<TaskVariable> pageAll(TaskVariable queryParamDTO, int page, int limit);

    @Select("<script>select count(1) from task_variable t0 " +
            //add here if need left join
            "where 1=1" +
            "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
            "<when test='name!=null and name!=&apos;&apos; '> and t0.name=#{name}</when> " +
            "<when test='value!=null and value!=&apos;&apos; '> and t0.value=#{value}</when> " +
            "<when test='createDate!=null and createDate!=&apos;&apos; '> and t0.create_date=#{createDate}</when> " +
            "<when test='updateDate!=null and updateDate!=&apos;&apos; '> and t0.update_date=#{updateDate}</when> " +
            "<when test='groupId!=null and groupId!=&apos;&apos; '> and t0.group_id=#{groupId}</when> " +
            "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
            " </script>")
    int countAll(TaskVariable queryParamDTO);

}