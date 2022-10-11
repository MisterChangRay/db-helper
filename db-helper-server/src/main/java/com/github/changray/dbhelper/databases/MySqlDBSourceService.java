package com.github.changray.dbhelper.databases;

import com.github.changray.dbhelper.pojo.dto.dbinfo.TableInfo;
import com.github.changray.dbhelper.pojo.mapper.DbServersMapper;
import com.github.changray.dbhelper.pojo.po.DbServers;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Service
public class MySqlDBSourceService {
    static Map<String, DataSource> dataSourceCache= new HashMap<>();
    @Autowired
    private DbServersMapper dbServersMapper;

    public DataSource getDataSource(String dbid) {
        if(dataSourceCache.containsKey(dbid)) {
            return dataSourceCache.get(dbid);
        }

        DbServers dbServers = dbServersMapper.selectById(dbid);
        if(Objects.isNull(dbServers)) {
            return null;
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setUsername(dbServers.getUsername());
        hikariConfig.setPassword(dbServers.getPassword());
        hikariConfig.setConnectionTestQuery("select 1;");
        if(StringUtils.hasLength(dbServers.getConnUri())) {
            hikariConfig.setJdbcUrl(dbServers.getConnUri());
        } else {
            hikariConfig.setJdbcUrl("jdbc:mysql://" +dbServers.getIp()+ ":" + dbServers.getPort()+"/mysql?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=true");
        }
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        dataSourceCache.put(dbid, hikariDataSource);
        return hikariDataSource;
    }


    public List<String> getAllDatabases(String dbid) {
        DataSource dataSource = this.getDataSource(dbid);
        List<String> res = new ArrayList<>();

        try(Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.execute("show databases;");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                res.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public List<TableInfo> getAllTablels(String dbid, String database) {
        DataSource dataSource = this.getDataSource(dbid);
        List<TableInfo> res = new ArrayList<>();
        try(Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {

            DatabaseMetaData metaData = connection.getMetaData();
            statement.execute(String.format("use %s;", database));
            statement.execute("show tables;");

            ResultSet resultSet = statement.getResultSet();
            TableInfo tableInfo =null;
            ArrayList<String> pks = null;
            while (resultSet.next()) {
                tableInfo = new TableInfo();
                tableInfo.setName(resultSet.getString(1));
                ResultSet rs2 = metaData.getPrimaryKeys(database, null, tableInfo.getName());
                pks = new ArrayList<>(20);
                while (rs2.next()){
                    pks.add(rs2.getString("COLUMN_NAME"));
                }
                if(pks.size() == 1) {
                    tableInfo.setPk(pks.get(0));
                } else {
                  // 复合主键, 需要手动配置主键
                }
                res.add(tableInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


}
