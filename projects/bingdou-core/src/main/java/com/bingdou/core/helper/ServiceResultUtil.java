package com.bingdou.core.helper;

import com.bingdou.tools.JsonUtil;
import com.google.gson.JsonElement;

/**
 */
public class ServiceResultUtil {

    private ServiceResultUtil() {
    }

    /**
     * 构造token失效结果
     */
    public static ServiceResult tokenExpired() {
        return new ServiceResult(ReturnCode.TOKEN_EXPIRED, "令牌检查失败");
    }

    /**
     * 构造代金券过期结果
     */
    public static ServiceResult voucherExpired() {
        return new ServiceResult(ReturnCode.VOUCHER_EXPIRED, "代金券过期");
    }

    /**
     * 构造游客账号已经升级结果
     */
    public static ServiceResult guestLoginUpdated(JsonElement result) {
        return new ServiceResult(ReturnCode.GUEST_LOGIN_UPDATED, result, "游客账号已升级,请使用密码登录");
    }

    /**
     * 构造非法请求结果
     */
    public static ServiceResult illegal(String msg) {
        return new ServiceResult(ReturnCode.ILLEGAL_REQUEST, msg);
    }

    /**
     * 构造成功请求结果
     */
    public static ServiceResult success(JsonElement result) {
        return new ServiceResult(result);
    }

    /**
     * 构造成功请求结果
     */
    public static ServiceResult success(Object responseBean) {
        return success(JsonUtil.bean2JsonTree(responseBean));
    }

    /**
     * 构造成功请求结果
     */
    public static ServiceResult success() {
        return new ServiceResult();
    }

    /**
     * 构造服务器异常结果
     */
    public static ServiceResult serverError(String msg) {
        return new ServiceResult(ReturnCode.SERVER_ERROR, msg);
    }

}
