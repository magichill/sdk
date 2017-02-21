package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 16-11-4.
 */
public class FindLiveRequest extends BaseRequest{

    @SerializedName("user_id")
    private String userId; //传则返回该主播视频，不传则返回所有主播视频

    @SerializedName("status")
    private int status = 1;

    @SerializedName("live_id")
    private Integer liveId;
    /**
     * 起始页
     */
    @SerializedName("page")
    private int start = 0;
    /**
     * 条数
     */
    @SerializedName("count")
    private int limit = 10;

    @Override
    protected String getLoggerName() {
        return "FindLiveRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        FindLiveRequest request = JsonUtil.jsonStr2Bean(requestString,
                FindLiveRequest.class);
        this.start = request.getStart();
        this.limit = request.getLimit();
        this.status = request.getStatus();
        this.liveId = request.getLiveId();
        this.userId = request.getUserId();
        return request;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
