package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-4.
 */
public class GetLiveInfoRequest extends BaseRequest {

    @SerializedName("live_id")
    private int liveId;

    @SerializedName("user_id")
    private String userId;

    @Override
    protected String getLoggerName() {
        return "GetLiveInfoRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetLiveInfoRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetLiveInfoRequest.class);
        this.liveId = request.getLiveId();
        this.userId = request.getUserId();
        return request;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
