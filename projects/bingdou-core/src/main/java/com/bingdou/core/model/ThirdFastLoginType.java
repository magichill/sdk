package com.bingdou.core.model;

/**
 * 第三方登录授权
 * Created by gaoshan on 17/2/21.
 */
public enum ThirdFastLoginType {

    ALI(1),WEIXIN(2);

    private int index;

    ThirdFastLoginType(int index) {
        this.index = index;
    }

    public static ThirdFastLoginType getByIndex(int index) {
        for (ThirdFastLoginType type : ThirdFastLoginType.values()) {
            if (index == type.getIndex()) {
                return type;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
