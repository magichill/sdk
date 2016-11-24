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

    @SerializedName("user_id")
    private String userId;


    @Override
    protected String getLoggerName() {
        return "CreateLiveRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CreateLiveRequest request = JsonUtil.jsonStr2Bean(requestString, CreateLiveRequest.class);
        this.liveTitle = request.getLiveTitle();
        this.userId = request.getUserId();
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
}
