package com.bingdou.core.model;

/**
 * ֧����ʽ
 * Created by gaoshan on 16/11/22.
 */
public enum PayType {

    BINGDOU_COIN(0, "������"), ALI_MOBILE(1, "֧�������"), UPMP(3, "�����ֻ�֧��"), PAY_19(4, "�ֻ���ֵ��֧��"),
    WEIXIN(5, "΢��֧��"), PP(7, "���п����֧��"), VOUCHER(8, "��ֵ����ȯ"), HEEPAY_WEIXIN_SDK(10, "��Ԫ΢��"),
    ALI_NO_PWD(11, "֧��������֧��"), HEEPAY_WEIXIN_SCAN_CODE(12, "΢��֧��(ɨ��)"),
    HEEPAY_WEIXIN_WAP(13, "΢��֧��(WAP)"), APPLE_INNER(14, "ƻ��Ӧ����֧��"),
    ALI_SCAN(15, "֧����֧��(ɨ��)"), CHINA_PAY_UNION(16, "����֧��");

    private int index;
    private String name;

    PayType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PayType getByIndex(int index) {
        for (PayType payType : PayType.values()) {
            if (index == payType.getIndex())
                return payType;
        }
        return null;
    }
}
