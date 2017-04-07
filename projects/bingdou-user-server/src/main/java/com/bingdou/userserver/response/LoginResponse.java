package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.UserStat;
import com.bingdou.core.model.UserVipGrade;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import com.bingdou.userserver.constant.ResponseConstant;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 登录请求响应
 */
public class LoginResponse {

    @SerializedName("user_profile")
    private UserProfileResponse userProfileResponse;
    @SerializedName("user_stats_data")
    private UserStatsDataResponse userStatsDataResponse;
//    @SerializedName("user_id")
//    private Integer userPrimaryId = 0;
//    @SerializedName("cpid_or_id")
//    private String cpIdOrId = "";
//    @SerializedName("login_name")
//    private String loginName = "";
//    @SerializedName("mobile")
//    private String mobile = "";
//    @SerializedName("email")
//    private String email = "";
//    @SerializedName("money")
//    private Float money = 0f;
//    @SerializedName("virtual_money")
//    private float virtualMoney = 0f;
//    @SerializedName("safe_level")
//    private int safeLevel = 0;
//    @SerializedName("level_id")
//    private Integer levelId = 0;
//    @SerializedName("vip_level")
//    private Integer vipLevel = 0;
//    @SerializedName("vip_up_need_money")
//    private Float vipUpNeedMoney = 0f;
//    @SerializedName("next_vip_level_money")
//    private Float nextVipLevelMoney = 0f;
//    @SerializedName("user_recharge_money")
//    private Float userRechargeMoney = 0f;
    @SerializedName("token")
    private String token = "";
    @SerializedName("v_token")
    private String vToken = "";
//    @SerializedName("nick_name")
//    private String nickName = "";
//    @SerializedName("unread_message_num")
//    private int unreadMessageNum = 0;
//    @SerializedName("is_need_update")
//    private int isNeedUpdate = 0;
//    @SerializedName("avatar_url")
//    private String avatar = "http://tva2.sinaimg.cn/crop.3.0.634.634.1024/cd516b22jw8fa4mlfynwzj20hs0hm0tr.jpg";
//    @SerializedName("signature")
//    private String signature = "这个人的签名被吃了";
//    @SerializedName("certification_status")
//    private Integer certificationStatus = 0;
//    @SerializedName("like_count")
//    private Integer likeCount = 0;
//    @SerializedName("followers")
//    private Integer followers = 0;

    public void parseFromUser(User user, UserVipGrade userVipGrade,
                              int certificationStatus, int virtualMoneyFen, UserStat userStat){
        if (user == null)
            return;
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        UserStatsDataResponse statsDataResponse = new UserStatsDataResponse();
        userProfileResponse.setUserPrimaryId(user.getId());
        userProfileResponse.setCpIdOrId(user.getReturnUserId());
        userProfileResponse.setLevel(user.getVipLevel());
        userProfileResponse.setGender(user.getGender());
        userProfileResponse.setAvatar(user.getAvatar());

        userProfileResponse.setMobile(user.getMobile());

        userProfileResponse.setSignature(user.getSignature()==null? ResponseConstant.DEFAULT_SIGNATURE:user.getSignature());
        statsDataResponse.setLevelId(user.getSafeLevel());
        statsDataResponse.setVipLevel(userVipGrade.getUserLevelId());
        userProfileResponse.setNickName(user.getNickName());
        setToken(user.getToken());
        setvToken(user.getvToken());


        statsDataResponse.setUserRechargeMoney(userVipGrade.getMoney());
        statsDataResponse.setAccount(user.getMoney());
        buildVipData(userVipGrade,statsDataResponse);
        userProfileResponse.setCertificationStatus(certificationStatus);
        if(userStat != null) {
            statsDataResponse.setLikeCount(userStat.getLikeCount());
            statsDataResponse.setFollowers(userStat.getFollowers());
        }else{
            statsDataResponse.setLikeCount(0);
            statsDataResponse.setFollowers(0);
        }
        setUserProfileResponse(userProfileResponse);
        setUserStatsDataResponse(statsDataResponse);
    }
    public void parseFromUser(User user, UserVipGrade userVipGrade,
                              boolean isSupportVirtualMoney,
                              boolean aliPayNoPwdSigned, int virtualMoneyFen) {
//        if (user == null)
//            return;
//        setUserPrimaryId(user.getId());
//        setCpIdOrId(user.getReturnUserId());
//        setLoginName(user.getLoginName());
//        setMobile(user.getMobile());
//        setEmail(user.getEmail());
//        setVirtualMoney(NumberUtil.convertYuanFromFen(virtualMoneyFen));
//        if (user.getMoney() != null)
//            setMoney(NumberUtil.convertYuanFromFen(user.getMoney() + virtualMoneyFen));
//        setSafeLevel(UserUtils.getSafeLevel(user));
//        setLevelId(user.getSafeLevel());
//        if(userVipGrade==null){
//            setVipLevel(0);
//        }else {
//            setVipLevel(userVipGrade.getUserLevelId());
//        }
//        setNickName(user.getNickName());
//        setToken(user.getToken());
//        setvToken(user.getvToken());
//        setIsNeedUpdate(StringUtils.isEmpty(user.getPassword()) ? 1 : 0);
        if (user == null)
            return;
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        UserStatsDataResponse statsDataResponse = new UserStatsDataResponse();
        userProfileResponse.setUserPrimaryId(user.getId());
        userProfileResponse.setCpIdOrId(user.getReturnUserId());
        userProfileResponse.setLevel(user.getVipLevel());
        userProfileResponse.setGender(user.getGender());
        userProfileResponse.setAvatar(user.getAvatar());

        userProfileResponse.setMobile(user.getMobile());

        userProfileResponse.setSignature(user.getSignature()==null? ResponseConstant.DEFAULT_SIGNATURE:user.getSignature());
        statsDataResponse.setLevelId(user.getSafeLevel());
        statsDataResponse.setVipLevel(userVipGrade.getUserLevelId());
        statsDataResponse.setAccount(user.getMoney());
        userProfileResponse.setNickName(user.getNickName());
        setToken(user.getToken());
        setvToken(user.getvToken());

        buildStatsDataResponse(userVipGrade,statsDataResponse);
        userProfileResponse.setCertificationStatus(0);
        statsDataResponse.setLikeCount(0);
        statsDataResponse.setFollowers(0);
        setUserProfileResponse(userProfileResponse);
        setUserStatsDataResponse(statsDataResponse);
    }

//    public void parseFromUser4GetUserInfoListRequest(User user) {
//        if (user == null)
//            return;
//        setUserPrimaryId(user.getId());
//        setCpIdOrId(user.getReturnUserId());
//        if (user.getVipBlackStatus() == null
//                || user.getVipBlackStatus() != 1)
//            setVipLevel(user.getVipLevel());
//        setNickName(user.getNickName());
//    }

