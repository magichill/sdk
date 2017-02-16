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
    private String userId;

    @SerializedName("target_id")
    private String followId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
