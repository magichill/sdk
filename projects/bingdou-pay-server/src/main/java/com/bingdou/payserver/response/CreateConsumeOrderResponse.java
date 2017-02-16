package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

public class CreateConsumeOrderResponse extends CreateOrderResponse {

    @SerializedName("need_pay_money")
    private float needPayMoney = 0f;

    public float getNeedPayMoney() {
        return needPayMoney;
    }

    public void setNeedPayMoney(float needPayMoney) {
        this.needPayMoney = needPayMoney;
    }
}
