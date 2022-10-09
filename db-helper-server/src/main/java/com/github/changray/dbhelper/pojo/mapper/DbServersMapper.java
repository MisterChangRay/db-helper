package com.github.changray.dbhelper.pojo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.changray.dbhelper.pojo.po.DbServers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description db_serversMapper
 * @author ray
 * @date 2022-10-08
 */
@Mapper
public interface DbServersMapper extends BaseMapper<DbServers> {

    @Select(
            "<script>select t0.* from db_servers t0 " +
                    //add here if need left join
                    "where 1=1" +
                    "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
                    "<when test='ip!=null and ip!=&apos;&apos; '> and t0.ip=#{ip}</when> " +
                    "<when test='username!=null and username!=&apos;&apos; '> and t0.username=#{username}</when> " +
                    "<when test='password!=null and password!=&apos;&apos; '> and t0.password=#{password}</when> " +
                    "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
                    //add here if need page limit
                    //" limit ${page},${limit} " +
                    " </script>")
    List<DbServers> pageAll(DbServers queryParamDTO, int page, int limit);

    @Select("<script>select count(1) from db_servers t0 " +
            //add here if need left join
            "where 1=1" +
            "<when test='id!=null and id!=&apos;&apos; '> and t0.id=#{id}</when> " +
            "<when test='ip!=null and ip!=&apos;&apos; '> and t0.ip=#{ip}</when> " +
            "<when test='username!=null and username!=&apos;&apos; '> and t0.username=#{username}</when> " +
            "<when test='password!=null and password!=&apos;&apos; '> and t0.password=#{password}</when> " +
            "<when test='desc!=null and desc!=&apos;&apos; '> and t0.desc=#{desc}</when> " +
            " </script>")
    int countAll(DbServers queryParamDTO);

}