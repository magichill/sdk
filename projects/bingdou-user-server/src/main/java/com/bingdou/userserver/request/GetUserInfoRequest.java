package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 获取用户信息请求类
 */
public class GetUserInfoRequest extends BaseRequest {

    @SerializedName("query_account")
    private String queryAccount;
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

    public String getQueryAccount() {
        return queryAccount;
    }

    public void setQueryAccount(String queryAccount) {
        this.queryAccount = queryAccount;
    }

    @Override
    protected String getLoggerName() {
        return "GetUserInfoRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetUserInfoRequest request = JsonUtil.jsonStr2Bean(requestString, GetUserInfoRequest.class);
        this.account = request.getAccount();
        this.queryAccount = request.getQueryAccount();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
