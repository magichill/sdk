package com.bingdou.api.response;

import com.bingdou.core.model.live.Live;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

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

    @SerializedName("video_type")
    private int videoType;

    @SerializedName("share_count")
    private int shareCount;

    @SerializedName("playback_view_count")
    private int playBackCount;

    @SerializedName("chatroom_id")
    private long chatRoomId;

//    @SerializedName("tags")
//    private List<String> tags;

    @SerializedName("tag")
    private LiveTagResponse liveTagResponse;

    @SerializedName("start_at")
    private Long startAt;

    @SerializedName("end_at")
    private Long endAt;

    @SerializedName("create_at")
    private Long createAt;

    @SerializedName("update_at")
    private Long updateAt;

//    @SerializedName("preview_created_at")
//    private long previewCreateAt;
//
//    @SerializedName("preview_updated_at")
//    private long previewUpdateAt;

    @SerializedName("playback_duration")
    private int duration;

    @SerializedName("publish_url")
    private String pushUrl = "";

    @SerializedName("play_url")
    private String playUrl = "";

    @SerializedName("h5_url")
    private String h5Url = "";

    @SerializedName("replay_url")
    private String replayUrl = "";

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

    public LiveTagResponse getLiveTagResponse() {
        return liveTagResponse;
    }

    public void setLiveTagResponse(LiveTagResponse liveTagResponse) {
        this.liveTagResponse = liveTagResponse;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
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

    public String getReplayUrl() {
        return replayUrl;
    }

    public void setReplayUrl(String replayUrl) {
        this.replayUrl = replayUrl;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
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
        setH5Url(live.getH5Url());
        setTitle(live.getLiveTitle());
        setReplayUrl(live.getReplayUrl());
        setStartAt(live.getStartTime()==null?0:live.getStartTime().getTime());
        setEndAt(live.getEndTime()==null?0:live.getEndTime().getTime());
        setCreateAt(live.getCreateTime()==null?0:live.getCreateTime().getTime());
        setUpdateAt(live.getUpdateTime()==null?0:live.getUpdateTime().getTime());
//        List tagList = Lists.newArrayList();
//        String tags = live.getTags();
//        if(!StringUtils.isEmpty(tags)) {
//            String[] tagArg = tags.split(",");
//            for(String tag : tagArg){
//                tagList.add(tag);
//            }
//        }
        LiveTagResponse liveTagResponse = new LiveTagResponse();
        liveTagResponse.parseFromTag(live.getTag());
        setLiveTagResponse(liveTagResponse);
        setVideoType(live.getLiveType());
    }
}
