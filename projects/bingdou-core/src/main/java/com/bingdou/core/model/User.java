package com.bingdou.core.model;

import com.bingdou.core.constants.DBConstants;
import com.bingdou.tools.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gaoshan on 16-10-20.
 */
public class User {

    /**
     * 主键id（mid）
     */
    private int id;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * Email
     */
    private String email;
    /**
     * 余额(分)
     */
    private Integer money;
    /**
     * 用户token
     */
    private String token;
    /**
     * CP用的登录验证token
     */
    private String vToken;
    /**
     * 用户状态
     */
    private int status;
    /**
     * 登录错误时的时间戳
     */
    private int loginErrorTime;
    /**
     * 登录错误次数
     */
    private int loginError;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐，用于校验密码
     */
    private String salt;
    /**
     * 安全等级
     */
    private int safeLevel;
    /**
     * 版本标记,0:老版本,无cpid字段为空,1:新版本,cpid字段不为空
     */
    private int versionFlag;
    /**
     * CP ID
     */
    private String cpId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 游客登录设备信息
     */
    private String device;
    /**
     * 微信公众号注册用
     */
    private String wxOpenId;
    /**
     * 查询用户列表请求用
     */
    private Integer vipLevel;
    /**
     * VIP黑名单状态
     */
    private Integer vipBlackStatus;

    public boolean isLoginLocked() {
        return this.isLoginErrorInOneHour() && this.isTouchLoginErrorTimes();
    }

    public boolean isLoginErrorInOneHour() {
        return System.currentTimeMillis() / 1000 - this.loginErrorTime <= 3600;
    }

    public boolean isTouchLoginErrorTimes() {
        return this.loginError >= 5;
    }

    public boolean isPasswordValid(String password) {
        String encodedPassword = getEncodedPassword(password, this.salt);
        return this.password.equals(encodedPassword);
    }

