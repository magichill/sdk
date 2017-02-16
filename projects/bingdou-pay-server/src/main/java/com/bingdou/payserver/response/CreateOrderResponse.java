package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.service.pay.paytype.WxResponse;

/**
 * Created by gaoshan on 17/01/22.
 */
public abstract class CreateOrderResponse {

    @SerializedName("order_id")
    protected String orderId = "";
    @SerializedName("weixin")
    protected WxResponse wxResponse;
    @SerializedName("pay_request_url")
    protected String payRequestUrl;

//    @SerializedName("heepay_weixin_sdk")
//    protected HeepaySdkWxResponse heepaySdkWxResponse;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public WxResponse getWxResponse() {
        return wxResponse;
    }

    public void setWxResponse(WxResponse wxResponse) {
        this.wxResponse = wxResponse;
    }

    public String getPayRequestUrl() {
        return payRequestUrl;
    }

    public void setPayRequestUrl(String payRequestUrl) {
        this.payRequestUrl = payRequestUrl;
    }

//    public HeepaySdkWxResponse getHeepaySdkWxResponse() {
//        return heepaySdkWxResponse;
//    }
//
//    public void setHeepaySdkWxResponse(HeepaySdkWxResponse heepaySdkWxResponse) {
//        this.heepaySdkWxResponse = heepaySdkWxResponse;
//    }
}
