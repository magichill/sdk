package com.bingdou.cdn.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16/12/13.
 */
public class ViewerCountRequest extends BaseRequest {

    /**
     * 流名称
     */
    @SerializedName("stream_name")
    private String streamName;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    @Override
    protected String getLoggerName() {
        return "ViewerCountRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ViewerCountRequest request = JsonUtil.jsonStr2Bean(requestString, ViewerCountRequest.class);
        this.streamName = request.getStreamName();
        return request;
    }
}
