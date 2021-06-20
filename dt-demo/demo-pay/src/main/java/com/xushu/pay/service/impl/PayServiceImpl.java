package com.xushu.pay.service.impl;

import com.xushu.common.account.dto.AccountDTO;
import com.xushu.common.account.service.AccountDubboService;
import com.xushu.common.coupon.dto.CouponDTO;
import com.xushu.common.coupon.service.CouponDubboService;
import com.xushu.dt.client.tm.TransactionManager;
import com.xushu.pay.entity.PayChannel;
import com.xushu.pay.entity.PayOrder;
import com.xushu.pay.enums.PayChannelEnum;
import com.xushu.pay.mapper.PayChannelMapper;
import com.xushu.pay.mapper.PayOrderMapper;
import com.xushu.pay.service.PayService;
import com.google.common.base.Preconditions;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xushu
 */
@Service
public class PayServiceImpl implements PayService {

    @Reference
    private AccountDubboService accountDubboService;

    @Reference
    private CouponDubboService couponDubboService;

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private PayChannelMapper payChannelMapper;

    @Resource
    private TransactionManager dtTransactionManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean accountAndCouponPay(PayOrder payOrder, List<PayChannel> channels) {
        AccountDTO accountDTO = buildAccountDTO(payOrder, channels);
        CouponDTO couponDTO = buildCouponDTO(payOrder, channels);
        dtTransactionManager.start();
        payOrderMapper.insert(payOrder);
        payChannelMapper.insert(channels);
        Preconditions.checkArgument(accountDubboService.freeze(accountDTO), "余额冻结失败");
        //todo test
//        int i = 10/0;
        Preconditions.checkArgument(couponDubboService.freeze(couponDTO), "券冻结失败");
        return true;
    }


    private AccountDTO buildAccountDTO(PayOrder payOrder, List<PayChannel> channels) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUid(payOrder.getUid());
        accountDTO.setOrderId(payOrder.getOrderId());
        for (PayChannel channel : channels) {
            if (channel.getChannelId() == PayChannelEnum.ACCOUNT.value()) {
                accountDTO.setAmount(channel.getAmount());
            }
        }
        return accountDTO;
    }

    private CouponDTO buildCouponDTO(PayOrder payOrder, List<PayChannel> channels) {
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setUid(payOrder.getUid());
        couponDTO.setOrderId(payOrder.getOrderId());
        for (PayChannel channel : channels) {
            if (channel.getChannelId() == PayChannelEnum.COUPON.value()) {
                couponDTO.setAmount(channel.getAmount());
                couponDTO.setCouponId(channel.getAssetId());
            }
        }
        return couponDTO;
    }
}
