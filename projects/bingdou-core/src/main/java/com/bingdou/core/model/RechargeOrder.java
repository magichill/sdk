package com.bingdou.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RechargeOrder extends Order {

    private int orderMoney;
    private int verify;
    private int verifyMoney;
    private long verifyTime;
    private String verifyMessage;
    private float quantity;
    private int isAct;
    private int backMoney;
    private int ruleId;
    private int activityType;
    private int propId;
    private int buffId;
    private int clientScene;
    private String osName;

    public int getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(int orderMoney) {
        this.orderMoney = orderMoney;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }

    public int getVerifyMoney() {
        return verifyMoney;
    }

    public void setVerifyMoney(int verifyMoney) {
        this.verifyMoney = verifyMoney;
    }

    public long getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyMessage() {
        return verifyMessage;
    }

    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getIsAct() {
        return isAct;
    }

    public void setIsAct(int isAct) {
        this.isAct = isAct;
    }

    public int getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(int backMoney) {
        this.backMoney = backMoney;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public int getBuffId() {
        return buffId;
    }

    public void setBuffId(int buffId) {
        this.buffId = buffId;
    }

    public int getClientScene() {
        return clientScene;
    }

    public void setClientScene(int clientScene) {
        this.clientScene = clientScene;
    }

    @Override
    public String getOsName() {
        return osName;
    }

    @Override
    public void setOsName(String osName) {
        this.osName = osName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
