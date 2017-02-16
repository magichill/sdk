package com.bingdou.core.utils;

/**
 * Created by gaoshan on 16/11/29.
 */
public class DataLogPayObject {

    private int payType = -1;
    private String orderId = "";
    private int orderMoney = 0;
    private int payedMoney = 0;
    private int orderType = -1;
    private int orderStatus = -1;
    private int activityType = -1;
    private int isUseVoucher = -1;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(int orderMoney) {
        this.orderMoney = orderMoney;
    }

    public int getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(int payedMoney) {
        this.payedMoney = payedMoney;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getIsUseVoucher() {
        return isUseVoucher;
    }

    public void setIsUseVoucher(int isUseVoucher) {
        this.isUseVoucher = isUseVoucher;
    }
}
