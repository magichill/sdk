package com.bingdou.core.model;

/**
 * 支付方式
 * Created by gaoshan on 16/11/22.
 */
public enum PayType {

    BINGDOU_COIN(0, "冰豆币"), ALI_MOBILE(1, "支付宝快捷"), UPMP(3, "银联手机支付"), PAY_19(4, "手机充值卡支付"),
    WEIXIN(5, "微信支付"),PUBLIC_WEIXIN(17,"微信公众号支付"), PP(7, "银行卡快捷支付"), VOUCHER(8, "充值代金券"), HEEPAY_WEIXIN_SDK(10, "汇元微信"),
    ALI_NO_PWD(11, "支付宝无密支付"), HEEPAY_WEIXIN_SCAN_CODE(12, "微信支付(扫码)"),
    HEEPAY_WEIXIN_WAP(13, "微信支付(WAP)"), APPLE_INNER(14, "苹果应用内支付"),
    ALI_SCAN(15, "支付宝支付(扫码)"), CHINA_PAY_UNION(16, "银联支付");

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
