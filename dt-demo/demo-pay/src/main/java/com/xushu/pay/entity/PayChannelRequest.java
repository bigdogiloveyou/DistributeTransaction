package com.xushu.pay.entity;

import lombok.Data;

/**
 * @author xushu
 */
@Data
public class PayChannelRequest {

    private int channelId;

    private long amount;

    private String assetId;

}
