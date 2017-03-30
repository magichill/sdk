package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.UserStat;
import com.bingdou.core.model.UserVipGrade;
import com.bingdou.tools.NumberUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/2/16.
 */
public class UserStatsDataResponse {

    @SerializedName("live_count")
    private int liveCount = 0; //录播个数
    @SerializedName("like_count")
    private Integer likeCount = 0;
    @SerializedName("earnings")
    private float earnings = 0f;
    @SerializedName("balance")
    private float account = 0f;
    @SerializedName("followers")
    private Integer followers = 0;
    @SerializedName("vip_level")
    private int vipLevel = 0;
    @SerializedName("exp_value")
    private int expValue = 0;
    @SerializedName("income_loyalty")
    private int incomeLoyalty = 0;
    @SerializedName("consume_loyalty")
    private int consumLoyalty = 0;
    @SerializedName("level_id")
    private Integer levelId = 0;
    @SerializedName("vip_up_need_money")
    private Float vipUpNeedMoney = 0f;
    @SerializedName("next_vip_level_money")
    private Float nextVipLevelMoney = 0f;
    @SerializedName("user_recharge_money")
    private Float userRechargeMoney = 0f;
    @SerializedName("official_tag")
    private String officialTag = "";
    @SerializedName("other_tags")
    private String otherTags = "";
    @SerializedName("charged_at")
    private long chargeTime = 0l;

    public int getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public float getEarnings() {
        return earnings;
    }

    public void setEarnings(float earnings) {
        this.earnings = earnings;
    }

    public float getAccount() {
        return account;
    }

    public void setAccount(float account) {
        this.account = account;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getExpValue() {
        return expValue;
    }

    public void setExpValue(int expValue) {
        this.expValue = expValue;
    }

    public int getIncomeLoyalty() {
        return incomeLoyalty;
    }

    public void setIncomeLoyalty(int incomeLoyalty) {
        this.incomeLoyalty = incomeLoyalty;
    }

    public int getConsumLoyalty() {
        return consumLoyalty;
    }

    public void setConsumLoyalty(int consumLoyalty) {
        this.consumLoyalty = consumLoyalty;
    }

    public String getOfficialTag() {
        return officialTag;
    }

    public void setOfficialTag(String officialTag) {
        this.officialTag = officialTag;
    }

    public String getOtherTags() {
        return otherTags;
    }

    public void setOtherTags(String otherTags) {
        this.otherTags = otherTags;
    }

    public long getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(long chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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

    public Float getUserRechargeMoney() {
        return userRechargeMoney;
    }

    public void setUserRechargeMoney(Float userRechargeMoney) {
        this.userRechargeMoney = userRechargeMoney;
    }

    public void parseFromUser(User user, UserVipGrade userVipGrade,
                               UserStat userStat){
        if (user == null)
            return;


        setLevelId(user.getSafeLevel());
        setVipLevel(userVipGrade.getUserLevelId());
        setUserRechargeMoney(userVipGrade.getMoney());
        if (userVipGrade.getNextLevelRechargeAmount() > 0) {
            setNextVipLevelMoney(userVipGrade.getNextLevelRechargeAmount());
            setVipUpNeedMoney(userVipGrade.getNextLevelNeedRechargeAmount());
        } else {
            setNextVipLevelMoney(0f);
            setVipUpNeedMoney(0f);
        }
        setAccount(NumberUtil.convertYuanFromFen(user.getMoney()));
        setLikeCount(userStat.getLikeCount());
        setFollowers(userStat.getFollowers());
    }
}
