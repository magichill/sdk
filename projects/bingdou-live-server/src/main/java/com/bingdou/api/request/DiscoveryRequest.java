package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/15.
 */
public class DiscoveryRequest extends BaseRequest {

    @SerializedName("user_id")
    private String account;

    @SerializedName("status")
    private Integer status;

    @SerializedName("page")
    private int start = 0;

    @SerializedName("count")
    private int limit = 10;

    @Override
    protected String getLoggerName() {
        return "DiscoveryRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        DiscoveryRequest request = JsonUtil.jsonStr2Bean(requestString, DiscoveryRequest.class);
        this.setAccount(request.getAccount());
        this.setStatus(request.getStatus());
        this.setStart(request.getStart());
        this.setLimit(request.getLimit());
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
