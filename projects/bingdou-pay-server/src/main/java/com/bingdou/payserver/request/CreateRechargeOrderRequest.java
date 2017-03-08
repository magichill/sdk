package com.bingdou.payserver.request;

import com.google.gson.annotations.SerializedName;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * 创建充值订单请求
 * Created by gaoshan on 17/02/03.
 */
public class CreateRechargeOrderRequest extends CreateOrderRequest {

    @SerializedName("activity_type")
    private int activityType;
    @SerializedName("recharge_card_id")
    private int rechargeCardId;
    @SerializedName("client_scene")
    private int clientScene;
    @SerializedName("19_pay")
    private CardPay cardPay;

    @Override
    protected String getLoggerName() {
        return "CreateRechargeOrderRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        CreateRechargeOrderRequest request = JsonUtil.jsonStr2Bean(requestString, CreateRechargeOrderRequest.class);
        this.account = request.getAccount();
        this.money = request.getMoney();
        this.payType = request.getPayType();
        this.goodsName = request.getGoodsName();
        this.goodsDescription = request.getGoodsDescription();
        this.other = request.getOther();
        this.activityType = request.getActivityType();
        this.rechargeCardId = request.getRechargeCardId();
        this.clientScene = request.getClientScene();
        this.cardPay = request.getCardPay();
        this.returnUrl = request.getReturnUrl();
        this.openId = request.getOpenId();
        return request;
    }

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

    public CardPay getCardPay() {
        return cardPay;
    }

    public void setCardPay(CardPay cardPay) {
        this.cardPay = cardPay;
    }

    public class CardPay {

        @SerializedName("type")
        private int type;
        @SerializedName("card_no")
        private String cardNo;
        @SerializedName("card_password")
        private String cardPassword;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardPassword() {
            return cardPassword;
        }

        public void setCardPassword(String cardPassword) {
            this.cardPassword = cardPassword;
        }
    }
}
