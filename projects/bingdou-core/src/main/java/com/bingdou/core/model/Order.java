package com.bingdou.core.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by gaoshan on 16/11/22.
 */
public abstract class Order {

    protected Integer id;
    protected Integer userId;
    protected Integer appMemberId;
    protected String orderId;
    protected long orderTime;
    protected Integer status;
    protected String goodsName;
    protected Integer payedMoney;
    protected long payedTime;
    protected Integer payType;
    protected String remark;
    protected String appId;
    protected String channel = "";
    protected String sdkVersion;
    protected String osName;
    protected String userOrderId;
    protected String unionPayOrderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAppMemberId() {
        return appMemberId;
    }

    public void setAppMemberId(Integer appMemberId) {
        this.appMemberId = appMemberId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(Integer payedMoney) {
        this.payedMoney = payedMoney;
    }

    public long getPayedTime() {
        return payedTime;
    }

    public void setPayedTime(long payedTime) {
        this.payedTime = payedTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        if (StringUtils.isEmpty(channel))
            return;
        this.channel = channel;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getUnionPayOrderId() {
        return unionPayOrderId;
    }

    public void setUnionPayOrderId(String unionPayOrderId) {
        this.unionPayOrderId = unionPayOrderId;
    }
}
