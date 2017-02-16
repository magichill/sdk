package com.bingdou.core.constants;

/**
 * 支付常量
 * Created by gaoshan on 16/12/24.
 */
public class PayConstants {

    private PayConstants() {
    }

    public static final String[] AIBEI_APPLICATION_APPIDS = {"10d2c77d26e97fdc30d84c0964a2ab80"
            , "e3e03829e76339dcd644304378cb1da7", "35bde924210f77c1b3c56393374c496a"
            , "c2baeaa6c18090c4f705d3467a143316", "07cb138d191fba887c6f25bcd14a8624"};

    /**
     * 支付宝免密授权信息标题
     */
    public static final String ALI_PAY_NO_PWD_AUTH_TITLE = "支付宝免密支付授权";
    /**
     * 支付宝免密授权信息内容
     */
    public static final String ALI_PAY_NO_PWD_AUTH_CONTENT = "支付宝免密授权成功";
    /**
     * 苹果应用内支付凭证验证正确返回
     */
    public static final int APPLE_PAY_VERIFY_SUCCESS = 0;
    /**
     * 苹果应用内支付凭证验证错误返回(多个凭证)
     */
    public static final int APPLE_PAY_VERIFY_MULTI_ERROR = 1;
    /**
     * 苹果应用内支付凭证验证错误返回(凭证已经处理过)
     */
    public static final int APPLE_PAY_VERIFY_DEAL_ERROR = 2;
    /**
     * 苹果应用内支付凭证验证错误返回(苹果状态异常)
     */
    public static final int APPLE_PAY_VERIFY_APPLE_STATUS_ERROR = 3;
    /**
     * 苹果应用内支付凭证验证错误返回(订单金额错误)
     */
    public static final int APPLE_PAY_VERIFY_APPLE_MONEY_ERROR = 4;
    /**
     * 苹果应用内支付凭证验证正确
     */
    public static final int APPLE_PAY_VERIFY_SUCCESS_RETURN_CODE = 0;
    /**
     * 苹果应用内支付凭证验证接口超时时间(毫秒)
     */
    public static final int APPLE_PAY_VERIFY_TIME_OUT = 1200;
    /**
     * 充返卡道具类型
     */
    public static final int CHARGE_BACK_CARD_PROP_TYPE = 1;
    /**
     * 网页支付超时(秒)
     */
    public static final int WEB_PAY_TYPE_TIME_OUT_SECONDS = 1800;
    /**
     * 支付宝交易完成状态码
     */
    public static final String ALI_CALL_RESULT_FINISHED_CODE = "TRADE_FINISHED";
    /**
     * 支付宝交易成功状态码
     */
    public static final String ALI_CALL_RESULT_SUCCESS_CODE = "TRADE_SUCCESS";
    /**
     * 支付宝等待交易状态码
     */
    public static final String ALI_CALL_RESULT_WAIT_CODE = "WAIT_BUYER_PAY";
    /**
     * CHINAPAY银联交易成功状态码
     */
    public static final String CHINA_PAY_UNION_CALL_RESULT_SUCCESS_CODE = "0000";
    /**
     * 特殊渠道充返标题
     */
    public static final String SPECIAL_ACTIVITY_TITLE = "冰豆币充值返利活动";
    /**
     * 特殊渠道充返内容
     */
    public static final String SPECIAL_ACTIVITY_CONTENT = "1元起，充值冰豆币均有100%返利";
    /**
     * 特殊渠道APP ID
     */
    public static final String SPECIAL_ACTIVITY_APP_IDS = "74925a529cfde58e441c386dbf88ec94,289e7752c4c6c0d8e14b2f5823c7056a";
    /**
     * 特殊活动渠道号
     */
    public static final String SPECIAL_ACTIVITY_CHANNEL = "000001690";
}
