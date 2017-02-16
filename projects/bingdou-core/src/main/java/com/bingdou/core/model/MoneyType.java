package com.bingdou.core.model;

/**
 * ’Àªß¿‡–Õ
 * Created by gaoshan on 16/12/24.
 */
public enum MoneyType {

    BINGDOU_MONEY(1), VIRTUAL_MONEY(2);

    private int index;

    MoneyType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
