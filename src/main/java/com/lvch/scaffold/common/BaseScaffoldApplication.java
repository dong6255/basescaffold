package com.lvch.scaffold.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.lvch")
@MapperScan({"com.lvch.scaffold.common.**.mapper"})
@SpringBootApplication
@ServletComponentScan
public class BaseScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseScaffoldApplication.class,args);
    }

}