package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/2/18.
 * 获取关注用户请求
 */
public class GetFollowingRequest extends BaseRequest {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("following_status")
    private Integer followStatus;

    /**
     * 起始页
     */
    @SerializedName("page")
    private int page = 0;
    /**
     * 条数
     */
    @SerializedName("count")
    private int count = 10;

    @Override
    protected String getLoggerName() {
        return "GetFollowingRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetFollowingRequest getFollowingRequest = JsonUtil.jsonStr2Bean(requestString,
                GetFollowingRequest.class);
        this.page = getFollowingRequest.getPage();
        this.count = getFollowingRequest.getCount();
        this.userId = getFollowingRequest.getUserId();
        this.followStatus = getFollowingRequest.getFollowStatus();
        return getFollowingRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(Integer followStatus) {
        this.followStatus = followStatus;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}