package com.bingdou.core.helper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-3.
 */
public class LsmSmsResponse {

    @SerializedName("batch_id")
    private String batchId;//发送批次号
    @SerializedName("msg")
    private String msg;//
    @SerializedName("error")
    private int error; //0成功
    @SerializedName("hit")
    private String hit;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }
}
