package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/2/18.
 * 获取用户粉丝请求
 */
public class GetFollowersRequest extends BaseRequest {

    @SerializedName("user_id")
    private String userId;

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
        return "GetFollowersRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetFollowersRequest getFollowersRequest = JsonUtil.jsonStr2Bean(requestString,
                GetFollowersRequest.class);
        this.page = getFollowersRequest.getPage();
        this.count = getFollowersRequest.getCount();
        this.userId = getFollowersRequest.getUserId();
        return getFollowersRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
