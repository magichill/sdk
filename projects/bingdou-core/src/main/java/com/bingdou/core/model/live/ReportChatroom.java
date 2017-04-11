package com.bingdou.core.model.live;


/**
 * Created by gaoshan on 17/4/10.
 */
public class ReportChatroom {

    private Integer id;

    private Integer liveId;

    private Integer audienceCount;

    private Integer viewCount;

    private Integer addAudience;

    public Integer getAddAudience() {
        return addAudience;
    }

    public void setAddAudience(Integer addAudience) {
        this.addAudience = addAudience;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
