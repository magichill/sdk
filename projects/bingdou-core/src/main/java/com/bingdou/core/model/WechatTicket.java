package com.bingdou.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/9.
 */
public class WechatTicket {

    @SerializedName("errcode")
    private  int errcode;

    @SerializedName("errmsg")
    private String errmsg;

    @SerializedName("ticket")
    private String ticket;

    @SerializedName("expires_in")
    private int expiresTime;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(int expiresTime) {
        this.expiresTime = expiresTime;
    }
}
