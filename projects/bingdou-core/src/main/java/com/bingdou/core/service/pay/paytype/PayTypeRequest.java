package com.bingdou.core.service.pay.paytype;


import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.NumberUtil;

/**
 * Created by gaoshan on 16/12/27.
 */
public class PayTypeRequest {

    private String orderId;
    private String orderDesc;
    private int userId;
    private float money;
    private String clientIP;
    private int pay19Type;
    private String pay19CardNo;
    private String pay19CardPassword;
    private boolean isRecharge;
    private int payType;
    private String returnUrl;
    private BaseRequest baseRequest;
    private String aliAgreementNo;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getMoneyFen() {
        if (this.money <= 0)
            return 0;
        return NumberUtil.convertFenFromYuan(this.money);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public int getPay19Type() {
        return pay19Type;
    }

    public void setPay19Type(int pay19Type) {
        this.pay19Type = pay19Type;
    }

    public String getPay19CardNo() {
        return pay19CardNo;
    }

    public void setPay19CardNo(String pay19CardNo) {
        this.pay19CardNo = pay19CardNo;
    }

    public String getPay19CardPassword() {
        return pay19CardPassword;
    }

    public void setPay19CardPassword(String pay19CardPassword) {
        this.pay19CardPassword = pay19CardPassword;
    }

    public boolean isRecharge() {
        return isRecharge;
    }

    public void setRecharge(boolean recharge) {
        isRecharge = recharge;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public BaseRequest getBaseRequest() {
        return baseRequest;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    public String getAliAgreementNo() {
        return aliAgreementNo;
    }

    public void setAliAgreementNo(String aliAgreementNo) {
        this.aliAgreementNo = aliAgreementNo;
    }
}
