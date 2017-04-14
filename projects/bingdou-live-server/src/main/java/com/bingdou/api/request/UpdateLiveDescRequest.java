package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/4/13.
 */
public class UpdateLiveDescRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("description")
    private String description;

    @Override
    protected String getLoggerName() {
        return "UpdateLiveDescRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        UpdateLiveDescRequest request = JsonUtil.jsonStr2Bean(requestString,
                UpdateLiveDescRequest.class);
        this.description = request.getDescription();
        this.liveId = request.getLiveId();
        this.account = request.getAccount();
        return request;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
