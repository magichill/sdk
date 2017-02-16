package com.bingdou.core.service.pay.paytype;


import com.bingdou.core.model.AliNoPwdPayReturnCode;

public class PayTypeResponse {

    private boolean isSuccess;
    private String resultMessage;
    private String resultCode;
//    private PPResponse ppResponse;
    private WxResponse wxResponse;
//    private UpmpResponse upmpResponse;
    private String requestUrl;
    private String requestParam;
//    private HeepaySdkWxResponse heepaySdkWxResponse;
    private AliNoPwdPayReturnCode aliNoPwdPayReturnCode;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

//    public PPResponse getPpResponse() {
//        return ppResponse;
//    }
//
//    public void setPpResponse(PPResponse ppResponse) {
//        this.ppResponse = ppResponse;
//    }

    public WxResponse getWxResponse() {
        return wxResponse;
    }

    public void setWxResponse(WxResponse wxResponse) {
        this.wxResponse = wxResponse;
    }

//    public UpmpResponse getUpmpResponse() {
//        return upmpResponse;
//    }
//
//    public void setUpmpResponse(UpmpResponse upmpResponse) {
//        this.upmpResponse = upmpResponse;
//    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

//    public HeepaySdkWxResponse getHeepaySdkWxResponse() {
//        return heepaySdkWxResponse;
//    }
//
//    public void setHeepaySdkWxResponse(HeepaySdkWxResponse heepaySdkWxResponse) {
//        this.heepaySdkWxResponse = heepaySdkWxResponse;
//    }
//
    public AliNoPwdPayReturnCode getAliNoPwdPayReturnCode() {
        return aliNoPwdPayReturnCode;
    }

    public void setAliNoPwdPayReturnCode(AliNoPwdPayReturnCode aliNoPwdPayReturnCode) {
        this.aliNoPwdPayReturnCode = aliNoPwdPayReturnCode;
    }
}
