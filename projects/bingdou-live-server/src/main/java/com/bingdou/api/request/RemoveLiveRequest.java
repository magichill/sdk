package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-4.
 */
public class RemoveLiveRequest  extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private Integer liveId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    @Override
    protected String getLoggerName() {
        return "RemoveLiveRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        RemoveLiveRequest request = JsonUtil.jsonStr2Bean(requestString, RemoveLiveRequest.class);
        this.account = request.getAccount();
        this.liveId = request.getLiveId();
        return request;
    }
}
