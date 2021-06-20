package com.xushu.coupon.service;

import com.xushu.common.coupon.dto.CouponDTO;

/**
 * @author xushu
 */
public interface CouponService {

    boolean freeze(CouponDTO couponDTO);

    boolean commit(CouponDTO couponDTO);

    boolean unfreeze(CouponDTO couponDTO);

}
