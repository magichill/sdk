package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * ���Ѷ�����ѯ������
 * Created by gaoshan on 16/12/4.
 */
public class GetConsumeOrderRequest extends BaseRequest {

    /**
     * CP������
     */
    @SerializedName("user_order_id")
    private String userOrderId;
    /**
     * ��¼����cpId
     */
    @SerializedName("account")
    private String account;

    @Override
    protected String getLoggerName() {
        return "GetConsumeOrderRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetConsumeOrderRequest request = JsonUtil.jsonStr2Bean(requestString, GetConsumeOrderRequest.class);
        this.userOrderId = request.getUserOrderId();
        this.account = request.getAccount();
        return request;
    }

    public String getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(String userOrderId) {
        this.userOrderId = userOrderId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
