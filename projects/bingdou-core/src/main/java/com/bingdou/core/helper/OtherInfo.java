package com.bingdou.core.helper;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 其他信息请求对象
 */
public class OtherInfo {

    @SerializedName("app_id")
    private String appId;
    @SerializedName("package_name")
    private String packageName;
    @SerializedName("sdk_version")
    private String sdkVersion;
    @SerializedName("channel")
    private String channel;
    @SerializedName("app_version")
    private String appVersion;
    @SerializedName("client_time")
    private int clientTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getClientTime() {
        return clientTime;
    }

    public void setClientTime(int clientTime) {
        this.clientTime = clientTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
