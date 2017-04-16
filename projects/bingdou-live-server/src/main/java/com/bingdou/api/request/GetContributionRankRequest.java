package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/14.
 */
public class GetContributionRankRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("query_account")
    private String queryAccount;

    @SerializedName("page")
    private int page = 0;

    @SerializedName("count")
    private int count = 10;

    @Override
    protected String getLoggerName() {
        return "GetContributionRankRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetContributionRankRequest request = JsonUtil.jsonStr2Bean(requestString, GetContributionRankRequest.class);
        this.account = request.getAccount();
        this.queryAccount = request.getQueryAccount();
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

    public String getQueryAccount() {
        return queryAccount;
    }

    public void setQueryAccount(String queryAccount) {
        this.queryAccount = queryAccount;
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
}
