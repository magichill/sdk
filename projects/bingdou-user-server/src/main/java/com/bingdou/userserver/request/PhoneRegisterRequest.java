package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 手机注册请求类
 */
public class PhoneRegisterRequest extends BaseRequest {

    /**
     * 手机号
     */
    @SerializedName("mobile")
    private String mobile;
    /**
     * 密码
     */
    @SerializedName("password")
    private String password;
    /**
     * 验证码
     */
    @SerializedName("code")
    private String code;

    @Override
    protected String getLoggerName() {
        return "PhoneRegisterRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        PhoneRegisterRequest request = JsonUtil.jsonStr2Bean(requestString,
                PhoneRegisterRequest.class);
        this.mobile = request.getMobile();
        this.password = request.getPassword();
        this.code = request.getCode();
        return request;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
