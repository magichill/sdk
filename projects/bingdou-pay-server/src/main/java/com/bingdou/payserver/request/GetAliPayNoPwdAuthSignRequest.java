package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

public class GetAliPayNoPwdAuthSignRequest extends BaseRequest {

    @SerializedName("account")
    private String account;
    @SerializedName("return_url")
    private String returnUrl;

    @Override
    protected String getLoggerName() {
        return "GetAliPayNoPwdAuthSignRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetAliPayNoPwdAuthSignRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetAliPayNoPwdAuthSignRequest.class);
        this.account = request.getAccount();
        this.returnUrl = request.getReturnUrl();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
