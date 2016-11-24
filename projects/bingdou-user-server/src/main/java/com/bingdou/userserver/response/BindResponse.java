package com.bingdou.userserver.response;

import com.google.gson.annotations.SerializedName;

public class BindResponse {

    @SerializedName("safe_level")
    private int safeLevel;

    public int getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(int safeLevel) {
        this.safeLevel = safeLevel;
    }
}
