package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-4.
 */
public class FindLiveRequest extends BaseRequest{

    /**
     * 起始页
     */
    @SerializedName("start")
    private int start = 1;
    /**
     * 条数
     */
    @SerializedName("limit")
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
}
