package com.bingdou.cdn.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-14.
 */
public class CreateLiveRequest extends BaseRequest{

    @SerializedName("live_title")
    private String liveTitle;

    @SerializedName("live_pic")
    private String livePic;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("stream_id")
    private String streamId;


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
        return request;
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
}
