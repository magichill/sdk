package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-4.
 */
public class CreateLiveRequest extends BaseRequest {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("live_title")
    private String liveTitle;


    @Override
    protected String getLoggerName() {
        return "CreateLiveRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CreateLiveRequest request = JsonUtil.jsonStr2Bean(requestString,
                CreateLiveRequest.class);
        this.userId = request.getUserId();
        this.liveTitle = request.getLiveTitle();
        return request;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }
}
