package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.NumberUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/12.
 */
public class ConsumeBingdouCoinRequest extends BaseRequest {

    @SerializedName("account")
    private String account;
    @SerializedName("user_order_id")
    private String userOrderId;
    @SerializedName("order_money")
    private float orderMoney;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("goods_description")
    private String goodsDescription;

    @Override
    protected String getLoggerName() {
        return "ConsumeHaimaCoinRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ConsumeBingdouCoinRequest request = JsonUtil.jsonStr2Bean(requestString,
                ConsumeBingdouCoinRequest.class);
        this.account = request.getAccount();
        this.userOrderId = request.getUserOrderId();
        this.orderMoney = request.getOrderMoney();
        this.goodsName = request.getGoodsName();
        this.goodsDescription = request.getGoodsDescription();
        return request;
    }

    public int getOrderMoneyFen() {
        return NumberUtil.convertFenFromBingdou(this.getOrderMoney());
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
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

}
