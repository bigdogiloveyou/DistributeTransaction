package com.xushu.coupon.enums;

/**
 * @author xushu
 */
public enum CouponStatus {

    INIT(0),
    FREEZE(1),
    COMMIT(2);

    private int status;

    CouponStatus(int status) {
        this.status = status;
    }

    public int value() {
        return this.status;
    }

}
