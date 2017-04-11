package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/10.
 */
public class ReportChatRoomRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("audience_count")
    private Integer audienceCount;

    @SerializedName("view_count")
    private Integer viewCount;

    @Override
    protected String getLoggerName() {
        return "ReportChatRoomRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ReportChatRoomRequest request = JsonUtil.jsonStr2Bean(requestString, ReportChatRoomRequest.class);
        this.account = request.getAccount();
        this.liveId = request.getLiveId();
        this.audienceCount = request.getAudienceCount();
        this.viewCount = request.getViewCount();
        return request;
    }

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

    public Integer getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(Integer audienceCount) {
        this.audienceCount = audienceCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
