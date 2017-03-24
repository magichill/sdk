package com.bingdou.core.constants;

import com.bingdou.tools.LogContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 支付方式配置常量
 * Created by gaoshan on 16/12/27.
 */
public class PayTypeData {

    public static final String WEB_PAY_TYPE_REQUEST_URL_PREFIX;
    public static final String CLIENT_PAY_SUCCESS_RETURN_URL;
    /**
     * PP钱包
     */
    public static final String PAY_TYPE_PP_ORDER_CODE = "33A11H";
    public static final String PAY_TYPE_PP_PAY_CODE = "33A12H";
    public static final String PAY_TYPE_PP_PRODUCT_ID = "100001";
    public static final String PAY_TYPE_PP_ORDER_TYPE = "collect";
    public static final String PAY_TYPE_PP_MER_ID = "2015061217";
    public static final String PAY_TYPE_PP_KEY = "xUo17/zddtORIdyfhfJ/doqlVsODXNI1";
    public static final String PAY_TYPE_PP_URL;
    public static final int PAY_TYPE_PP_TIMEOUT;
    public static final String PAY_TYPE_PP_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_PP_CONSUME_NOTIFY_URL;

    /**
     * 官方微信
     */
    public static final String PAY_TYPE_WX_APP_ID = "wx5b90f55c6fa86b9d";
    public static final String PAY_TYPE_WX_APP_ID_H5 = "wx9ccf4d8d561bbd9c";
    public static final String PAY_TYPE_WX_MCH_ID = "1359273902";
    public static final String PAY_TYPE_WX_MCH_ID_H5 = "1370845702";

    public static final String PAY_TYPE_WX_FEE_TYPE = "CNY";
    public static final String PAY_TYPE_WX_TRADE_TYPE = "APP";
    public static final String PAY_TYPE_WX_TRADE_TYPE_H5 = "JSAPI";
    public static final String PAY_TYPE_WX_SECRET = "3301402a9f9ae2fa28f1e2951e3b69a6";
    public static final String PAY_TYPE_WX_KEY = "QPalZMwoSKxnEUdjCNryFHvb14982364";
    public static final String PAY_TYPE_WX_SECRET_H5 = "ccddcebe153419b6d5001ddf0ed090db";
    public static final String PAY_TYPE_WX_KEY_H5 = "12345678901234567890QPWOEIRUTYqp";
    public static final String PAY_TYPE_WX_PACKAGE = "Sign=WXPay";
    public static final String PAY_TYPE_WX_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_WX_CONSUME_NOTIFY_URL;
    public static final String PAY_TYPE_WX_PUBLIC_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_WX_PUBLIC_CONSUME_NOTIFY_URL;
    public static final String PAY_TYPE_WX_URL;
    public static final int PAY_TYPE_WX_TIMEOUT;

    /**
     * 融信优贝银联
     */
    public static final String PAY_TYPE_UPMP_MER_ID = "898111148160288";
    public static final String PAY_TYPE_UPMP_KEY = "RBPM3fEtZFOey7VgaZkNb03GoUir2toO";
    public static final String PAY_TYPE_UPMP_TRADE_URL;
    public static final String PAY_TYPE_UPMP_RECHARGE_BACK_END_URL;
    public static final String PAY_TYPE_UPMP_CONSUME_BACK_END_URL;
    public static final int PAY_TYPE_UPMP_TIMEOUT;

    /**
     * 手机卡
     */
    public static final String PAY_TYPE_CARD_MER_ID = "275127";
    public static final String PAY_TYPE_CARD_YIDONG_PM_ID = "CMJFK";
    public static final String PAY_TYPE_CARD_DIANXIN_PM_ID = "DXJFK";
    public static final String PAY_TYPE_CARD_LIANTONG_PM_ID = "LTJFK";
    public static final String PAY_TYPE_CARD_YIDONG_PC_ID = "CMJFK00010001";
    public static final String PAY_TYPE_CARD_DIANXIN_PC_ID = "DXJFK00010001";
    public static final String PAY_TYPE_CARD_LIANTONG_PC_ID = "LTJFK00020000";
    public static final String PAY_TYPE_CARD_DES_KEY = "dbj9l4mm";
    public static final String PAY_TYPE_CARD_KEY = "dbj9l4mmn7rgogi3wlzqwpjlx0wd11635n61q1huvtalzky2uclheuq3877ja0ri8u1cvwdomn7mvq036ot1tk8a0tqwi3j3lpvb2wik8tof3znweigrqsk4ihjbzuu8";
    public static final String PAY_TYPE_CARD_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_CARD_URL;
    public static final int PAY_TYPE_CARD_TIMEOUT;