    private UserStatsDataResponse buildStatsDataResponse(UserVipGrade userVipGrade,UserStatsDataResponse statsDataResponse){
        if(userVipGrade != null) {
            statsDataResponse.setVipLevel(userVipGrade.getUserLevelId());
            statsDataResponse.setUserRechargeMoney(userVipGrade.getMoney());
            buildVipData(userVipGrade,statsDataResponse);
        }else{
            statsDataResponse.setVipLevel(0);
            statsDataResponse.setUserRechargeMoney(0f);
            statsDataResponse.setNextVipLevelMoney(0f);
            statsDataResponse.setVipUpNeedMoney(0f);
        }
        return statsDataResponse;
    }

    private UserStatsDataResponse buildVipData(UserVipGrade userVipGrade,UserStatsDataResponse statsDataResponse){
        if (userVipGrade.getNextLevelRechargeAmount() > 0) {
            statsDataResponse.setNextVipLevelMoney(userVipGrade.getNextLevelRechargeAmount());
            statsDataResponse.setVipUpNeedMoney(userVipGrade.getNextLevelNeedRechargeAmount());
        } else {
            statsDataResponse.setNextVipLevelMoney(0f);
            statsDataResponse.setVipUpNeedMoney(0f);
        }
        return statsDataResponse;
    }
    public UserProfileResponse getUserProfileResponse() {
        return userProfileResponse;
    }

    public void setUserProfileResponse(UserProfileResponse userProfileResponse) {
        this.userProfileResponse = userProfileResponse;
    }

    public UserStatsDataResponse getUserStatsDataResponse() {
        return userStatsDataResponse;
    }

