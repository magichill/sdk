package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 注册请求类
 *
 */
public class RegisterRequest extends BaseRequest {

    /**
     * 用户名
     */
    @SerializedName("login_name")
    private String loginName;
    /**
     * 密码
     */
    @SerializedName("password")
    private String password;

    @Override
    protected String getLoggerName() {
        return "RegisterRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        RegisterRequest request = JsonUtil.jsonStr2Bean(requestString,
                RegisterRequest.class);
        this.loginName = request.getLoginName();
        this.password = request.getPassword();
        return request;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
