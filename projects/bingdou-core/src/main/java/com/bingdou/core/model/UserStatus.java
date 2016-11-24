package com.bingdou.core.model;

/**
 */
public enum UserStatus {

    VALID(1, "可用"),
    INVALID(0, "未启用"),
    PAUSE(2, "已暂停"),
    DENY(5, "已禁用");

    private int status;
    private String name;

    UserStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public static UserStatus valueOf(int value) {
        for (UserStatus us : UserStatus.values()) {
            if (us.getStatus() == value) {
                return us;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public int getStatus() {
        return this.status;
    }
}
