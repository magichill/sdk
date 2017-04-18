package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/16.
 */
public class RecBroadcasterRequest extends BaseRequest {

    @SerializedName("account")
    private String account;
    @SerializedName("broadcaster_type")
    private Integer broadcasterType;  //0：推荐主播 1：人气主播 2:活跃排名 不传返回所有主播
    @SerializedName("page")
    private Integer page = 0;
    @SerializedName("count")
    private Integer count = 10;

    @Override
    protected String getLoggerName() {
        return "RecBroadcasterRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        RecBroadcasterRequest request = JsonUtil.jsonStr2Bean(requestString,
                RecBroadcasterRequest.class);
        this.account = request.getAccount();
        this.broadcasterType = request.getBroadcasterType();
        this.page = request.getPage();
        this.count = request.getCount();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBroadcasterType() {
        return broadcasterType;
    }

    public void setBroadcasterType(Integer broadcasterType) {
        this.broadcasterType = broadcasterType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
