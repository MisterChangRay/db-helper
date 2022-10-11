package com.github.changray.dbhelper.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * @description db_servers
 * @author ray
 * @date 2022-10-08
 */
public class DbServers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * ip
     */
    private String ip;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * desc
     */
    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "`port`")
    private String port;


    private String connUri;

    public String getConnUri() {
        return connUri;
    }

    public void setConnUri(String connUri) {
        this.connUri = connUri;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public DbServers() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}