package com.xushu.common.coupon.service;

import com.xushu.common.coupon.dto.CouponDTO;
import com.xushu.dt.api.annotation.TwoPhaseCommit;
import com.xushu.dt.api.context.DTParam;

/**
 * @author xushu
 */
public interface CouponDubboService {

    @TwoPhaseCommit(name = "useCoupon", confirmMethod = "commit", cancelMethod = "unfreeze")
    boolean freeze(CouponDTO couponDTO);

    void commit(DTParam dtParam, CouponDTO couponDTO);

    void unfreeze(DTParam dtParam, CouponDTO couponDTO);

}
