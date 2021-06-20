package com.xushu.coupon.entity;

import lombok.Data;

/**
 * @author xushu
 */
@Data
public class Coupon {

    private long id;

    private String uid;

    private String couponId;

    private long amount;

    private int status;

    private String orderId;

}
