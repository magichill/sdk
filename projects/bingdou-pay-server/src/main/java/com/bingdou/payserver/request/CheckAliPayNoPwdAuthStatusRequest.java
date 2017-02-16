package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

public class CheckAliPayNoPwdAuthStatusRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @Override
    protected String getLoggerName() {
        return "CheckAliPayNoPwdAuthStatusRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CheckAliPayNoPwdAuthStatusRequest request = JsonUtil.jsonStr2Bean(requestString,
                CheckAliPayNoPwdAuthStatusRequest.class);
        this.account = request.getAccount();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
