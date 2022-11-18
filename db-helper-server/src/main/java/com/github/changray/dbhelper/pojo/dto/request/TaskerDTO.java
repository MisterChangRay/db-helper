package com.github.changray.dbhelper.pojo.dto.request;

import com.github.changray.dbhelper.pojo.dto.dbinfo.TableInfo;

public class TaskerDTO {
    private TableInfo[] tables;
    private String sourceDBId;
    private String sourceDatabase;
    private String targetDBId;
    private String targetDatabase;
    private String cron;
    private String name;
    private String desc;
    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public TableInfo[] getTables() {
        return tables;
    }

    public void setTables(TableInfo[] tables) {
        this.tables = tables;
    }

    public String getSourceDBId() {
        return sourceDBId;
    }

    public void setSourceDBId(String sourceDBId) {
        this.sourceDBId = sourceDBId;
    }

    public String getSourceDatabase() {
        return sourceDatabase;
    }

    public void setSourceDatabase(String sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public String getTargetDBId() {
        return targetDBId;
    }

    public void setTargetDBId(String targetDBId) {
        this.targetDBId = targetDBId;
    }

    public String getTargetDatabase() {
        return targetDatabase;
    }

    public void setTargetDatabase(String targetDatabase) {
        this.targetDatabase = targetDatabase;
    }



    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
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
}
