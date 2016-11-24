package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 游客登录请求
 */
public class GuestLoginRequest extends BaseRequest {

    @SerializedName("device_no_4_server")
    private String deviceNo4Server;

    @Override
    protected String getLoggerName() {
        return "GuestLoginRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GuestLoginRequest request = JsonUtil.jsonStr2Bean(requestString, GuestLoginRequest.class);
        this.deviceNo4Server = request.getDeviceNo4Server();
        return request;
    }

    public String getDeviceNo4Server() {
        return deviceNo4Server;
    }

    public void setDeviceNo4Server(String deviceNo4Server) {
        this.deviceNo4Server = deviceNo4Server;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
