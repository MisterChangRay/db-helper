package com.github.changray.dbhelper.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
* @description task
* @author ray
* @date 2022-10-09
*/
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    @TableField(value = "`desc`")
    private String desc;


    /**
     * create_time
     */
    private Date createTime;


    /**
     * 1运行，2暂停，3结束
     */
    private Integer status;

    private int taskerRanCount;

    private int taskerCount;

    public Task() {}


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getTaskerRanCount() {
        return taskerRanCount;
    }

    public void setTaskerRanCount(int taskerRanCount) {
        this.taskerRanCount = taskerRanCount;
    }

    public int getTaskerCount() {
        return taskerCount;
    }

    public void setTaskerCount(int taskerCount) {
        this.taskerCount = taskerCount;
    }
}