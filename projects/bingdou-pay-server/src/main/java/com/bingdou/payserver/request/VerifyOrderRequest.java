package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * 验证订单请求
 * Created by gaoshan on 17/11/25.
 */
public class VerifyOrderRequest extends BaseRequest {

    @SerializedName("order_id")
    private String orderId;
    @SerializedName("account")
    private String account;
    @SerializedName("order_type")
    private int orderType;
    @SerializedName("pay_type")
    private int payType;

    @Override
    protected String getLoggerName() {
        return "VerifyOrderRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        VerifyOrderRequest request = JsonUtil.jsonStr2Bean(requestString, VerifyOrderRequest.class);
        this.orderId = request.getOrderId();
        this.account = request.getAccount();
        this.orderType = request.getOrderType();
        this.payType = request.getPayType();
        return request;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
