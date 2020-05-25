package com.springboottest.serverstarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author LJS
 * @data 2020/5/14 18:00
 */
@SpringBootApplication
@MapperScan(basePackages = "com.springboottest.dao")
@ComponentScan(basePackages = {"com.springboottest"})
public class HelloSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class,args);
    }
}
