package com.bingdou.userserver.response;

import com.bingdou.core.constants.UserConstants;
import com.google.gson.JsonObject;

public class AliFastLoginAuthResult extends FastLoginAuthResult {

    private String resultStatus;
    private String result;
    private String memo;
    private String resultCode;

    @Override
    protected boolean isAuthSuccess() {
        return UserConstants.ALI_FAST_LOGIN_AUTH_SUCCESS_STATUS.equals(this.resultStatus)
                && UserConstants.ALI_FAST_LOGIN_AUTH_SUCCESS_CODE.equals(this.resultCode);
    }

    @Override
    protected void parse(JsonObject authResult) throws Exception {
        if (authResult == null) {
            throw new Exception("转换支付宝授权结果JSON失败");
        }
        this.resultStatus = authResult.get("resultStatus").getAsString();
        this.result = authResult.get("result").getAsString();
        this.memo = authResult.get("memo").getAsString();
        String[] resultValue = result.split("&");
        for (String value : resultValue) {
            if (value.startsWith("alipay_open_id")) {
                this.openId = getValue(value);
            }
            if (value.startsWith("auth_code")) {
                this.authCode = getValue(value);
            }
            if (value.startsWith("result_code")) {
                this.resultCode = getValue(value);
            }
            if (value.startsWith("sign")) {
                this.sign = getValue(value);
            }
        }
    }

    private String getValue(String data) {
        String[] content = data.split("=\"");
        if (content.length < 2)
            return "";
        String value = content[1];
        if (value.endsWith("\""))
            return value.substring(0, value.lastIndexOf("\""));
        return value;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
