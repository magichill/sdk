package com.bingdou.core.model.live;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-28.
 */
public class CncRecordCommonRequest {

    /**
     * 公共参数
     */
    @SerializedName("persisentId")
    private String persistentId;

    @SerializedName("streamname")
    private String streamName;

    @SerializedName("ops")
    private String ops;

    @SerializedName("bucket")
    private String bucket;

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getOps() {
        return ops;
    }

    public void setOps(String ops) {
        this.ops = ops;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
