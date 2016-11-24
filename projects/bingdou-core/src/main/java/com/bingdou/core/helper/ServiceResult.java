package com.bingdou.core.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by gaoshan on 16-11-1.
 */
public class ServiceResult {

    private JsonElement result;
    private ReturnCode returnCode;
    private String errorMessage;

    public ServiceResult() {
        this.returnCode = ReturnCode.SUCCESS;
        this.result = new JsonObject();
    }

    public ServiceResult(JsonElement result) {
        this.result = result;
        this.returnCode = ReturnCode.SUCCESS;
    }

    public ServiceResult(ReturnCode returnCode, JsonElement result, String errorMessage) {
        this.result = result;
        this.returnCode = returnCode;
        this.errorMessage = errorMessage;
    }

    public ServiceResult(ReturnCode returnCode, JsonElement result) {
        this.result = result;
        this.returnCode = returnCode;
    }

    public ServiceResult(ReturnCode returnCode, String errorMessage) {
        this.returnCode = returnCode;
        this.errorMessage = errorMessage;
        this.result = new JsonObject();
    }

    public JsonElement getResult() {
        return result;
    }

    public void setResult(JsonElement result) {
        this.result = result;
    }

    public ReturnCode getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
