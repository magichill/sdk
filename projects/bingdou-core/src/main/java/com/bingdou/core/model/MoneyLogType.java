package com.bingdou.core.model;

public enum MoneyLogType {

    /**
     * 充值冰豆币
     */
    RECHARGE(10),
    /**
     * 消费冰豆币
     */
    CONSUME_BINGDOU_COIN(11),
    /**
     * 消费游戏币
     */
    CONSUME_VIRTUAL_MONEY(12),
    /**
     * 返利
     */
    BACK(13),
    /**
     * 充值代金券
     */
    RECHARGE_VOUCHER(14),
    /**
     * 转账
     */
    TRANSFER(15),
    /**
     * 消费代金券
     */
    CONSUME_VOUCHER(16);

    private int index;

    MoneyLogType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
