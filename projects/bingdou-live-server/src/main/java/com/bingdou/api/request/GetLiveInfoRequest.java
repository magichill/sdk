package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-4.
 */
public class GetLiveInfoRequest extends BaseRequest {

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @Override
    protected String getLoggerName() {
        return "GetLiveInfoRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetLiveInfoRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetLiveInfoRequest.class);
        this.liveId = request.getLiveId();
        this.account = request.getAccount();
        this.password = request.getPassword();
        return request;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
