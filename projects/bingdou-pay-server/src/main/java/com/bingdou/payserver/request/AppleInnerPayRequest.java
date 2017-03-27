package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 苹果应用内支付请求
 */
public class AppleInnerPayRequest extends BaseRequest {

    @SerializedName("account")
    private String account;
    @SerializedName("order_money")
    private float orderMoney;
    @SerializedName("product_list")
    private List<AppleInnerPayProduct> productList;
    @SerializedName("receipt_data")
    private String receiptData;

    @Override
    protected String getLoggerName() {
        return "AppleInnerPayRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        AppleInnerPayRequest request = JsonUtil.jsonStr2Bean(requestString, AppleInnerPayRequest.class);
        this.orderMoney = request.getOrderMoney();
        this.account = request.getAccount();
        this.receiptData = request.getReceiptData();
        this.productList = request.getProductList();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public List<AppleInnerPayProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<AppleInnerPayProduct> productList) {
        this.productList = productList;
    }

    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }
}
