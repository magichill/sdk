package com.bingdou.core.model;

/**
 * 验证码
 */
public class ValidateCode {

    private String mobileOrEmail;
    private String vcode;
    private long vcodeTime;
    private int type;
    private int purpose;

    public String getMobileOrEmail() {
        return mobileOrEmail;
    }

    public void setMobileOrEmail(String mobileOrEmail) {
        this.mobileOrEmail = mobileOrEmail;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public long getVcodeTime() {
        return vcodeTime;
    }

    public void setVcodeTime(long vcodeTime) {
        this.vcodeTime = vcodeTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }
}