    /**
     * 汇元微信
     */
    public static final String PAY_TYPE_HEEPAY_WX_PAY_VERSION = "1";
    public static final String PAY_TYPE_HEEPAY_WX_AGENT_ID = "2026004";
    public static final String PAY_TYPE_HEEPAY_WX_SIGN_KEY = "9D4495AAAEA04117AAF33836";
    public static final int PAY_TYPE_HEEPAY_WX_PAY_TYPE = 30;
    public static final String PAY_TYPE_HEEPAY_WX_REQUEST_URL;
    public static final String PAY_TYPE_HEEPAY_WX_SCAN_CODE_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_HEEPAY_WX_SCAN_CODE_CONSUME_NOTIFY_URL;
    public static final String PAY_TYPE_HEEPAY_WX_WAP_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_HEEPAY_WX_WAP_CONSUME_NOTIFY_URL;
    public static final int PAY_TIMEOUT_HEEPAY_WX_TIMEOUT;
    /**
     * 汇元微信(SDK)
     */
    public static final String PAY_TYPE_HEEPAY_SDK_WX_REQUEST_URL;
    public static final String PAY_TYPE_HEEPAY_WX_SDK_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_HEEPAY_WX_SDK_CONSUME_NOTIFY_URL;

    /**
     * 支付宝
     */
    private static final String PAY_TYPE_ALI_URL_ENCODING_PARAMS = "_input_charset=utf-8";
    public static final String PAY_TYPE_ALI_GATE_WAY = "https://mapi.alipay.com/gateway.do?";
    public static final String PAY_TYPE_ALI_URL_WITH_ENCODING = PAY_TYPE_ALI_GATE_WAY + PAY_TYPE_ALI_URL_ENCODING_PARAMS;
    public static final String PAY_TYPE_ALI_SIGN_KEY = "su7lnd88m4t24zrnmf9ts6q38nkt5w7u";
    public static final String PAY_TYPE_ALI_NO_PWD_AUTH_QUERY_METHOD = "alipay.dut.customer.agreement.query";
    public static final String PAY_TYPE_ALI_NO_PWD_AUTH_SIGN_METHOD = "alipay.dut.customer.agreement.page.sign";
    public static final String PAY_TYPE_ALI_NO_PWD_PAY_METHOD = "alipay.acquire.createandpay";
    public static final String PAY_TYPE_ALI_MOBILE_PAY_METHOD = "mobile.securitypay.pay";
    public static final String PAY_TYPE_ALI_NO_PWD_AUTH_PRODUCT_CODE = "GENERAL_WITHHOLDING_P";
    public static final String PAY_TYPE_ALI_NO_PWD_PAY_PRODUCT_CODE = "GENERAL_WITHHOLDING";
    public static final String PAY_TYPE_ALI_NO_PWD_AUTH_SCENE = "INDUSTRY|GAME_CHARGE";
    public static final String PAY_TYPE_ALI_SCAN_METHOD = "create_direct_pay_by_user";
    public static final String PAY_TYPE_VERIFY_URL = PAY_TYPE_ALI_GATE_WAY + "service=notify_verify";
    public static final int PAY_TYPE_ALI_TIMEOUT;
    public static final String PAY_TYPE_ALI_PAY_SCAN_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_ALI_PAY_SCAN_CONSUME_NOTIFY_URL;
    public static final String PAY_TYPE_ALI_PAY_SCAN_RETURN_URL;

    /**
     * CHINA PAY银联
     */
    public static final String PAY_TYPE_CHINA_PAY_UNION_VERSION = "20140728";
    public static final String PAY_TYPE_CHINA_PAY_UNION_MER_ID = "799311601050002";
    public static final String PAY_TYPE_CHINA_PAY_UNION_BUSI_TYPE = "0001";
    public static final String PAY_TYPE_CHINA_PAY_UNION_CURRY_NO = "CNY";
    public static final String PAY_TYPE_CHINA_PAY_UNION_RETURN_URL;
    public static final String PAY_TYPE_CHINA_PAY_UNION_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_CHINA_PAY_UNION_CONSUME_NOTIFY_URL;
    public static final String PAY_TYPE_CHINA_PAY_UNION_URL;

    /**
     * 支付宝快捷
     */
    public static final String PAY_TYPE_ALI_MOBILE_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_ALI_MOBILE_CONSUME_NOTIFY_URL;

    public static final String PAY_TYPE_ALI_NO_PWD_AUTH_NOTIFY_URL;
    public static final String PAY_TYPE_ALI_NO_PWD_PAY_RECHARGE_NOTIFY_URL;
    public static final String PAY_TYPE_ALI_NO_PWD_PAY_CONSUME_NOTIFY_URL;

    public static final String CONSUME_BINGDOU_URL;
    private PayTypeData() {
    }

    static {
        Properties prop = new Properties();
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("pay-type.properties");
        } catch (IOException e) {
            LogContext.instance().error(e, "初始化支付方式数据错误");
        }
        WEB_PAY_TYPE_REQUEST_URL_PREFIX = prop.getProperty("web.pay.type.request.url.prefix");
        CLIENT_PAY_SUCCESS_RETURN_URL = prop.getProperty("client.pay.success.return.url");

        PAY_TYPE_PP_URL = prop.getProperty("pp.url");
        PAY_TYPE_PP_TIMEOUT = Integer.parseInt(prop.getProperty("pp.timeout"));
        PAY_TYPE_PP_RECHARGE_NOTIFY_URL = prop.getProperty("pp.recharge.notify.url");
        PAY_TYPE_PP_CONSUME_NOTIFY_URL = prop.getProperty("pp.consume.notify.url");

