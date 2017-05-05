package com.bingdou.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户经验等级关系
 * Created by gaoshan on 17/4/28.
 */
public class UserExpGrade {

    /**
     * 用户当前经验
     */
    private Long expValue = 0l;
    /**
     * 用户当前实际的经验等级
     */
    private Integer userLevelId = 0;
    /**
     * 用户当前应对应的经验等级总共需要的经验
     */
    private Long expAmount = 0l;
    /**
     * 下一等级(针对expAmount)总共需要的经验
     */
    private Long nextLevelExpAmount = 0l;
    /**
     * 下一等级(针对expAmount)还需要的经验
     */
    private Long nextLevelNeedExpAmount = 0l;

    /**
     * 是否是新的VIP用户
     */
    private boolean isNewVipUser = false;



    public Integer getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(Integer userLevelId) {
        this.userLevelId = userLevelId;
    }

    public Long getExpValue() {
        return expValue;
    }

    public void setExpValue(Long expValue) {
        this.expValue = expValue;
    }

    public Long getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(Long expAmount) {
        this.expAmount = expAmount;
    }

    public Long getNextLevelExpAmount() {
        return nextLevelExpAmount;
    }

    public void setNextLevelExpAmount(Long nextLevelExpAmount) {
        this.nextLevelExpAmount = nextLevelExpAmount;
    }

    public Long getNextLevelNeedExpAmount() {
        return nextLevelNeedExpAmount;
    }

    public void setNextLevelNeedExpAmount(Long nextLevelNeedExpAmount) {
        this.nextLevelNeedExpAmount = nextLevelNeedExpAmount;
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
