package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 登录请求类
 */
public class LoginRequest extends BaseRequest {

    /**
     * 用户账号,可以是登录名 / 手机 / 邮箱
     */
    @SerializedName("account")
    private String account;
    /**
     * 密码
     */
    @SerializedName("password")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected String getLoggerName() {
        return "LoginRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        LoginRequest request = JsonUtil.jsonStr2Bean(requestString,
                LoginRequest.class);
        this.account = request.getAccount();
        this.password = request.getPassword();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
