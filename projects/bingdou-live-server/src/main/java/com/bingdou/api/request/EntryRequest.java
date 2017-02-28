package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/2/27.
 */
public class EntryRequest extends BaseRequest {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("password")
    private String password;

    @Override
    protected String getLoggerName() {
        return "EntryRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        EntryRequest request = JsonUtil.jsonStr2Bean(requestString, EntryRequest.class);
        this.userId = request.getUserId();
        this.liveId = request.getLiveId();
        this.password = request.getPassword();
        return request;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
