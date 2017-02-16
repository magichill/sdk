package com.bingdou.core.model;

/**
 * 支付宝免密授权状态
 * Created by gaoshan on 16/12/28.
 */
public enum AliPayNoPwdAuthStatus {

    /**
     * 解约
     */
    CANCEL(-1, "解约"),
    /**
     * 未签约,创建签约订单
     */
    CREATE_SIGN(0, "未签约，默认值"),
    /**
     * 已经签约
     */
    SIGNED(1, "NORMAL"),
    /**
     * 暂存
     */
    TEMPORARY(2, "TEMP"),
    /**
     * 暂停
     */
    PAUSE(3, "STOP");

    private int index;
    private String name;

    AliPayNoPwdAuthStatus(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static AliPayNoPwdAuthStatus getByName(String name) {
        for (AliPayNoPwdAuthStatus status : AliPayNoPwdAuthStatus.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }

    public static AliPayNoPwdAuthStatus getByIndex(int index) {
        for (AliPayNoPwdAuthStatus status : AliPayNoPwdAuthStatus.values()) {
            if (index == status.getIndex()) {
                return status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
