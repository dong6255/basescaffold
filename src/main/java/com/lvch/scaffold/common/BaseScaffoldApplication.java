package com.lvch.scaffold.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.lvch")
@SpringBootApplication
public class BaseScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseScaffoldApplication.class,args);
    }

}