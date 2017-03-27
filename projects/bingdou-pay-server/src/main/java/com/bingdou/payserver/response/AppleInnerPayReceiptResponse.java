package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 苹果应用内支付凭证返回对象
 * Created by gaoshan on 17/3/35.
 */
public class AppleInnerPayReceiptResponse {

    @SerializedName("status")
    private int status;
    @SerializedName("environment")
    private String environment;
    @SerializedName("receipt")
    private Receipt receipt;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public class Receipt {

        @SerializedName("receipt_type")
        private String receiptType;
        @SerializedName("adam_id")
        private int adamId;
        @SerializedName("app_item_id")
        private int appItemId;
        @SerializedName("bundle_id")
        private String bundleId;
        @SerializedName("application_version")
        private String applicationVersion;
        @SerializedName("receipt_creation_date_ms")
        private long receiptCreationDateMs;
        @SerializedName("request_date_ms")
        private long requestDateMs;
        @SerializedName("original_application_version")
        private String originalApplicationVersion;
        @SerializedName("in_app")
        private List<InApp> inAppList;

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

        public List<InApp> getInAppList() {
            return inAppList;
        }

        public void setInAppList(List<InApp> inAppList) {
            this.inAppList = inAppList;
        }

        public class InApp {

            @SerializedName("quantity")
            private int quantity;
            @SerializedName("product_id")
            private String productId;
            @SerializedName("transaction_id")
            private String transactionId;
            @SerializedName("original_transaction_id")
            private String originalTransactionId;
            @SerializedName("purchase_date_ms")
            private long purchaseDateMs;
            @SerializedName("original_purchase_date_ms")
            private long originalPurchaseDateMs;
            @SerializedName("is_trial_period")
            private boolean isTrialPeriod;

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
        }
    }

}
