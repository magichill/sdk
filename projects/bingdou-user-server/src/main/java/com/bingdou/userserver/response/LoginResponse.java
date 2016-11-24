package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.UserVipGrade;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * µÇÂ¼ÇëÇóÏìÓ¦
 */
public class LoginResponse {

    @SerializedName("user_id")
    private Integer userPrimaryId = 0;
    @SerializedName("cpid_or_id")
    private String cpIdOrId = "";
    @SerializedName("login_name")
    private String loginName = "";
    @SerializedName("mobile")
    private String mobile = "";
    @SerializedName("email")
    private String email = "";
    @SerializedName("money")
    private Float money = 0f;
    @SerializedName("virtual_money")
    private float virtualMoney = 0f;
    @SerializedName("safe_level")
    private int safeLevel = 0;
    @SerializedName("level_id")
    private Integer levelId = 0;
    @SerializedName("vip_level")
    private Integer vipLevel = 0;
    @SerializedName("vip_up_need_money")
    private Float vipUpNeedMoney = 0f;
    @SerializedName("next_vip_level_money")
    private Float nextVipLevelMoney = 0f;
    @SerializedName("user_recharge_money")
    private Float userRechargeMoney = 0f;
    @SerializedName("token")
    private String token = "";
    @SerializedName("v_token")
    private String vToken = "";
    @SerializedName("nick_name")
    private String nickName = "";

    @SerializedName("is_show_live_tag")
    private int isSupportLive = 0;
//    @SerializedName("is_support_virtual_money")
//    private int isSupportVirtualMoney = 0;
//    @SerializedName("message_list")
//    private List<MessageInfo> messageInfoList = new ArrayList<MessageInfo>();
//    @SerializedName("notice_list")
//    private List<NoticeInfo> noticeInfoList = new ArrayList<NoticeInfo>();
//    @SerializedName("ali_pay_no_pwd_auth")
//    private int aliPayNoPwdAuth = 0;
//    @SerializedName("ali_pay_no_pwd_auth_message")
//    private AliPayNoPwdAuthMessage aliPayNoPwdAuthMessage = new AliPayNoPwdAuthMessage();
    @SerializedName("unread_message_num")
    private int unreadMessageNum = 0;
    @SerializedName("is_need_update")
    private int isNeedUpdate = 0;

    public void parseFromUser(User user, UserVipGrade userVipGrade,
                              boolean isSupportVirtualMoney, boolean isSupportLive,
                              boolean aliPayNoPwdSigned, int virtualMoneyFen) {
        if (user == null)
            return;
        setUserPrimaryId(user.getId());
        setCpIdOrId(user.getReturnUserId());
        setLoginName(user.getLoginName());
        setMobile(user.getMobile());
        setEmail(user.getEmail());
        setVirtualMoney(NumberUtil.convertYuanFromFen(virtualMoneyFen));
        if (user.getMoney() != null)
            setMoney(NumberUtil.convertYuanFromFen(user.getMoney() + virtualMoneyFen));
        setSafeLevel(UserUtils.getSafeLevel(user));
        setLevelId(user.getSafeLevel());
        if(userVipGrade==null){
            setVipLevel(0);
        }else {
            setVipLevel(userVipGrade.getUserLevelId());
        }
        setNickName(user.getNickName());
        setToken(user.getToken());
        setvToken(user.getvToken());
        setIsSupportLive(isSupportLive ? 1 : 0);
//        setIsSupportVirtualMoney(isSupportVirtualMoney ? 1 : 0);
//        setAliPayNoPwdAuth(aliPayNoPwdSigned ? 1 : 0);
        setIsNeedUpdate(StringUtils.isEmpty(user.getPassword()) ? 1 : 0);
//        if (!aliPayNoPwdSigned) {
//            AliPayNoPwdAuthMessage aliPayNoPwdAuthMessage = new AliPayNoPwdAuthMessage();
//            aliPayNoPwdAuthMessage.setContent(PayConstants.ALI_PAY_NO_PWD_AUTH_CONTENT);
//            aliPayNoPwdAuthMessage.setTitle(PayConstants.ALI_PAY_NO_PWD_AUTH_TITLE);
//            setAliPayNoPwdAuthMessage(aliPayNoPwdAuthMessage);
//        }
//        setUserRechargeMoney(userVipGrade.getMoney());
//        if (userVipGrade.getNextLevelRechargeAmount() > 0) {
//            setNextVipLevelMoney(userVipGrade.getNextLevelRechargeAmount());
//            setVipUpNeedMoney(userVipGrade.getNextLevelNeedRechargeAmount());
//        } else {
//            setNextVipLevelMoney(0f);
//            setVipUpNeedMoney(0f);
//        }
    }

