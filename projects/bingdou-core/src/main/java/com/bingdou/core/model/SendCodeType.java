package com.bingdou.core.model;

/**
 * 验证码类型枚举
 */
public enum SendCodeType {

    BIND(1), LOST_PASSWORD(2), PHONE_REGISTER(3), MODIFY_PASSWORD(5);

    private int index;

    SendCodeType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
