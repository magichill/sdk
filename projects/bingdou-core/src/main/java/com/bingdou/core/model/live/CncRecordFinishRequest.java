package com.bingdou.core.model.live;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 16-11-28.
 */
public class CncRecordFinishRequest {

    @SerializedName("items")
    private List<FinishItem> items;

    @SerializedName("batch_notify_id")
    private String batchNotifyId;


    public List<FinishItem> getItems() {
        return items;
    }

    public void setItems(List<FinishItem> items) {
        this.items = items;
    }

    public String getBatchNotifyId() {
        return batchNotifyId;
    }

    public void setBatchNotifyId(String batchNotifyId) {
        this.batchNotifyId = batchNotifyId;
    }

    public class FinishItem extends  CncRecordCommonRequest{

        /**
         * 回调结束参数
         */
        @SerializedName("code")
        private String code;

        @SerializedName("desc")
        private String desc;

        @SerializedName("error")
        private String error;

        @SerializedName("keys")
        private List<String> keys;

        @SerializedName("urls")
        private List<String> urls;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }
    }
}
