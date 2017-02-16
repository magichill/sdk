package com.bingdou.core.service.pay.paytype;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16/12/8.
 */
public class WxResponse {

    @SerializedName("prepayid")
    private String preOrderId;
    @SerializedName("appid")
    private String wxAppId;
    @SerializedName("partnerid")
    private String partnerId;
    @SerializedName("noncestr")
    private String nonceStr;
    @SerializedName("timestamp")
    private long timestamp;
    @SerializedName("package")
    private String wxPackage;
    @SerializedName("sign")
    private String sign;

    public String getPreOrderId() {
        return preOrderId;
    }

    public void setPreOrderId(String preOrderId) {
        this.preOrderId = preOrderId;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getWxPackage() {
        return wxPackage;
    }

    public void setWxPackage(String wxPackage) {
        this.wxPackage = wxPackage;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
