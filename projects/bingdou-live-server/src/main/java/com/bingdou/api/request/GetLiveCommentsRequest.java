package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/2/19.
 */
public class GetLiveCommentsRequest extends BaseRequest {

    @SerializedName("live_id")
    private long liveId;

    @SerializedName("account")
    private String account;

    @SerializedName("page")
    private int page = 0;

    @SerializedName("count")
    private int count = 10;

    public long getLiveId() {
        return liveId;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    protected String getLoggerName() {
        return "GetLiveCommentsRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GetLiveCommentsRequest request = JsonUtil.jsonStr2Bean(requestString,
                GetLiveCommentsRequest.class);
        this.page = request.getPage();
        this.count = request.getCount();
        this.liveId = request.getLiveId();
        this.account = request.getAccount();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
