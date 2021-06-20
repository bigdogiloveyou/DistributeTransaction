package com.xushu.pay.service;

import com.xushu.pay.entity.PayChannel;
import com.xushu.pay.entity.PayOrder;

import java.util.List;

/**
 * @author xushu
 */
public interface PayService {

    /**
     * 余额加券支付
     *
     * @param payOrder
     * @param channels
     * @return
     */
    boolean accountAndCouponPay(PayOrder payOrder, List<PayChannel> channels);

}
