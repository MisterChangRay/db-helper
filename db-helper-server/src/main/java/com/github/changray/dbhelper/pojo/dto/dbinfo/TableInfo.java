package com.github.changray.dbhelper.pojo.dto.dbinfo;

import java.util.List;

public class TableInfo {
    private String name;
    private List<String> pk;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPk() {
        return pk;
    }

    public void setPk(List<String> pk) {
        this.pk = pk;
    }
}
