package com.bingdou.core.model;

import com.bingdou.tools.NumberUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户VIP等级金额关系
 */
public class UserVipGrade {

    /**
     * 用户当前充值金额(元)
     */
    private Float money = 0f;
    /**
     * 用户当前实际的VIP等级
     */
    private Integer userLevelId = 0;
    /**
     * 用户当前应对应的VIP等级总共需要充值的金额(元)
     */
    private Float rechargeAmount = 0f;
    /**
     * 下一等级(针对rechargeAmount)总共需要充值的金额(元)
     */
    private Float nextLevelRechargeAmount = 0f;
    /**
     * 下一等级(针对rechargeAmount)还需要充值的金额(元)
     */
    private Float nextLevelNeedRechargeAmount = 0f;
    /**
     * 是否在黑名单中
     */
    private boolean isInBlackList = false;
    /**
     * 是否是新的VIP用户
     */
    private boolean isNewVipUser = false;

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(Integer userLevelId) {
        this.userLevelId = userLevelId;
    }

    public Float getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Float rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Float getNextLevelRechargeAmount() {
        return NumberUtil.formatFloat(nextLevelRechargeAmount) ;
    }

    public void setNextLevelRechargeAmount(Float nextLevelRechargeAmount) {
        this.nextLevelRechargeAmount = nextLevelRechargeAmount;
    }

    public Float getNextLevelNeedRechargeAmount() {
        return NumberUtil.formatFloat(nextLevelNeedRechargeAmount);
    }

    public void setNextLevelNeedRechargeAmount(Float nextLevelNeedRechargeAmount) {
        this.nextLevelNeedRechargeAmount = nextLevelNeedRechargeAmount;
    }

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        isInBlackList = inBlackList;
    }

    public boolean isNewVipUser() {
        return isNewVipUser;
    }

    public void setNewVipUser(boolean newVipUser) {
        isNewVipUser = newVipUser;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
