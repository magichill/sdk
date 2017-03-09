package com.bingdou.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/9.
 */
public class WechatToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
