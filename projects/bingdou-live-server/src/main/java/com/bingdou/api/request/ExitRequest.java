package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/2/27.
 */
public class ExitRequest extends BaseRequest {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("message_count")
    private Integer messageCount;

    @SerializedName("present_count")
    private Integer presentCount;

    @Override
    protected String getLoggerName() {
        return "ExitRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ExitRequest request = JsonUtil.jsonStr2Bean(requestString, ExitRequest.class);
        this.userId = request.getUserId();
        this.liveId = request.getLiveId();
        this.messageCount = request.getMessageCount();
        this.presentCount = request.getPresentCount();
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

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(Integer presentCount) {
        this.presentCount = presentCount;
    }
}
