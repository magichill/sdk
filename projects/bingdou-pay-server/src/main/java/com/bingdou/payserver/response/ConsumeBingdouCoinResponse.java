package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/03/12.
 */
public class ConsumeBingdouCoinResponse {

    @SerializedName("money")
    private float money = 0f;
    @SerializedName("status")
    private int status = 0;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
