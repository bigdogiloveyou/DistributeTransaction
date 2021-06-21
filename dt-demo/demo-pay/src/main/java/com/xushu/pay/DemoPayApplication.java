package com.xushu.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xin
 */
@SpringBootApplication
@MapperScan({"com.xushu.pay.mapper", "com.xushu.dt.log.repository.mybatis.mapper"})
public class DemoPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoPayApplication.class, args);
    }

}
