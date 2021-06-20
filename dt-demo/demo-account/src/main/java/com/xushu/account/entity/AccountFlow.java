package com.xushu.account.entity;

import lombok.Data;

/**
 * @author xushu
 */
@Data
public class AccountFlow {

    private long id;

    private String uid;

    private String orderId;

    private int status;

    private long amount;

}
