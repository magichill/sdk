package com.bingdou.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/2/27.
 */
public class EntryResponse {


    @SerializedName("result")
    private String result;

    @SerializedName("enter_count")
    private int enterCount;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(int enterCount) {
        this.enterCount = enterCount;
    }
}
