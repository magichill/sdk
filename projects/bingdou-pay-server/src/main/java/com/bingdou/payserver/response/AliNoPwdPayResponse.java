package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

public class AliNoPwdPayResponse {

    @SerializedName("order_id")
    private String orderId = "";
    @SerializedName("pay_result")
    private int payResult;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPayResult() {
        return payResult;
    }

    public void setPayResult(int payResult) {
        this.payResult = payResult;
    }
}
