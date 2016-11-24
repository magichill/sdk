package com.bingdou.tools.constants;

/**
 * 日志名称常量
 */
public class CommonLoggerNameConstants {

    private CommonLoggerNameConstants() {
    }

    /**
     * 请求时间日志
     */
    public static final String TIME_CALCULATOR_LOGGER = "timerCalculator_Logger";
    /**
     * 请求入口日志
     */
    public static final String REQUEST_PARAM_LOG_NAME = "requestParam";
    /**
     * 请其他系统的日志
     */
    public static final String HTTP_CLIENT_LOGGER = "httpClient_Logger";
    /**
     * 服务器hadoop入库日志
     */
    public static final String HADOOP_LOGGER = "serverToHadoop_Logger";
    /**
     * 客户端ANDROID hadoop入库日志
     */
    public static final String CLIENT_ANDROID_HADOOP_LOGGER = "androidClientToHadoop_Logger";
    /**
     * 客户端IOS hadoop入库日志
     */
    public static final String CLIENT_IOS_HADOOP_LOGGER = "iosClientToHadoop_Logger";
    /**
     * 发邮件日志
     */
    public static final String MAIL_LOGGER = "mail_Logger";
    /**
     * 支付方式回调日志
     */
    public static final String PAY_TYPE_CALL_BACK_LOGGER = "payTypeCallBack_Logger";
    /**
     * 支付方式网页版日志
     */
    public static final String PAY_TYPE_WEB_LOGGER = "payTypeWeb_Logger";
    /**
     * 监控日志
     */
    public static final String MONITOR_LOGGER = "monitor_Logger";
    /**
     * 回调CP业务日志
     */
    public static final String CALL_BACK_CP_LOGGER = "callBackCp_Logger";
    /**
     * 回调CP记录日志
     */
    public static final String CALL_BACK_CP_RECORD_LOGGER = "callBackCpRecord_Logger";
    /**
     * 其他系统回调日志
     */
    public static final String OTHER_CALL_BACK_LOGGER = "otherCallBack_Logger";
}
