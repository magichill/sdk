package com.bingdou.core.model;

/**
 * 短信发送历史对象
 */
public class SmsSendRecord {

    private String mobile;
    private String channel;
    private int sendType;
    private int sendStatus;
    private String channelMessageId;
    private String content;
    private String device;
    private String ip;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getChannelMessageId() {
        return channelMessageId;
    }

    public void setChannelMessageId(String channelMessageId) {
        this.channelMessageId = channelMessageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
