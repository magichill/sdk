package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/27.
 */
public class SendGiftRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("gift_id")
    private Integer giftId;

    @SerializedName("send_num")
    private int sendNum = 1;

    @Override
    protected String getLoggerName() {
        return "SendGiftRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        SendGiftRequest request = JsonUtil.jsonStr2Bean(requestString, SendGiftRequest.class);
        this.account = request.getAccount();
        this.liveId = request.getLiveId();
        this.giftId = request.getGiftId();
        this.sendNum = request.getSendNum();
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

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }
}