        PAY_TYPE_WX_RECHARGE_NOTIFY_URL = prop.getProperty("wx.recharge.notify.url");
        PAY_TYPE_WX_CONSUME_NOTIFY_URL = prop.getProperty("wx.consume.notify.url");
        PAY_TYPE_WX_PUBLIC_RECHARGE_NOTIFY_URL = prop.getProperty("wx.public.recharge.notify.url");
        PAY_TYPE_WX_PUBLIC_CONSUME_NOTIFY_URL = prop.getProperty("wx.public.consume.notify.url");
        PAY_TYPE_WX_URL = prop.getProperty("wx.url");
        PAY_TYPE_WX_TIMEOUT = Integer.parseInt(prop.getProperty("wx.timeout"));

        PAY_TYPE_UPMP_TRADE_URL = prop.getProperty("upmp.trade.url");
        PAY_TYPE_UPMP_RECHARGE_BACK_END_URL = prop.getProperty("upmp.recharge.back.end.url");
        PAY_TYPE_UPMP_CONSUME_BACK_END_URL = prop.getProperty("upmp.consume.back.end.url");
        PAY_TYPE_UPMP_TIMEOUT = Integer.parseInt(prop.getProperty("upmp.timeout"));

        PAY_TYPE_CARD_RECHARGE_NOTIFY_URL = prop.getProperty("card.recharge.notify.url");
        PAY_TYPE_CARD_URL = prop.getProperty("card.url");
        PAY_TYPE_CARD_TIMEOUT = Integer.parseInt(prop.getProperty("card.timeout"));

        PAY_TYPE_HEEPAY_WX_SCAN_CODE_RECHARGE_NOTIFY_URL = prop.getProperty("heepay.wx.recharge.notify.url");
        PAY_TYPE_HEEPAY_WX_SCAN_CODE_CONSUME_NOTIFY_URL = prop.getProperty("heepay.wx.consume.notify.url");
        PAY_TYPE_HEEPAY_WX_WAP_RECHARGE_NOTIFY_URL = prop.getProperty("heepay.wx.recharge.notify.wap.url");
        PAY_TYPE_HEEPAY_WX_WAP_CONSUME_NOTIFY_URL = prop.getProperty("heepay.wx.consume.notify.wap.url");
        PAY_TYPE_HEEPAY_WX_REQUEST_URL = prop.getProperty("heepay.wx.request.url");
        PAY_TIMEOUT_HEEPAY_WX_TIMEOUT = Integer.parseInt(prop.getProperty("heepay.wx.timeout"));

        PAY_TYPE_HEEPAY_SDK_WX_REQUEST_URL = prop.getProperty("heepay.sdk.wx.request.url");
        PAY_TYPE_HEEPAY_WX_SDK_RECHARGE_NOTIFY_URL = prop.getProperty("heepay.sdk.wx.recharge.notify.url");
        PAY_TYPE_HEEPAY_WX_SDK_CONSUME_NOTIFY_URL = prop.getProperty("heepay.sdk.wx.consume.notify.url");

        PAY_TYPE_ALI_TIMEOUT = Integer.parseInt(prop.getProperty("ali.pay.timeout"));
        PAY_TYPE_ALI_PAY_SCAN_RECHARGE_NOTIFY_URL = prop.getProperty("ali.pay.scan.recharge.notify.url");
        PAY_TYPE_ALI_PAY_SCAN_CONSUME_NOTIFY_URL = prop.getProperty("ali.pay.scan.consume.notify.url");
        PAY_TYPE_ALI_PAY_SCAN_RETURN_URL = prop.getProperty("ali.pay.scan.return.url");

        PAY_TYPE_CHINA_PAY_UNION_URL = prop.getProperty("china.pay.union.url");
        PAY_TYPE_CHINA_PAY_UNION_RETURN_URL = prop.getProperty("china.pay.union.return.url");
        PAY_TYPE_CHINA_PAY_UNION_RECHARGE_NOTIFY_URL = prop.getProperty("china.pay.union.recharge.notify.url");
        PAY_TYPE_CHINA_PAY_UNION_CONSUME_NOTIFY_URL = prop.getProperty("china.pay.union.consume.notify.url");

        PAY_TYPE_ALI_MOBILE_RECHARGE_NOTIFY_URL = prop.getProperty("ali.mobile.pay.recharge.notify.url");
        PAY_TYPE_ALI_MOBILE_CONSUME_NOTIFY_URL = prop.getProperty("ali.mobile.pay.consume.notify.url");

        PAY_TYPE_ALI_NO_PWD_AUTH_NOTIFY_URL = prop.getProperty("ali.no.pwd.auth.notify.url");
        PAY_TYPE_ALI_NO_PWD_PAY_RECHARGE_NOTIFY_URL = prop.getProperty("ali.no.pwd.pay.recharge.notify.url");
        PAY_TYPE_ALI_NO_PWD_PAY_CONSUME_NOTIFY_URL = prop.getProperty("ali.no.pwd.pay.consume.notify.url");
        CONSUME_BINGDOU_URL = prop.getProperty("bingdou.consume.coin.url");
    }

}
