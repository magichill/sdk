package com.bingdou.core.model.live;

import com.bingdou.tools.IndexUtil;

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

    private String mid;

    private int liveType;

    private String pushStream;

    private String pullStream;

    private String replayUrl;

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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
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
