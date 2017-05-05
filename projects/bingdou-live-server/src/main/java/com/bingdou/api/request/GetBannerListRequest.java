package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/22.
 */
public class GetBannerListRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @Override
    protected String getLoggerName() {
        return "GetBannerListRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetBannerListRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetBannerListRequest.class);
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