    /**
     * 获取加密变换后的密码
     */
    public static String getEncodedPassword(String password, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(password) + salt);
    }

    /**
     * 生成密码盐
     */
    public static String generateSalt() {
        return CodecUtils.generateRandCode();
    }

    public static boolean isValidLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName))
            return false;
        if (ValidateUtil.isInteger(loginName))
            return false;
        Pattern p = Pattern.compile("[A-Za-z0-9]{4,20}");
        Matcher m = p.matcher(loginName);
        return m.matches();
    }

    public static boolean isValidPassword(String password) {
        if (StringUtils.isEmpty(password))
            return false;
        Pattern p = Pattern.compile("[A-Za-z0-9]{6,20}");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * mybatis动态调用方法
     *
     * @param idList 用户主键ID集合
     * @return sql
     * @throws Exception
     */
    public static String buildGetUserInfoListByIdListSql(List<Integer> idList) throws Exception {
        if (idList == null || idList.isEmpty())
            return "SELECT ''";
        StringBuilder sql = new StringBuilder();
        int i = 0;
        for (Integer id : idList) {
            if (id == null)
                continue;
            sql.append("SELECT a.id,a.version_flag,a.cpid,a.nickname,a.vip_level_id,b.status AS vip_black_status FROM (")
                    .append("SELECT m.id,m.version_flag,m.cpid,m.nickname,uvip.level_id AS vip_level_id FROM ")
                    .append(DBConstants.USER_DB_NAME).append(".mc_member")
                    .append(getTableNumber(id)).append(" m LEFT JOIN ")
                    .append(DBConstants.PAY_DB_NAME).append(".mc_user_vip uvip ON m.id=uvip.mid WHERE m.id='")
                    .append(ValidateUtil.filterSqlValue(id)).append("' AND m.status=1) a LEFT JOIN ")
                    .append(DBConstants.PAY_DB_NAME)
                    .append(".mc_vip_blacklist b ON a.id = b.mid");
            if (i < idList.size() - 1) {
                sql.append(" UNION ");
            }
            i++;
        }
        return sql.toString();
    }

    /**
     * mybatis动态调用方法
     *
     * @param userIdList 用户ID集合(cpid和主键ID)
     * @return sql
     */
    public static String buildGetIdsByUserIdListSql(List<String> userIdList) {
        if (userIdList == null || userIdList.isEmpty())
            return "SELECT ''";
        StringBuilder sql = new StringBuilder();
        int i = 0;
        for (String id : userIdList) {
            if (StringUtils.isEmpty(id)) {
                continue;
            }
            if (id.startsWith("cpd")) {
                sql.append("SELECT id FROM ").append(DBConstants.USER_DB_NAME)
                        .append(".mc_member_index WHERE cpid='")
                        .append(ValidateUtil.filterSqlValue(id)).append("'");
            } else {
                sql.append("SELECT id FROM ").append(DBConstants.USER_DB_NAME)
                        .append(".mc_member_index WHERE id='")
                        .append(ValidateUtil.filterSqlValue(id)).append("'");
            }
            if (i < userIdList.size() - 1) {
                sql.append(" UNION ");
            }
            i++;
        }
        return sql.toString();
    }

    /**
     * mybatis动态调用方法
     *
     * @param userId 用户主键ID
     * @return 在mc_member的第几张表
     * @throws Exception
     */
    public static int getTableNumber(int userId) throws Exception {
        return IndexUtil.getTableNumber(userId);
    }

    public UserStatus getUserStatus() {
        return UserStatus.valueOf(status);
    }

    /**
     * 检查用户状态
     *
     * @return 可用状态返回null，否则返回错误提示语
     */
    public String checkStatus() {
        UserStatus us = getUserStatus();
        if (UserStatus.VALID.equals(us)) {
            return null;
        }
        switch (us) {
            case INVALID:
                return "此帐号未被启用";
            case PAUSE:
                return "此帐号已暂停";
            case DENY:
                return "此帐号已被禁用";
            default:
                return "帐号不存在";
        }
    }

    /**
     * 下发给客户端的用户ID,老版本返回id,新版本返回cpid
     */
    public String getReturnUserId() {
        if (this.versionFlag == 1) {
            return this.cpId;
        } else {
            return String.valueOf(this.id);
        }
    }

    /**
     * 下发给客户端的用户名,老版本返回用户名,新版本返回cpid
     */
    public String getOldLoginName() {
        if (this.versionFlag == 1) {
            return this.cpId;
        } else {
            return this.loginName;
        }
    }

    public String generateUserToken() {
        String seed = this.id + "" + CodecUtils.getRequestUUID();
        return DigestUtils.md5Hex(seed);
    }

    public String generateUserValidateToken() {
        String seed = this.id + "" + CodecUtils.getRequestUUID();
        return DigestUtils.sha1Hex(seed);
    }

    public String generateGuestLoginName() {
        return "YK" + DateUtil.getCurrentTimeSeconds() + NumberUtil.getRandomNum(1000, 9999);
    }

    public String generateThirdFastLoginName() {
        return "TH" + DateUtil.getCurrentTimeSeconds() + NumberUtil.getRandomNum(1000, 9999);
    }

    public String generateCpId() {
        return "cpd" + (this.id * 2 - 2);
    }

    public int getLoginErrorTime() {
        return loginErrorTime;
    }

    public void setLoginErrorTime(int loginErrorTime) {
        this.loginErrorTime = loginErrorTime;
    }

    public int getLoginError() {
        return loginError;
    }

    public void setLoginError(int loginError) {
        this.loginError = loginError;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
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
        this.vToken = vToken;
    }

    public int getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(int safeLevel) {
        this.safeLevel = safeLevel;
    }

    public int getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(int versionFlag) {
        this.versionFlag = versionFlag;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public Integer getVipLevel() {
        if (vipLevel == null)
            return 0;
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        if (vipLevel == null)
            return;
        this.vipLevel = vipLevel;
    }

    public Integer getVipBlackStatus() {
        return vipBlackStatus;
    }

    public void setVipBlackStatus(Integer vipBlackStatus) {
        this.vipBlackStatus = vipBlackStatus;
    }
}
