package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * 充值/消费记录查询(返回三个月内的记录)
 */
public class GetOrderRecordListRequest extends BaseRequest {

    @SerializedName("account")
    private String account;
    @SerializedName("order_type")
    private int orderType;

    @Override
    protected String getLoggerName() {
        return "GetOrderRecordListRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetOrderRecordListRequest request = JsonUtil.jsonStr2Bean(requestString, GetOrderRecordListRequest.class);
        this.account = request.getAccount();
        this.orderType = request.getOrderType();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
