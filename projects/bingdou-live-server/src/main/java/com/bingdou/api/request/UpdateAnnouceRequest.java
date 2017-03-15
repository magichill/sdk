package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/14.
 */
public class UpdateAnnouceRequest extends BaseRequest{

    @SerializedName("user_id")
    private String userId;
    @SerializedName("live_id")
    private int liveId;
    @SerializedName("cover_url")
    private String coverUrl;
    @SerializedName("title")
    private String title;
    @SerializedName("password")
    private String password;
    @SerializedName("price")
    private Float price ;
    @SerializedName("orientation")
    private Integer orientation;
    @SerializedName("start_at")
    private Long startAt;
    @SerializedName("reward_percent")
    private Integer percent = 0;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    @Override
    protected String getLoggerName() {
        return "UpdateAnnounceRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        UpdateAnnouceRequest request = JsonUtil.jsonStr2Bean(requestString, UpdateAnnouceRequest.class);
        this.userId = request.getUserId();
        this.coverUrl = request.getCoverUrl();
        this.title = request.getTitle();
        this.orientation = request.getOrientation();
        this.startAt = request.getStartAt();
        this.password = request.getPassword();
        this.price = request.getPrice();
        this.percent = request.getPercent();
        this.liveId = request.getLiveId();
        return request;
    }
}
