package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 27/05/17.
 */
public class BanUserChatRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private String liveId;

    @SerializedName("ban_account")
    private String banAccount;

    @SerializedName("minute")
    private String minute;
    @Override
    protected String getLoggerName() {
        return "BanUserChatRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        BanUserChatRequest request = JsonUtil.jsonStr2Bean(requestString, BanUserChatRequest.class);
        this.account = request.getAccount();
        this.liveId = request.getLiveId();
        this.minute = request.getMinute();
        this.banAccount = request.getBanAccount();
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

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getBanAccount() {
        return banAccount;
    }

    public void setBanAccount(String banAccount) {
        this.banAccount = banAccount;
    }
}
