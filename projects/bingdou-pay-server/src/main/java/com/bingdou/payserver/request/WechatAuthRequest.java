package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/9.
 */
public class WechatAuthRequest extends BaseRequest{

    @SerializedName("account")
    private String account;

    @SerializedName("url")
    private String url;

    @Override
    protected String getLoggerName() {
        return "WechatAuthRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        WechatAuthRequest request = JsonUtil.jsonStr2Bean(requestString, WechatAuthRequest.class);
        this.account = request.getAccount();
        this.url = request.getUrl();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
