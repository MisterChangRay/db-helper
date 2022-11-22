package com.github.changray.dbhelper.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @description Tasker
 * @author ray
 * @date 2022-10-09
 */
public class Tasker implements Serializable {

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
     * 执行sql
     */
    @TableField(value = "`sql`")
    private String sql;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * end_time
     */
    private Date endTime;

    /**
     * last_run_time
     */
    private Date runTime;

    /**
     * 执行次数
     */
    private Integer runCounter;

    /**
     * 分页方式，0普通，1自增主键分页，2普通主键分页
     */
    private Integer pagerType;

    /**
     * 索引字段
     */
    private String indexColumn;


    /**
     * 分页扩展字段
     */
    private String pagerExt;

    /**
     * update_time
     */
    private Date updateTime;

    /**
     * 脚本过滤
     */
    private String script;

    /**
     * 1运行，2暂停，3结束
     */
    private Integer status;

    private String cron;

    private Integer taskId;

    private Integer batchSize;


    private Integer sleepTime;

    private Integer type;

    private Integer totalSpendTime;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public Integer getTotalSpendTime() {
        return totalSpendTime;
    }

    public void setTotalSpendTime(Integer totalSpendTime) {
        this.totalSpendTime = totalSpendTime;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Tasker() {}

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

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

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



    public Integer getRunCounter() {
        return runCounter;
    }

    public void setRunCounter(Integer runCounter) {
        this.runCounter = runCounter;
    }

    public Integer getPagerType() {
        return pagerType;
    }

    public void setPagerType(Integer pagerType) {
        this.pagerType = pagerType;
    }

    public String getIndexColumn() {
        return indexColumn;
    }

    public void setIndexColumn(String indexColumn) {
        this.indexColumn = indexColumn;
    }



    public String getPagerExt() {
        return pagerExt;
    }

    public void setPagerExt(String pagerExt) {
        this.pagerExt = pagerExt;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}