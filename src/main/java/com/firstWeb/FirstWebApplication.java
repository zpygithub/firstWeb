package com.firstWeb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zpy on 2017/2/13.
 */
@SpringBootApplication
@MapperScan("com.firstWeb.mapper")
public class FirstWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstWebApplication.class, args);
    }
}
