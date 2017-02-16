package com.bingdou.tools.constants;

/**
 * Hadoop入库日志动作
 */
public enum HadoopLogAction {

    MONITOR_TEST(-1, "监控测试"),
    INIT(0, "初始化"), REGISTER(1, "用户名注册"), LOGIN(2, "登录"),
    GUEST_LOGIN(3, "游客登录"), CREATE_RECHARGE_ORDER(4, "创建充值订单"),
    CONSUME_ORDER_SUCCESS(5, "消费订单成功(海马币)"), CREATE_CP_PAY_ORDER(6, "创建直充订单"),
    RECHARGE_ORDER_SUCCESS(7, "充值订单成功"), CP_PAY_ORDER_SUCCESS(8, "直充订单成功"),
    GIVE_VOUCHER(9, "领取代金券"), CREATE_CONSUME_ORDER(10, "创建消费订单(海马币)"),
    SEND_MESSAGE(11, "下发消息"), GUEST_REGISTER(12, "游客注册"), AUTO_LOGIN(13, "自动登录"),
    SEND_CODE(14, "下发验证码"), FIND_PWD(15, "找回密码"), BIND_MOBILE_OR_EMAIL(16, "绑定邮箱或手机号"),
    PHONE_REGISTER(17, "手机号注册"), GUEST_LOGIN_UPDATED(18, "升级游客账号游客登录"),
    THIRD_FAST_LOGIN_REGISTER(19, "第三方账号注册"), THIRD_FAST_LOGIN(20, "第三方账号登录"),
    ACCOUNT_UPDATE(21, "账号升级"), MODIFY_PASSWORD(22, "修改密码"), LOGOUT(23, "注销"),
    CHECK_PASSWORD(24, "检查密码"), NOTICE_MESSAGE_SEND(25, "公告BANNER下发");

    private int index;
    private String description;

    HadoopLogAction(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
