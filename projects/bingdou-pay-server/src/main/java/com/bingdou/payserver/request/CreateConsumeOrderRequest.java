package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * 创建消费订单请求(直充直消)
 * Created by gaoshan on 16/12/3.
 */
public class CreateConsumeOrderRequest extends CreateOrderRequest {

    @SerializedName("user_order_id")
    private String userOrderId;
    @SerializedName("user_order_param")
    private String userOrderParam;
    @SerializedName("voucher_id")
    private int voucherId;
    @SerializedName("is_contain_user_money")
    private int isContainUserMoney;

    @Override
    protected String getLoggerName() {
        return "CreateConsumeOrderRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CreateConsumeOrderRequest request = JsonUtil.jsonStr2Bean(requestString, CreateConsumeOrderRequest.class);
        this.account = request.getAccount();
        this.money = request.getMoney();
        this.payType = request.getPayType();
        this.goodsName = request.getGoodsName();
        this.goodsDescription = request.getGoodsDescription();
        this.other = request.getOther();
        this.userOrderId = request.getUserOrderId();
        this.userOrderParam = request.getUserOrderParam();
        this.voucherId = request.getVoucherId();
        this.isContainUserMoney = request.getIsContainUserMoney();
        this.returnUrl = request.getReturnUrl();
        return request;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getUserOrderParam() {
        return userOrderParam;
    }

    public void setUserOrderParam(String userOrderParam) {
        this.userOrderParam = userOrderParam;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getIsContainUserMoney() {
        return isContainUserMoney;
    }

    public void setIsContainUserMoney(int isContainUserMoney) {
        this.isContainUserMoney = isContainUserMoney;
    }

}
