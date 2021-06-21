package com.xushu.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xin
 */
@SpringBootApplication
@MapperScan("com.xushu.account.mapper")
public class DemoAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoAccountApplication.class, args);
    }

}
