package com.bingdou.api.request;

import com.bingdou.api.service.GetUserLivesService;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/22.
 */
public class GetUserLivesRequest extends BaseRequest {

    @SerializedName("query_account")
    private String queryAccount;

    @SerializedName("account")
    private String account;

    @SerializedName("page")
    private Integer start = 0;

    @SerializedName("count")
    private Integer limit = 10;

    @Override
    protected String getLoggerName() {
        return "GetUserLivesRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetUserLivesRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetUserLivesRequest.class);
        this.start = request.getStart();
        this.limit = request.getLimit();
        this.account = request.getAccount();
        this.queryAccount = request.getQueryAccount();
        return request;
    }

    public String getQueryAccount() {
        return queryAccount;
    }

    public void setQueryAccount(String queryAccount) {
        this.queryAccount = queryAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
