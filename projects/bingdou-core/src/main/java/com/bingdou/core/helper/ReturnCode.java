package com.bingdou.core.helper;

/**
 * 返回值
 */
public enum ReturnCode {

    SUCCESS("1000"), ILLEGAL_REQUEST("1001"), SERVER_ERROR("1002"),
    TOKEN_EXPIRED("1003"), VOUCHER_EXPIRED("1004"), GUEST_LOGIN_UPDATED("1005"),
    ORDER_PAY_IN_PROCESS("1010"), ALI_PAY_NO_PWD_UN_SIGN("1011");

    private String index;

    ReturnCode(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setTypeIndex(String index) {
        this.index = index;
    }

    public static ReturnCode getReturnCodeByIndex(String index) {
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (returnCode.getIndex().equals(index))
                return returnCode;
        }
        return null;
    }
}
