package com.bingdou.payserver.request;

/**
 * Created by gaoshan on 17/3/25.
 */
public class AppleInnerPayProduct {

    private String id;
    private float amount;

    public AppleInnerPayProduct(String id, float amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
