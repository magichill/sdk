package com.bingdou.cdn.request;

import com.bingdou.cdn.constant.UpYunConstant;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 16-11-13.
 */
public class UpYunRequest {


    @SerializedName("bucket_name")
    private String bucketName = UpYunConstant.BUCKET_NAME;

    @SerializedName("stream")
    private List<String> stream;

    @SerializedName("app")
    private String app = "bingdou_live";

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public List<String> getStream() {
        return stream;
    }

    public void setStream(List<String> stream) {
        this.stream = stream;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String toString(){
        return JsonUtil.bean2JsonStr(this);
    }

}
