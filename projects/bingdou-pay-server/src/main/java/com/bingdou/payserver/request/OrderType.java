package com.bingdou.payserver.request;

/**
 * Created by gaoshan on 16/12/25.
 */
public enum OrderType {

    RECHARGE(1), CONSUME(2);

    private int index;

    OrderType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
