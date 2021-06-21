package com.xushu.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xin
 */
@SpringBootApplication
@MapperScan("com.xushu.coupon.mapper")
public class DemoCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoCouponApplication.class, args);
    }

}
