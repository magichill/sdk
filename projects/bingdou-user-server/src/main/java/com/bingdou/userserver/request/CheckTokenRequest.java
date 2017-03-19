package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/17.
 */
public class CheckTokenRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    protected String getLoggerName() {
        return "CheckTokenRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CheckTokenRequest request = JsonUtil.jsonStr2Bean(requestString, CheckTokenRequest.class);
        this.account = request.getAccount();
        return request;
    }
}
