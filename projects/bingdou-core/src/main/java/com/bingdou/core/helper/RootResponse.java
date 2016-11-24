package com.bingdou.core.helper;

import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.EncodeMethod;
import com.bingdou.tools.constants.KeyGroup;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RootResponse {

    @SerializedName("return_code")
    private String returnCode = ReturnCode.SUCCESS.getIndex();
    @SerializedName("error_message")
    private String errorMessage = "";
    @SerializedName("result")
    private JsonElement result = new JsonObject();

    public RootResponse(String returnCode, JsonElement result) {
        this.returnCode = returnCode;
        this.result = result;
    }

    public RootResponse(String returnCode, String errorMessage) {
        this.returnCode = returnCode;
        this.errorMessage = errorMessage;
    }

    public String convert2Result(KeyGroup keyGroup) {
        String result = null;
        try {
            String responseJsonStr = JsonUtil.bean2JsonStr(this);
            LogContext.instance().info(JsonUtil.formatJsonStr(responseJsonStr));
            if (EncodeMethod.AES.equals(keyGroup.getEncodeMethod())) {
                result = CodecUtils.aesEncode(responseJsonStr, keyGroup);
            } else {
                LogContext.instance().error("非法加密方法");
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "转化回复报文失败");
        } finally {
            LogContext.instance().info("---------------请求结束---------------");
        }
        return result;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public JsonElement getResult() {
        return result;
    }

    public void setResult(JsonElement result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
