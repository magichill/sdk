package com.bingdou.tools.constants;

/**
 * Hadoop入库日志动作
 */
public enum HadoopLogAction {
    MONITOR_TEST(-1, "监控测试"),
    INIT(0, "初始化"), REGISTER(1, "用户名注册"), LOGIN(2, "登录"),
    GUEST_LOGIN(3, "游客登录"), GUEST_REGISTER(4, "游客注册"), AUTO_LOGIN(5, "自动登录"),
    SEND_CODE(6, "下发验证码"), FIND_PWD(7, "找回密码"), BIND_MOBILE_OR_EMAIL(8, "绑定邮箱或手机号"),
    PHONE_REGISTER(9, "手机号注册"), THIRD_FAST_LOGIN_REGISTER(10, "第三方账号注册"), THIRD_FAST_LOGIN(11, "第三方账号登录"),
    ACCOUNT_UPDATE(12, "账号升级"), MODIFY_PASSWORD(13, "修改密码"), LOGOUT(14, "注销"),
    CHECK_PASSWORD(15, "检查密码"),GUEST_LOGIN_UPDATED(16, "升级游客账号游客登录"),;

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
