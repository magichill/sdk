package com.bingdou.core.model.live;

/**
 * Created by gaoshan on 17/4/10.
 */
public class ReportShare {

    private Integer id;
    private Integer broadcasterId;
    private Integer liveId;
    private Integer shareType;
    private Integer shareChannel;
    private Integer shareCount;
    private Integer mid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBroadcasterId() {
        return broadcasterId;
    }

    public void setBroadcasterId(Integer broadcasterId) {
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

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }
}
