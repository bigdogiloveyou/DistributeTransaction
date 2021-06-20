package com.xushu.pay.entity;

import lombok.Data;

/**
 * @author xushu
 */
@Data
public class PayChannel {

    private long id;

    private String uid;

    private String orderId;

    private int channelId;

    private int status;

    private long amount;

    private String assetId;

}
