package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/01/22.
 */
public class CreateRechargeOrderResponse extends CreateOrderResponse {

    @SerializedName("money")
    private float money = 0f;
    @SerializedName("can_use_recharge_card")
    private int canUseRechargeCard;

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getCanUseRechargeCard() {
        return canUseRechargeCard;
    }

    public void setCanUseRechargeCard(int canUseRechargeCard) {
        this.canUseRechargeCard = canUseRechargeCard;
    }
}
