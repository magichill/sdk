package com.bingdou.core.service.user;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.ThirdFastLogin;
import com.bingdou.core.model.ThirdFastLoginType;
import com.bingdou.core.repository.user.ThirdFastLoginDao;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Date;

/**
 * 第三方登录
 */
@Service
public class ThirdFastLoginAuthBaseService {

    @Autowired
    private ThirdFastLoginDao thirdFastLoginDao;

    public int getBingDouUserIdByOpenId(String openId, int type) {
        if (StringUtils.isEmpty(openId))
            return 0;
        Integer userId = thirdFastLoginDao.getBingDouUserIdByOpenInfo(openId, type);
        if (userId == null)
            return 0;
        return userId;
    }

    public void insertLoginInfo(ThirdFastLogin thirdFastLogin) {
        thirdFastLoginDao.insertLoginInfo(thirdFastLogin);
    }

    public boolean updateAuthToken(String openId, ThirdFastLoginType thirdFastLoginType,
                                   String authToken) {
        int count = thirdFastLoginDao.updateAuthToken(openId, thirdFastLoginType.getIndex(), authToken);
        return count > 0;
    }

    public boolean clearLoginAuth(String deviceNo) {
        int count = thirdFastLoginDao.updateLoginAuth(deviceNo, "");
        return count > 0;
    }

    public String getAuthInfo(ThirdFastLoginType type, String deviceNo) {
        String result = "";
        try {
            String targetId = getTargetId(deviceNo);
            if (ThirdFastLoginType.ALI.equals(type)) {
                result = getAliAuthInfo(deviceNo, targetId);
            } else if (ThirdFastLoginType.WEIXIN.equals(type)){
                result = getWeixinAuthInfo(deviceNo,targetId);
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "拼接授权信息失败");
        }
        return result;
    }

    public boolean existTargetId(String deviceNo) {
        String targetId = thirdFastLoginDao.getTargetId(deviceNo);
        return StringUtils.isNotEmpty(targetId);
    }

    private String getTargetId(String deviceNo) throws Exception {
        String targetId = DigestUtils.md5Hex(deviceNo + ":" + CodecUtils.getRequestUUID());
        int loginAuthCount = thirdFastLoginDao.getLoginAuthCount(deviceNo);
        if (loginAuthCount < 1) {
            thirdFastLoginDao.insertLoginAuth(deviceNo, targetId);
        } else {
            int count = thirdFastLoginDao.updateLoginAuth(deviceNo, targetId);
            if (count < 1)
                throw new Exception("更新登录授权信息失败");
        }
        return targetId;
    }

    private String getWeixinAuthInfo(String deviceNo, String targetId) throws Exception {
        if (StringUtils.isEmpty(deviceNo)
                || StringUtils.isEmpty(targetId)) {
            return "";
        }
        return "";
    }

    private String getAliAuthInfo(String deviceNo, String targetId) throws Exception {
        if (StringUtils.isEmpty(deviceNo)
                || StringUtils.isEmpty(targetId)) {
            return "";
        }
        String authInfo = "apiname=\"com.alipay.account.auth\"";
        authInfo += "&app_id=" + "\"" + UserConstants.ALI_FAST_LOGIN_AUTH_APP_ID + "\"";
        authInfo += "&app_name=\"mc\"";
        authInfo += "&auth_type=\"AUTHACCOUNT\"";
        authInfo += "&biz_type=\"openservice\"";
        authInfo += "&pid=" + "\"" + Constants.ALI_PID + "\"";
        authInfo += "&product_id=\"WAP_FAST_LOGIN\"";
        authInfo += "&scope=\"kuaijie\"";
        authInfo += "&target_id=" + "\"" + targetId + "\"";
        String signDate = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        authInfo += "&sign_date=" + "\"" + signDate + "\"";
        String sign = CodecUtils.rsaSign(authInfo, UserConstants.ALI_PKCS8_RSA_PRIVATE_KEY);
        if (StringUtils.isEmpty(sign))
            return "";
        sign = URLEncoder.encode(sign, "UTF-8");
        return authInfo + "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
    }

}
