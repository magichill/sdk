package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/19.
 */
public class ModifyVideoTypeRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private Integer liveId;

    @Override
    protected String getLoggerName() {
        return "ModifyVideoTypeRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ModifyVideoTypeRequest request = JsonUtil.jsonStr2Bean(requestString, ModifyVideoTypeRequest.class);
        this.account = request.getAccount();
        this.liveId = request.getLiveId();
        return request;
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
}
