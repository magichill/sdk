package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/1/24.
 */
public class PhoneLoginRequest extends BaseRequest {

    /**
     * 手机号
     */
    //TODO phone_number
    @SerializedName("mobile")
    private String mobile;

    /**
     * 验证码
     */
    //TODO captcha
    @SerializedName("code")
    private String code;

    //TODO user_name & password

    @Override
    protected String getLoggerName() {
        return "PhoneLoginRequest";
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        PhoneLoginRequest request = JsonUtil.jsonStr2Bean(requestString,
                PhoneLoginRequest.class);
        this.mobile = request.getMobile();
        this.code = request.getCode();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
