package com.xushu.pay.enums;

/**
 * @author xushu
 */
public enum PayStatus {

    INIT(0),
    SUCCESS(1);

    private int status;

    PayStatus(int status) {
        this.status = status;
    }

    public int value() {
        return this.status;
    }

}
