package com.bingdou.core.model;

/**
 */
public class SafeInfo {

    private String requestSource;
    private String ip;
    private String keyGroup;
    private String methodName;
    private String validTokenOnlyUserIdRs;
    private int isClient;

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getKeyGroup() {
        return keyGroup;
    }

    public void setKeyGroup(String keyGroup) {
        this.keyGroup = keyGroup;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getValidTokenOnlyUserIdRs() {
        return validTokenOnlyUserIdRs;
    }

    public void setValidTokenOnlyUserIdRs(String validTokenOnlyUserIdRs) {
        this.validTokenOnlyUserIdRs = validTokenOnlyUserIdRs;
    }

    public int getIsClient() {
        return isClient;
    }

    public void setIsClient(int isClient) {
        this.isClient = isClient;
    }
}
