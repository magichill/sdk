package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

public class GetAliPayNoPwdAuthSignResponse {

    @SerializedName("auth_url")
    private String authUrl;

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
