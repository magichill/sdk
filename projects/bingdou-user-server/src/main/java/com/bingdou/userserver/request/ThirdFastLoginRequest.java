package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * 第三方授权登录请求
 */
public class ThirdFastLoginRequest extends BaseRequest {

    @SerializedName("third_auth_type")
    private int thirdAuthType;
    @SerializedName("auth_result")
    private JsonObject authResult;

    @Override
    protected String getLoggerName() {
        return "ThirdFastLoginRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        ThirdFastLoginRequest request = JsonUtil.jsonStr2Bean(requestString,
                ThirdFastLoginRequest.class);
        this.authResult = request.getAuthResult();
        this.thirdAuthType = request.getThirdAuthType();
        return request;
    }

    public JsonObject getAuthResult() {
        return authResult;
    }

    public void setAuthResult(JsonObject authResult) {
        this.authResult = authResult;
    }

    public int getThirdAuthType() {
        return thirdAuthType;
    }

    public void setThirdAuthType(int thirdAuthType) {
        this.thirdAuthType = thirdAuthType;
    }
}
