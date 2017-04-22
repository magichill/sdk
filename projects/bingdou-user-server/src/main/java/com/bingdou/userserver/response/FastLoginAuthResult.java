package com.bingdou.userserver.response;

import com.bingdou.tools.LogContext;
import com.google.gson.JsonObject;


/**
 * Created by gaoshan on 17/2/21.
 */
public abstract class FastLoginAuthResult {

    protected String openId;
    protected String authCode;
    protected String sign;
    protected String unionId;

    protected abstract void parse(JsonObject authResult) throws Exception;

    protected abstract boolean isAuthSuccess();

    public boolean execute(JsonObject authResult) {
        try {
            parse(authResult);
            return isAuthSuccess();
        } catch (Exception e) {
            LogContext.instance().error(e, "第三方授权登录失败");
        }
        return false;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
