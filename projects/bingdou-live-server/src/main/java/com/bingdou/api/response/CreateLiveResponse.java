package com.bingdou.api.response;

import com.bingdou.core.model.live.Live;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-4.
 */
public class CreateLiveResponse {


    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private int status;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("orientation")
    private int orientation;

    @SerializedName("audience_count")
    private int audienceCount;

    @SerializedName("like_count")
    private int likeCount;

    @SerializedName("share_count")
    private int shareCount;

    @SerializedName("playback_view_count")
    private int playBackCount;

    @SerializedName("chatroom_id")
    private long chatRoomId;

    @SerializedName("tags")
    private String tags;

    @SerializedName("start_at")
    private long startAt;

    @SerializedName("end_at")
    private long endAt;

    @SerializedName("preview_created_at")
    private long previewCreateAt;

    @SerializedName("preview_updated_at")
    private long previewUpdateAt;

    @SerializedName("playback_duration")
    private int duration;

    @SerializedName("publish_url")
    private String pushUrl;

    @SerializedName("play_url")
    private String playUrl;

    @SerializedName("h5_url")
    private String h5Url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getAudienceCount() {
        return audienceCount;
    }

    public void setAudienceCount(int audienceCount) {
        this.audienceCount = audienceCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getPlayBackCount() {
        return playBackCount;
    }

    public void setPlayBackCount(int playBackCount) {
        this.playBackCount = playBackCount;
    }

    public long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
    }

    public long getPreviewCreateAt() {
        return previewCreateAt;
    }

    public void setPreviewCreateAt(long previewCreateAt) {
        this.previewCreateAt = previewCreateAt;
    }

    public long getPreviewUpdateAt() {
        return previewUpdateAt;
    }

    public void setPreviewUpdateAt(long previewUpdateAt) {
        this.previewUpdateAt = previewUpdateAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public void parseFromLive(Live live){
        if(live == null){
            return ;
        }
        setId(live.getId());
        setOrientation(live.getOrientation());
        setStatus(live.getStatus());
        setCoverUrl(live.getLivePicture());
        setPushUrl(live.getPushStream());
        setPlayUrl(live.getPullStream());
        setH5Url(live.getReplayUrl());
        setTitle(live.getLiveTitle());
    }
}
