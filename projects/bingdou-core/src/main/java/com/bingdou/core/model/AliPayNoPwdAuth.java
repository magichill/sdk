package com.bingdou.core.model;

/**
 * ÷ß∏∂±¶√‚√‹ ⁄»®
 * Created by gaoshan on 16/12/28.
 */
public class AliPayNoPwdAuth {

    private int userId;
    private String externalSignNo;
    private String alipayUserId;
    private int status;
    private String agreementNo;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExternalSignNo() {
        return externalSignNo;
    }

    public void setExternalSignNo(String externalSignNo) {
        this.externalSignNo = externalSignNo;
    }

    public String getAlipayUserId() {
        return alipayUserId;
    }

    public void setAlipayUserId(String alipayUserId) {
        this.alipayUserId = alipayUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }
}
