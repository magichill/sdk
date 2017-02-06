package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/1/31.
 */
public class CertificateRequest extends BaseRequest {
    /**
     * 用户账号,可以是用户名、邮箱或手机号或CP ID
     */
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
        return "CertificateRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CertificateRequest request = JsonUtil.jsonStr2Bean(requestString, CertificateRequest.class);
        this.account = request.getAccount();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
