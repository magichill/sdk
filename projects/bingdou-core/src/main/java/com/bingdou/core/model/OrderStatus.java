package com.bingdou.core.model;

/**
 * Created by gaoshan on 16/11/22.
 */
public enum OrderStatus {

    NOT_PAY(0, "未支付"), PAYED(1, "已支付"), NOT_EXISTS(2, "不存在");

    private int index;
    private String name;

    OrderStatus(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static String getNameByIndex(int index) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (index == orderStatus.getIndex())
                return orderStatus.getName();
        }
        return "";
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
