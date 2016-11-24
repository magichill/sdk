package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * 邮箱、短信下发验证码请求类
 */
public class SendCodeRequest extends BaseRequest {

    /**
     * 手机或者邮箱
     */
    @SerializedName("phone_or_email")
    private String phoneOrEmail;
    /**
     * 发送类型:1:绑定,2:忘记密码,3:手机号注册
     */
    @SerializedName("type")
    private int type;
    /**
     * 用户账号,可以是用户登录名或者CPID,绑定类型必须传
     */
    @SerializedName("account")
    private String account;

    @Override
    protected String getLoggerName() {
        return "SendCodeRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        SendCodeRequest request = JsonUtil.jsonStr2Bean(requestString, SendCodeRequest.class);
        this.phoneOrEmail = request.getPhoneOrEmail();
        this.type = request.getType();
        this.account = request.getAccount();
        return request;
    }

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