    public void parseFromUser4GetUserInfoListRequest(User user) {
        if (user == null)
            return;
        setUserPrimaryId(user.getId());
        setCpIdOrId(user.getReturnUserId());
        if (user.getVipBlackStatus() == null
                || user.getVipBlackStatus() != 1)
            setVipLevel(user.getVipLevel());
        setNickName(user.getNickName());
    }

    public String getCpIdOrId() {
        return cpIdOrId;
    }

    public void setCpIdOrId(String cpIdOrId) {
        if (StringUtils.isEmpty(cpIdOrId))
            return;
        this.cpIdOrId = cpIdOrId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName))
            return;
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        if (StringUtils.isEmpty(mobile))
            return;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (StringUtils.isEmpty(email))
            return;
        this.email = email;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        if (money == null)
            return;
        this.money = money;
    }

    public int getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(int safeLevel) {
        this.safeLevel = safeLevel;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        if (levelId == null)
            return;
        this.levelId = levelId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (StringUtils.isEmpty(token))
            return;
        this.token = token;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        if (vipLevel == null)
            return;
        this.vipLevel = vipLevel;
    }

    public Float getVipUpNeedMoney() {
        return vipUpNeedMoney;
    }

    public void setVipUpNeedMoney(Float vipUpNeedMoney) {
        if (vipUpNeedMoney == null)
            return;
        this.vipUpNeedMoney = vipUpNeedMoney;
    }

    public Float getNextVipLevelMoney() {
        return nextVipLevelMoney;
    }

    public void setNextVipLevelMoney(Float nextVipLevelMoney) {
        if (nextVipLevelMoney == null)
            return;
        this.nextVipLevelMoney = nextVipLevelMoney;
    }

    public Float getUserRechargeMoney() {
        return userRechargeMoney;
    }

    public void setUserRechargeMoney(Float userRechargeMoney) {
        if (userRechargeMoney == null)
            return;
        this.userRechargeMoney = userRechargeMoney;
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
            LogContext.instance().error(e, "×ª»»vtokenÊ§°Ü");
        }
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        if (StringUtils.isEmpty(nickName))
            return;
        this.nickName = nickName;
    }

    public Integer getUserPrimaryId() {
        return userPrimaryId;
    }

    public void setUserPrimaryId(Integer userPrimaryId) {
        if (userPrimaryId == null)
            return;
        this.userPrimaryId = userPrimaryId;
    }

    public int getIsSupportLive() {
        return isSupportLive;
    }

    public void setIsSupportLive(int isSupportLive) {
        this.isSupportLive = isSupportLive;
    }

//    public List<MessageInfo> getMessageInfoList() {
//        return messageInfoList;
//    }
//
//    public void setMessageInfoList(List<MessageInfo> messageInfoList) {
//        this.messageInfoList = messageInfoList;
//    }
//
//    public List<NoticeInfo> getNoticeInfoList() {
//        return noticeInfoList;
//    }
//
//    public void setNoticeInfoList(List<NoticeInfo> noticeInfoList) {
//        this.noticeInfoList = noticeInfoList;
//    }
//
//    public int getAliPayNoPwdAuth() {
//        return aliPayNoPwdAuth;
//    }
//
//    public void setAliPayNoPwdAuth(int aliPayNoPwdAuth) {
//        this.aliPayNoPwdAuth = aliPayNoPwdAuth;
//    }
//
//    public AliPayNoPwdAuthMessage getAliPayNoPwdAuthMessage() {
//        return aliPayNoPwdAuthMessage;
//    }
//
//    public void setAliPayNoPwdAuthMessage(AliPayNoPwdAuthMessage aliPayNoPwdAuthMessage) {
//        this.aliPayNoPwdAuthMessage = aliPayNoPwdAuthMessage;
//    }

    public int getUnreadMessageNum() {
        return unreadMessageNum;
    }

    public void setUnreadMessageNum(int unreadMessageNum) {
        this.unreadMessageNum = unreadMessageNum;
    }

    public int getIsNeedUpdate() {
        return isNeedUpdate;
    }

    public void setIsNeedUpdate(int isNeedUpdate) {
        this.isNeedUpdate = isNeedUpdate;
    }

    public float getVirtualMoney() {
        return virtualMoney;
    }

    public void setVirtualMoney(float virtualMoney) {
        this.virtualMoney = virtualMoney;
    }

//    public int getIsSupportVirtualMoney() {
//        return isSupportVirtualMoney;
//    }
//
//    public void setIsSupportVirtualMoney(int isSupportVirtualMoney) {
//        this.isSupportVirtualMoney = isSupportVirtualMoney;
//    }
}
