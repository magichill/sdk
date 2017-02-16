package com.bingdou.core.model;

public enum MoneyLogType {

    /**
     * ��ֵ������
     */
    RECHARGE(10),
    /**
     * ���ѱ�����
     */
    CONSUME_BINGDOU_COIN(11),
    /**
     * ������Ϸ��
     */
    CONSUME_VIRTUAL_MONEY(12),
    /**
     * ����
     */
    BACK(13),
    /**
     * ��ֵ����ȯ
     */
    RECHARGE_VOUCHER(14),
    /**
     * ת��
     */
    TRANSFER(15),
    /**
     * ���Ѵ���ȯ
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
