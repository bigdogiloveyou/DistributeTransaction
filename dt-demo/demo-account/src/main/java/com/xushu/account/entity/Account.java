package com.xushu.account.entity;

import lombok.Data;

/**
 * @author xushu
 */
@Data
public class Account {

    private long id;

    private String uid;

    private long availableAmount;

    private long freezeAmount;

}
