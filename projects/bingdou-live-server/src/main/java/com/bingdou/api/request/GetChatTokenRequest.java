package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/2.
 */
public class GetChatTokenRequest extends BaseRequest{

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
        return "GetChatToken";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetChatTokenRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetChatTokenRequest.class);
        this.account = request.getAccount();
        return request;
    }
}
