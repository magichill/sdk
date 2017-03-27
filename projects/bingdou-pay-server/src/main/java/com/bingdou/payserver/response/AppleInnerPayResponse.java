package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * 苹果应用内支付返回
 * Created by gaoshan on 17/3/25.
 */
public class AppleInnerPayResponse extends VerifyOrderResponse {

    @SerializedName("order_id")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
