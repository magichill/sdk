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
     * 发送验证码类型-短信
     */
    public static final int SEND_CODE_SMS_TYPE = 1;
    /**
     * 发送验证码类型-邮箱
     */
    public static final int SEND_CODE_EMAIL_TYPE = 2;

}
