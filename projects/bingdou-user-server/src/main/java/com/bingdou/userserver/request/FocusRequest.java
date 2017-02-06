package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/1/25.
 */
public class FocusRequest extends BaseRequest {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("follow_id")
    private int followId;

    @Override
    protected String getLoggerName() {
        return "FocusRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        FocusRequest request = JsonUtil.jsonStr2Bean(requestString, FocusRequest.class);
        this.userId = request.getUserId();
        this.followId = request.getFollowId();
        return request;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
