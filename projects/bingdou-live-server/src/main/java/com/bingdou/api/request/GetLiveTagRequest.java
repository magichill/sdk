package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/4.
 */
public class GetLiveTagRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @Override
    protected String getLoggerName() {
        return "GetLiveTagRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetLiveTagRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetLiveTagRequest.class);
        this.account = request.getAccount();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
