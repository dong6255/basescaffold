package com.lvch.scaffold.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan("com.lvch")
@SpringBootApplication
@ServletComponentScan
public class BaseScaffoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseScaffoldApplication.class,args);
    }

}