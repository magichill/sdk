package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * 查询消费订单响应类
 * Created by gaoshan on 17/1/4.
 */
public class GetConsumeOrderResponse {

    @SerializedName("user_order_id")
    private String userOrderId = "";
    @SerializedName("money")
    private Float money = 0f;
    @SerializedName("order_money")
    private Float orderMoney = 0f;
    @SerializedName("status")
    private Integer status = 0;
    @SerializedName("vip_level")
    private Integer vipLevel = 0;
    @SerializedName("vip_up_need_money")
    private Float vipUpNeedMoney = 0f;
    @SerializedName("next_vip_level_money")
    private Float nextVipLevelMoney = 0f;

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Float getVipUpNeedMoney() {
        return vipUpNeedMoney;
    }

    public void setVipUpNeedMoney(Float vipUpNeedMoney) {
        this.vipUpNeedMoney = vipUpNeedMoney;
    }

    public Float getNextVipLevelMoney() {
        return nextVipLevelMoney;
    }

    public void setNextVipLevelMoney(Float nextVipLevelMoney) {
        this.nextVipLevelMoney = nextVipLevelMoney;
    }
}
