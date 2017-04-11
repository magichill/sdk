package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/10.
 */
public class ReportShareRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("broadcaster_id")
    private String broadcasterId;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("share_type")
    private Integer shareType;

    @SerializedName("share_channel")
    private Integer shareChannel;
    @Override
    protected String getLoggerName() {
        return "ReportShareRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ReportShareRequest request = JsonUtil.jsonStr2Bean(requestString, ReportShareRequest.class);
        this.account = request.getAccount();
        this.liveId = request.getLiveId();
        this.broadcasterId = request.getBroadcasterId();
        this.shareType = request.getShareType();
        this.shareChannel = request.getShareChannel();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBroadcasterId() {
        return broadcasterId;
    }

    public void setBroadcasterId(String broadcasterId) {
        this.broadcasterId = broadcasterId;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public Integer getShareType() {
        return shareType;
    }

    public void setShareType(Integer shareType) {
        this.shareType = shareType;
    }

    public Integer getShareChannel() {
        return shareChannel;
    }

    public void setShareChannel(Integer shareChannel) {
        this.shareChannel = shareChannel;
    }
}
