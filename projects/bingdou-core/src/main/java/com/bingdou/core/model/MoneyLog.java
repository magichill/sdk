package com.bingdou.core.model;

public class MoneyLog {

    /**
     * �û�ID
     */
    private int userId;
    /**
     * �û��䶯���(��)
     */
    private int money;
    /**
     * �û��䶯�����(��)
     */
    private int moneyBalance;
    /**
     * һ���Ƕ�����
     */
    private String item;
    /**
     * �䶯ԭ��
     */
    private String reason;
    /**
     * �䶯����
     */
    private int type;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(int moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
