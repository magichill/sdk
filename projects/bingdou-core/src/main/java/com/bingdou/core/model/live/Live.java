package com.bingdou.core.model.live;

import com.bingdou.core.model.User;
import com.bingdou.tools.IndexUtil;

import java.util.Date;

/**
 * Created by gaoshan on 16-11-8.
 */
public class Live {

    /**
     * 主键id (lid)
     */
    private int id;

    private String liveTitle;

    private String livePicture;

    private String streamName;

    private int status;

    private int orientation; //是否横屏 1:是 0:否

    private Integer mid;

    private int liveType;

    private String pushStream;

    private String pullStream;

    private String h5Url;

    private String replayUrl;

    private User user;

    private String tags;

    private String password;

    private int price;

    private int rewardPercent;

    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getLivePicture() {
        return livePicture;
    }

    public void setLivePicture(String livePicture) {
        this.livePicture = livePicture;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public int getLiveType() {
        return liveType;
    }

    public void setLiveType(int liveType) {
        this.liveType = liveType;
    }

    public String getPushStream() {
        return pushStream;
    }

    public void setPushStream(String pushStream) {
        this.pushStream = pushStream;
    }

    public String getPullStream() {
        return pullStream;
    }

    public void setPullStream(String pullStream) {
        this.pullStream = pullStream;
    }

    public String getReplayUrl() {
        return replayUrl;
    }

    public void setReplayUrl(String replayUrl) {
        this.replayUrl = replayUrl;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRewardPercent() {
        return rewardPercent;
    }

    public void setRewardPercent(int rewardPercent) {
        this.rewardPercent = rewardPercent;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * mybatis动态调用方法
     *
     * @param id 用户主键ID
     * @return 在lc_live的第几张表
     * @throws Exception
     */
    public static int getTableNumber(int id) throws Exception {
        return IndexUtil.getTableNumber(id);
    }
}
