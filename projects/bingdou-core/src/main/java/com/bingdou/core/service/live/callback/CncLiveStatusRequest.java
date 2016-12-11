package com.bingdou.core.service.live.callback;

import com.bingdou.tools.JsonUtil;

/**
 * Created by gaoshan on 16/12/9.
 *
 * 网宿推流状态回调参数
 */
public class CncLiveStatusRequest {

    private String ip; //推流端IP
    private String streamName; //流名
    private String node; // 节点IP
    private String app; //推流域名
    private String appName; //发布点

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString(){
        return JsonUtil.bean2JsonStr(this);
    }
}
