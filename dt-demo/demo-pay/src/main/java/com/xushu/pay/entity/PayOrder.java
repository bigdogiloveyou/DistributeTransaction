package com.xushu.pay.entity;

import lombok.Data;

/**
 * @author xushu
 */
@Data
public class PayOrder {

    private long id;

    private String uid;

    private String orderId;

    private int status;

    private long amount;

}
