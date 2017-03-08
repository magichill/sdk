package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.google.gson.annotations.SerializedName;

/**
 * 创建订单请求父类
 * Created by gaoshan on 16/3/9.
 */
public abstract class CreateOrderRequest extends BaseRequest {

    @SerializedName("account")
    protected String account;
    @SerializedName("money")
    protected float money;
    @SerializedName("pay_type")
    protected int payType;
    @SerializedName("open_id")
    protected String openId;
    @SerializedName("goods_name")
    protected String goodsName;
    @SerializedName("goods_desc")
    protected String goodsDescription;
    @SerializedName("other")
    protected String other;
    @SerializedName("return_url")
    protected String returnUrl;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
