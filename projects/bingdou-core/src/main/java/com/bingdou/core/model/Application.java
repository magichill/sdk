package com.bingdou.core.model;

/**
 */
public class Application {

    private Integer id;
    private Integer appMemberId;
    private String name;
    private String appId;
    private String packageName;
    private Integer os;
    private String payAuthKey;
    private String notifyUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppMemberId() {
        return appMemberId;
    }

    public void setAppMemberId(Integer appMemberId) {
        this.appMemberId = appMemberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getOs() {
        return os;
    }

    public void setOs(Integer os) {
        this.os = os;
    }

    public String getPayAuthKey() {
        return payAuthKey;
    }

    public void setPayAuthKey(String payAuthKey) {
        this.payAuthKey = payAuthKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
