package com.bingdou.core.model;

/**
 * 苹果应用内支付凭证
 * Created by gaoshan on 17/3/35.
 */
public class AppleInnerPayReceipt {

    private int status;
    private String environment;
    private String receiptType;
    private int adamId;
    private int appItemId;
    private String bundleId;
    private String applicationVersion;
    private long receiptCreationDateMs;
    private long requestDateMs;
    private String originalApplicationVersion;
    private int quantity;
    private String productId;
    private String transactionId;
    private String originalTransactionId;
    private long purchaseDateMs;
    private long originalPurchaseDateMs;
    private boolean isTrialPeriod;
    private String rechargeOrderId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    public int getAdamId() {
        return adamId;
    }

    public void setAdamId(int adamId) {
        this.adamId = adamId;
    }

    public int getAppItemId() {
        return appItemId;
    }

    public void setAppItemId(int appItemId) {
        this.appItemId = appItemId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public long getReceiptCreationDateMs() {
        return receiptCreationDateMs;
    }

    public void setReceiptCreationDateMs(long receiptCreationDateMs) {
        this.receiptCreationDateMs = receiptCreationDateMs;
    }

    public long getRequestDateMs() {
        return requestDateMs;
    }

    public void setRequestDateMs(long requestDateMs) {
        this.requestDateMs = requestDateMs;
    }

    public String getOriginalApplicationVersion() {
        return originalApplicationVersion;
    }

    public void setOriginalApplicationVersion(String originalApplicationVersion) {
        this.originalApplicationVersion = originalApplicationVersion;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOriginalTransactionId() {
        return originalTransactionId;
    }

    public void setOriginalTransactionId(String originalTransactionId) {
        this.originalTransactionId = originalTransactionId;
    }

    public long getPurchaseDateMs() {
        return purchaseDateMs;
    }

    public void setPurchaseDateMs(long purchaseDateMs) {
        this.purchaseDateMs = purchaseDateMs;
    }

    public long getOriginalPurchaseDateMs() {
        return originalPurchaseDateMs;
    }

    public void setOriginalPurchaseDateMs(long originalPurchaseDateMs) {
        this.originalPurchaseDateMs = originalPurchaseDateMs;
    }

    public boolean isTrialPeriod() {
        return isTrialPeriod;
    }

    public void setTrialPeriod(boolean trialPeriod) {
        isTrialPeriod = trialPeriod;
    }

    public String getRechargeOrderId() {
        return rechargeOrderId;
    }

    public void setRechargeOrderId(String rechargeOrderId) {
        this.rechargeOrderId = rechargeOrderId;
    }
}
