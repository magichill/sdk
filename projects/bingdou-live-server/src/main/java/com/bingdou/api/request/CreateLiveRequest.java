package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

/**
 * Created by gaoshan on 16-11-4.
 */
public class CreateLiveRequest extends BaseRequest {

    @SerializedName("title")
    private String liveTitle;

    @SerializedName("cover_url")
    private String livePic;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("stream_id")
    private String streamId;

    @SerializedName("stream_type")
    private int streamType = 1;
    @SerializedName("certification_status")
    private int certificationStatus;
    @SerializedName("status")
    private int status;
    @SerializedName("orientation")
    private int orientation;
    @SerializedName("start_at")
    private long startAt;
    @SerializedName("tags")
    private String tags;
    @SerializedName("video_type")
    private int videoType;  //视频的类型 1（免费）；2（加密）；3（普通付费）；4（分销付费）
    @SerializedName("password")
    private String password;
    @SerializedName("price")
    private float price = 0;

    @SerializedName("reward_percent")
    private int percent = 0;

    @Override
    protected String getLoggerName() {
        return "CreateLiveRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CreateLiveRequest request = JsonUtil.jsonStr2Bean(requestString, CreateLiveRequest.class);
        this.liveTitle = request.getLiveTitle();
        this.userId = request.getUserId();
        this.livePic = request.getLivePic();
        this.streamId = request.getStreamId();
        this.streamType = request.getStreamType();
        this.certificationStatus = request.getCertificationStatus();
        this.status = request.getStatus();
        this.orientation = request.getOrientation();
        this.startAt = request.getStartAt();
        this.tags = request.getTags();
        this.videoType = request.getVideoType();
        this.password = request.getPassword();
        this.price = request.getPrice();
        this.percent = request.getPercent();
        return request;
    }

    public boolean checkValid(){
        switch (this.getVideoType()){
            case 2:
                if(StringUtils.isEmpty(this.getPassword()))
                    return false;
                break;
            case 3:
                if(this.getPrice() == 0)
                    return false;
                break;
            case 4:
                if(this.getPrice() == 0 || this.getPercent() == 0)
                    return false;
                break;
            default:
                break;
        }
        return true;
    }
    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLivePic() {
        return livePic;
    }

    public void setLivePic(String livePic) {
        this.livePic = livePic;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public int getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(int certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
