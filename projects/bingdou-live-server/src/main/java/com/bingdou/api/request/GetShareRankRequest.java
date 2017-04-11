package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/4/11.
 */
public class GetShareRankRequest extends BaseRequest {

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
        return "GetShareRankRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetShareRankRequest request = JsonUtil.jsonStr2Bean(requestString, GetShareRankRequest.class);
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
