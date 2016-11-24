package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * 注销登陆
 */
public class LogoutRequest extends BaseRequest {

    /**
     * 用户账号,可以是登录名 / CP ID
     */
    @SerializedName("account")
    private String account;

    @Override
    protected String getLoggerName() {
        return "LogoutRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        LogoutRequest request = JsonUtil.jsonStr2Bean(requestString, LogoutRequest.class);
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
