package com.xushu.common.coupon.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xushu
 */
@Data
public class CouponDTO implements Serializable {

    private static final long serialVersionUID = -5450347693955573757L;

    private String uid;

    private String orderId;

    private String couponId;

    private long amount;

}
