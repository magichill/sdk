package com.bingdou.core.constants;

/**
 * REDIS 常量
 */
public class RedisDBConstants {

    private RedisDBConstants() {
    }

    /**
     * 用户TOKEN
     */
    public static final int USER_TOKEN_DB_INDEX = 1;
    /**
     * 应用所属用户表
     */
    public static final int USER_STATISTICS_DB_INDEX = 2;
    /**
     * 支付宝免密授权
     */
    public static final int ALI_NO_PWD_AUTH_DB_INDEX = 4;
    /**
     * 发送短信和邮件
     */
    public static final int SEND_SMS_OR_EMAIL_DB_INDEX = 5;
    /**
     * 安全信息
     */
    public static final int SAFE_DB_INDEX = 6;
    /**
     * 充返活动
     */
    public static final int CHARGE_BACK_ACTIVITY_DB_INDEX = 7;
    
}
