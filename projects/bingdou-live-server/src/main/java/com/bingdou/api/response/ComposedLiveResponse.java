package com.bingdou.api.response;

import com.bingdou.core.model.live.Live;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.util.List;


/**
 * Created by gaoshan on 17/2/18.
 */
public class ComposedLiveResponse {

    @SerializedName("user")
    private UserResponse userResponse;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private int status;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("playback_url")
    private String playbackUrl;

    @SerializedName("publish_url")
    private String publishUrl;

    @SerializedName("play_url")
    private String playUrl;

    @SerializedName("h5_url")
    private String h5Url;

    @SerializedName("orientation")
    private int orientation;

    @SerializedName("audience_count")
    private int audienceCount;

    @SerializedName("like_count")
    private int likeCount = 0;

    @SerializedName("share_count")
    private int shareCount = 0;

    @SerializedName("playback_view_count")
    private int playbackCount = 0;

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

    @SerializedName("video_type")
    private Integer videoType;

    @SerializedName("password")
    private String password;

    @SerializedName("price")
    private Integer price;

    @SerializedName("reward_percent")
    private Integer rewardPercent;

    @SerializedName("playback_duration")
    private long playbackDuration = 0;

    @SerializedName("lock")
    private Boolean lock;


    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPlaybackUrl() {
        return playbackUrl;
    }

    public void setPlaybackUrl(String playbackUrl) {
        this.playbackUrl = playbackUrl;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
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

    public int getPlaybackCount() {
        return playbackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        this.playbackCount = playbackCount;
    }

//    public List<String> getTags() {
//        return tags;
//    }
//
//    public void setTags(List<String> tags) {
//        this.tags = tags;
//    }


    public LiveTagResponse getLiveTagResponse() {
        return liveTagResponse;
    }

    public void setLiveTagResponse(LiveTagResponse liveTagResponse) {
        this.liveTagResponse = liveTagResponse;
    }

    public long getPlaybackDuration() {
        return playbackDuration;
    }

    public void setPlaybackDuration(long playbackDuration) {
        this.playbackDuration = playbackDuration;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
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

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRewardPercent() {
        return rewardPercent;
    }

    public void setRewardPercent(Integer rewardPercent) {
        this.rewardPercent = rewardPercent;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public void parseFromLive(Live live){
        setId(live.getId());
        setPlayUrl(live.getPullStream());
        setCoverUrl(live.getLivePicture());
        setTitle(live.getLiveTitle());
        setPublishUrl(live.getPushStream());
        setPlaybackUrl(live.getReplayUrl());
        setH5Url(live.getH5Url());
        setStatus(live.getStatus());
//        List tagList = Lists.newArrayList();
//        String tags = live.getTags();
//        if(!StringUtils.isEmpty(tags)) {
//            String[] tagArg = tags.split(",");
//            for(String tag : tagArg){
//                tagList.add(tag);
//            }
//        }
//        setTags(tagList);
        LiveTagResponse liveTagResponse = new LiveTagResponse();
        liveTagResponse.parseFromTag(live.getTag());
        setLiveTagResponse(liveTagResponse);
        setOrientation(live.getOrientation());
        setCreateAt(live.getCreateTime()==null?0:live.getCreateTime().getTime());
        setUpdateAt(live.getUpdateTime()==null?0:live.getUpdateTime().getTime());
        setStartAt(live.getStartTime()==null?0:live.getStartTime().getTime());
        setEndAt(live.getEndTime()==null?0:live.getEndTime().getTime());
        setPassword(live.getPassword());
        setPrice(live.getPrice());
        setRewardPercent(live.getRewardPercent());
        setVideoType(live.getLiveType());
    }
}
