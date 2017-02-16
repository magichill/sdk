package com.bingdou.core.model;

public class MoneyLog {

    /**
     * 用户ID
     */
    private int userId;
    /**
     * 用户变动金额(分)
     */
    private int money;
    /**
     * 用户变动后余额(分)
     */
    private int moneyBalance;
    /**
     * 一般是订单号
     */
    private String item;
    /**
     * 变动原因
     */
    private String reason;
    /**
     * 变动类型
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
