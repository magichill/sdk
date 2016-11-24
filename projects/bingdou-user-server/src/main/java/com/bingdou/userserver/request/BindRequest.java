package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * 绑定邮箱或者手机请求
 */
public class BindRequest extends BaseRequest {

    /**
     * 用户账号,可以是登录名或者CP ID
     */
    @SerializedName("account")
    private String account;
    /**
     * 手机号或者邮箱
     */
    @SerializedName("phone_or_email")
    private String phoneOrMail;
    /**
     * 验证码
     */
    @SerializedName("validation_code")
    private String validationCode;

    @Override
    protected String getLoggerName() {
        return "BindRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        BindRequest request = JsonUtil.jsonStr2Bean(requestString, BindRequest.class);
        this.account = request.getAccount();
        this.phoneOrMail = request.getPhoneOrMail();
        this.validationCode = request.getValidationCode();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhoneOrMail() {
        return phoneOrMail;
    }

    public void setPhoneOrMail(String phoneOrMail) {
        this.phoneOrMail = phoneOrMail;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }


}
