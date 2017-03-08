package com.bingdou.payserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * 验证订单返回值
 * Created by gaoshan on 16/12/25.
 */
public class VerifyOrderResponse {

    @SerializedName("verify_result")
    private int verifyResult = -1;
    @SerializedName("cpid_or_id")
    private String cpIdOrId;
    @SerializedName("money")
    private Float money;
    @SerializedName("vip_level")
    private Integer vipLevel;
    @SerializedName("vip_up_need_money")
    private Float vipUpNeedMoney;
    @SerializedName("next_vip_level_money")
    private Float nextVipLevelMoney;

    public int getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(int verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getCpIdOrId() {
        return cpIdOrId;
    }

    public void setCpIdOrId(String cpIdOrId) {
        this.cpIdOrId = cpIdOrId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Float getVipUpNeedMoney() {
        return vipUpNeedMoney;
    }

    public void setVipUpNeedMoney(Float vipUpNeedMoney) {
        this.vipUpNeedMoney = vipUpNeedMoney;
    }

    public Float getNextVipLevelMoney() {
        return nextVipLevelMoney;
    }

    public void setNextVipLevelMoney(Float nextVipLevelMoney) {
        this.nextVipLevelMoney = nextVipLevelMoney;
    }
}
