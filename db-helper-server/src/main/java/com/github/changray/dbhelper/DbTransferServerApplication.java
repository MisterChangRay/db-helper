package com.github.changray.dbhelper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.github.changray.dbhelper")
public class DbTransferServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbTransferServerApplication.class, args);
    }

}
