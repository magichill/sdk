package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

public class AliNoPwdPayRequest extends BaseRequest {

    @SerializedName("account")
    private String account;
    @SerializedName("money")
    private float money;
    @SerializedName("is_recharge")
    private int isRecharge;
    @SerializedName("recharge_info")
    private AliNoPwdPayRecharge aliNoPwdPayRecharge;
    @SerializedName("consume_info")
    private AliNoPwdPayConsume aliNoPwdPayConsume;

    @Override
    protected String getLoggerName() {
        return "AliNoPwdPayRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        AliNoPwdPayRequest request = JsonUtil.jsonStr2Bean(requestString, AliNoPwdPayRequest.class);
        this.account = request.getAccount();
        this.money = request.getMoney();
        this.isRecharge = request.getIsRecharge();
        this.aliNoPwdPayRecharge = request.getAliNoPwdPayRecharge();
        this.aliNoPwdPayConsume = request.getAliNoPwdPayConsume();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(int isRecharge) {
        this.isRecharge = isRecharge;
    }

    public AliNoPwdPayRecharge getAliNoPwdPayRecharge() {
        return aliNoPwdPayRecharge;
    }

    public void setAliNoPwdPayRecharge(AliNoPwdPayRecharge aliNoPwdPayRecharge) {
        this.aliNoPwdPayRecharge = aliNoPwdPayRecharge;
    }

    public AliNoPwdPayConsume getAliNoPwdPayConsume() {
        return aliNoPwdPayConsume;
    }

    public void setAliNoPwdPayConsume(AliNoPwdPayConsume aliNoPwdPayConsume) {
        this.aliNoPwdPayConsume = aliNoPwdPayConsume;
    }

    public class AliNoPwdPayRecharge {

        @SerializedName("activity_type")
        private int activityType;
        @SerializedName("recharge_card_id")
        private int rechargeCardId;
        @SerializedName("client_scene")
        private int clientScene;

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public int getRechargeCardId() {
            return rechargeCardId;
        }

        public void setRechargeCardId(int rechargeCardId) {
            this.rechargeCardId = rechargeCardId;
        }

        public int getClientScene() {
            return clientScene;
        }

        public void setClientScene(int clientScene) {
            this.clientScene = clientScene;
        }
    }

    public class AliNoPwdPayConsume {

        @SerializedName("goods_name")
        protected String goodsName;
        @SerializedName("goods_desc")
        protected String goodsDescription;
        @SerializedName("user_order_id")
        private String userOrderId;
        @SerializedName("user_order_param")
        private String userOrderParam;
        @SerializedName("voucher_id")
        private int voucherId;
        @SerializedName("is_contain_user_money")
        private int isContainUserMoney;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsDescription() {
            return goodsDescription;
        }

        public void setGoodsDescription(String goodsDescription) {
            this.goodsDescription = goodsDescription;
        }

        public String getUserOrderId() {
            return userOrderId;
        }

        public void setUserOrderId(String userOrderId) {
            this.userOrderId = userOrderId;
        }

        public String getUserOrderParam() {
            return userOrderParam;
        }

        public void setUserOrderParam(String userOrderParam) {
            this.userOrderParam = userOrderParam;
        }

        public int getVoucherId() {
            return voucherId;
        }

        public void setVoucherId(int voucherId) {
            this.voucherId = voucherId;
        }

        public int getIsContainUserMoney() {
            return isContainUserMoney;
        }

        public void setIsContainUserMoney(int isContainUserMoney) {
            this.isContainUserMoney = isContainUserMoney;
        }
    }

}
