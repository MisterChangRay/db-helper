package com.github.changray.dbhelper.core;

public class DataTransfer {
    private int page;
    private int pageSize;

    private String pk;

    private String shortPk;

    private String tableName;

    private String dbName;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getShortPk() {
        return shortPk;
    }

    public void setShortPk(String shortPk) {
        this.shortPk = shortPk;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
