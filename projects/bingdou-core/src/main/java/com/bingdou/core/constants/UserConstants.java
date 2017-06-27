package com.bingdou.core.constants;

/**
 */
public class UserConstants {

    private UserConstants() {
    }

    /**
     * VIP最高等级
     */
    public static final int TOP_VIP_LEVEL_ID = 12;

    /**
     * 经验等级
     */
    public static final int TOP_EXP_LEVEL_ID = 200;
    /**
     * TOKEN过期时间7天
     */
    public static final int USER_TOKEN_EXPIRE_SECONDS = 604800;
    /**
     * CP TOKEN过期时间 1分钟
     */
    public static final int USER_VALIDATE_TOKEN_EXPIRE_SECONDS = 60;
    /**
     * 验证码过期时间（秒）
     */
    public static final int VALIDATION_CODE_EXPIRE_SECONDS = 1800;
    /**
     * 两次验证码发送时间间隔（秒）
     */
    public static final int VALIDATION_CODE_SEND_INTERVAL_SECONDS = 60;
    /**
     * 初始化调用软件应用商店超时时间(毫秒)
     */
    public static final int INIT_CALL_RESOURCE_APP_STORE_TIME_OUT = 1500;
    /**
     * 普通用户
     */
    public static final int NORMAL_USER_TYPE_INDEX_4_STATISTICS = 0;
    /**
     * 新增用户
     */
    public static final int NEW_USER_TYPE_INDEX_4_STATISTICS = 1;
    /**
     * 活跃用户
     */
    public static final int ACTIVE_USER_TYPE_INDEX_4_STATISTICS = 2;
    /**
     * cpid前缀
     */
    public static final String CP_PREFIX = "cpd";
    /**
     * 账户安全对应逻辑： 游客身份
     */
    public static final int SECURE_LEVEL_0 = 0;
    /**
     * 账户安全对应逻辑： 账号登陆
     */
    public static final int SECURE_LEVEL_1 = 1;
    /**
     * 账户安全对应逻辑：绑定了邮箱
     */
    public static final int SECURE_LEVEL_2 = 2;
    /**
     * 账户安全对应逻辑： 绑定了手机号
     */
    public static final int SECURE_LEVEL_3 = 3;
    /**
     * 账户安全对应逻辑： 绑定了手机号和邮箱
     */
    public static final int SECURE_LEVEL_4 = 4;
    /**
     * 账户安全对应逻辑:第三方授权
     */
    public static final int SECURE_LEVEL_5 = 5;
    /**
     * 短信发送验证码模板
     */
    public static final String SEND_CODE_TEMPLATE_4_SMS = "是您本次身份验证码，30分钟内有效，请及时使用，切勿告知他人。";
    /**
     * 邮件发送验证码模板
     */
    public static final String SEND_CODE_TEMPLATE_4_EMAIL = SEND_CODE_TEMPLATE_4_SMS + "【冰豆直播】";

    /**
     * 登录框显示的客服联系方式
     */
    public static final String USER_CUSTOM_SERVICE_NO = "1928175732,3065861697,2782722855";
    /**
     * 充值和支付中心显示的客服联系方式
     */
    public static final String PAY_CUSTOM_SERVICE_NO = "QQ 3065861697";
    /**
     * 用户安全等级:低
     */
    public static final int SAFE_LEVEL_LOW = 0;
    /**
     * 用户安全等级:中
     */
    public static final int SAFE_LEVEL_MEDIUM = 1;
    /**
     * 用户安全等级:高
     */
    public static final int SAFE_LEVEL_HIGH = 2;
    /**
     * 游客升级类型
     */
    public static final int ACCOUNT_UPDATE_TYPE_GUEST = 0;
    /**
     * 第三方账户升级类型
     */
    public static final int ACCOUNT_UPDATE_TYPE_THIRD = 1;
    /**
     * 一天内同设备发送短信或者邮件的限制次数
     */
    public static final int SEND_SMS_OR_EMAIL_COUNT_EVERY_DAY = 15;
    /**
     * 一天内同设备登录错误限制次数
     */
    public static final int DEVICE_LOGIN_ERROR_COUNT_EVERY_DAY = 10;
    /**
     * 设备登录黑名单未启用状态
     */
    public static final int DEVICE_LOGIN_BLACK_LIST_NO_STATUS = 0;
    /**
     * 设备登录黑名单启用状态
     */
    public static final int DEVICE_LOGIN_BLACK_LIST_YES_STATUS = 1;

    /**
     * 支付宝无限登录授权APP ID
     */
    public static final String ALI_FAST_LOGIN_AUTH_APP_ID = "2016051001385237";
    /**
     * 支付宝PKCS8 RSA私钥
     */
    public static final String ALI_PKCS8_RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALPuP+K5Dq1ZXOD2"
            + "rHJ7/mxa78IpwcJu/RWXE2jgavHZSB9yqFxW00qXSn8d14BJdKChYN9Gr1QVfXsn"
            + "5nukvZpvIXqN5hpob7c7SZmVufKbKzBaNB5qOGJHwqgOlDieGWLitXSFDdMyDnql"
            + "h4I2Z6XZ+eITUzovNoa9CXRnOhvpAgMBAAECgYBULHoAk7g1CmAz8dIcwLr2b9hI"
            + "CgbV8dw99G4lGw+M0qw14gq3H/s6ouo763b8UeDYoIZUDZpKCL+qFLFzWE9GHdS6"
            + "V3fSWCu3n4YRBcEPRc9OAv1B2vYQtAT8IJRMtPQkcbsB3xvHuZqjFUQYXXvJABFY"
            + "GjpY8BTxglJk3jvezQJBAORB+JxskP3GbDnBZjz11EASW9YFxPXkpZwafhTxMPfc"
            + "9LToem5zlIsh5x5D+UXQseXiZrOOBKqxtFayk7UNhEMCQQDJzKCCun4JKKoPNBQ4"
            + "QlozZYz2pdRja0/FL2gCom0h0Oe87l8c6NxNteucx7EsVyOMmZsGGu+s5mcCYiPh"
            + "69JjAkEAgAjFRDDdr4vSfx8NkxotYXcqzkVUMr17a7GskWCtIAtvs95HdpccdmKg"
            + "+mgUdeXXM+SnyQXbdkfRqWPn2IHpcwJAK0sHNISl/XkH4vMdU2SIzXX/4/p3skyX"
            + "dIpTGh2WpCaZUOMi7KTYfqOExMHitMyNB+D8bTbiLInRl6VOZW2eWQJBALbplKGU"
            + "vTPkdh2lTMx1aKNvBkcfXEjaNsk2Ds6fMGaJUCmRVh5U+VITR2HSRRdZf6EdzhSX"
            + "gAokMiEh1XB1Iqk=";
    /**
     * 支付宝授权登录成功状态码
     */
    public static final String ALI_FAST_LOGIN_AUTH_SUCCESS_STATUS = "9000";
    /**
     * 支付宝授权登录成功码
     */
    public static final String ALI_FAST_LOGIN_AUTH_SUCCESS_CODE = "200";
    /**
     * 发送验证码类型-短信
     */
    public static final int SEND_CODE_SMS_TYPE = 1;
    /**
     * 发送验证码类型-邮箱
     */
    public static final int SEND_CODE_EMAIL_TYPE = 2;

}
