package com.github.changray.dbhelper.pojo.dto.request;

public class TaskDTO {
    private TaskerDTO tasker;
    private Integer id;
    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    public TaskerDTO getTasker() {
        return tasker;
    }

    public void setTasker(TaskerDTO tasker) {
        this.tasker = tasker;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