    public void setUserStatsDataResponse(UserStatsDataResponse userStatsDataResponse) {
        this.userStatsDataResponse = userStatsDataResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getvToken() {
        return vToken;
    }

    public void setvToken(String vToken) {
        if (StringUtils.isEmpty(vToken))
            return;
        try {
            this.vToken = Base64.encodeBase64String(vToken.getBytes("UTF-8"));
        } catch (Exception e) {
            LogContext.instance().error(e, "转换vtoken失败");
        }
    }

//    public String getCpIdOrId() {
//        return cpIdOrId;
//    }
//
//    public void setCpIdOrId(String cpIdOrId) {
//        if (StringUtils.isEmpty(cpIdOrId))
//            return;
//        this.cpIdOrId = cpIdOrId;
//    }
//
//    public String getLoginName() {
//        return loginName;
//    }
//
//    public void setLoginName(String loginName) {
//        if (StringUtils.isEmpty(loginName))
//            return;
//        this.loginName = loginName;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        if (StringUtils.isEmpty(mobile))
//            return;
//        this.mobile = mobile;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        if (StringUtils.isEmpty(email))
//            return;
//        this.email = email;
//    }
//
//    public Float getMoney() {
//        return money;
//    }
//
//    public void setMoney(Float money) {
//        if (money == null)
//            return;
//        this.money = money;
//    }
//
//    public int getSafeLevel() {
//        return safeLevel;
//    }
//
//    public void setSafeLevel(int safeLevel) {
//        this.safeLevel = safeLevel;
//    }
//
//    public Integer getLevelId() {
//        return levelId;
//    }
//
//    public void setLevelId(Integer levelId) {
//        if (levelId == null)
//            return;
//        this.levelId = levelId;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        if (StringUtils.isEmpty(token))
//            return;
//        this.token = token;
//    }
//
//    public Integer getVipLevel() {
//        return vipLevel;
//    }
//
//    public void setVipLevel(Integer vipLevel) {
//        if (vipLevel == null)
//            return;
//        this.vipLevel = vipLevel;
//    }
//
//    public Float getVipUpNeedMoney() {
//        return vipUpNeedMoney;
//    }
//
//    public void setVipUpNeedMoney(Float vipUpNeedMoney) {
//        if (vipUpNeedMoney == null)
//            return;
//        this.vipUpNeedMoney = vipUpNeedMoney;
//    }
//
//    public Float getNextVipLevelMoney() {
//        return nextVipLevelMoney;
//    }
//
//    public void setNextVipLevelMoney(Float nextVipLevelMoney) {
//        if (nextVipLevelMoney == null)
//            return;
//        this.nextVipLevelMoney = nextVipLevelMoney;
//    }
//
//    public Float getUserRechargeMoney() {
//        return userRechargeMoney;
//    }
//
//    public void setUserRechargeMoney(Float userRechargeMoney) {
//        if (userRechargeMoney == null)
//            return;
//        this.userRechargeMoney = userRechargeMoney;
//    }
//
//    public String getvToken() {
//        return vToken;
//    }
//
//    public void setvToken(String vToken) {
//        if (StringUtils.isEmpty(vToken))
//            return;
//        try {
//            this.vToken = Base64.encodeBase64String(vToken.getBytes("UTF-8"));
//        } catch (Exception e) {
//            LogContext.instance().error(e, "转换vtoken失败");
//        }
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        if (StringUtils.isEmpty(nickName))
//            return;
//        this.nickName = nickName;
//    }
//
//    public Integer getUserPrimaryId() {
//        return userPrimaryId;
//    }
//
//    public void setUserPrimaryId(Integer userPrimaryId) {
//        if (userPrimaryId == null)
//            return;
//        this.userPrimaryId = userPrimaryId;
//    }
//
//    public int getUnreadMessageNum() {
//        return unreadMessageNum;
//    }
//
//    public void setUnreadMessageNum(int unreadMessageNum) {
//        this.unreadMessageNum = unreadMessageNum;
//    }
//
//    public int getIsNeedUpdate() {
//        return isNeedUpdate;
//    }
//
//    public void setIsNeedUpdate(int isNeedUpdate) {
//        this.isNeedUpdate = isNeedUpdate;
//    }
//
//    public float getVirtualMoney() {
//        return virtualMoney;
//    }
//
//    public void setVirtualMoney(float virtualMoney) {
//        this.virtualMoney = virtualMoney;
//    }
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//
//    public String getSignature() {
//        return signature;
//    }
//
//    public void setSignature(String signature) {
//        this.signature = signature;
//    }
//
//    public Integer getCertificationStatus() {
//        return certificationStatus;
//    }
//
//    public void setCertificationStatus(Integer certificationStatus) {
//        this.certificationStatus = certificationStatus;
//    }
//
//    public Integer getLikeCount() {
//        return likeCount;
//    }
//
//    public void setLikeCount(Integer likeCount) {
//        this.likeCount = likeCount;
//    }
//
//    public Integer getFollowers() {
//        return followers;
//    }
//
//    public void setFollowers(Integer followers) {
//        this.followers = followers;
//    }

//    public int getIsSupportVirtualMoney() {
//        return isSupportVirtualMoney;
//    }
//
//    public void setIsSupportVirtualMoney(int isSupportVirtualMoney) {
//        this.isSupportVirtualMoney = isSupportVirtualMoney;
//    }
}
