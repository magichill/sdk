package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/19.
 */
public class GetHomeFollowRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

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
        return "GetHomeFollowRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetHomeFollowRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetHomeFollowRequest.class);
        this.start = request.getStart();
        this.limit = request.getLimit();
        this.account = request.getAccount();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
