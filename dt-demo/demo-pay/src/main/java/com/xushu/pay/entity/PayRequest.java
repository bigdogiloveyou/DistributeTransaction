package com.xushu.pay.entity;

import lombok.Data;

import java.util.List;

/**
 * @author xushu
 */
@Data
public class PayRequest {

    private String uid;

    private String orderId;

    private long amount;

    private List<PayChannelRequest> channels;

}
