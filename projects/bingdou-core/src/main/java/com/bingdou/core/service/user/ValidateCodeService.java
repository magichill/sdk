package com.bingdou.core.service.user;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.SendCodeType;
import com.bingdou.core.model.ValidateCode;
import com.bingdou.core.repository.user.ValidateCodeDao;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证码服务类
 */
@Service
public class ValidateCodeService {

    @Autowired
    private ValidateCodeDao validateCodeDao;

    public void insertValidateCode4Mobile(String mobile, String vCode, SendCodeType sendCodeType) {
        insertValidateCode(mobile, vCode, sendCodeType, UserConstants.SEND_CODE_SMS_TYPE);
    }

    public ValidateCode getValidateCode(String mobileOrEmail, SendCodeType sendCodeType, int type) {
        if (StringUtils.isEmpty(mobileOrEmail) || sendCodeType == null)
            return null;
        return validateCodeDao.getValidateCode(mobileOrEmail, sendCodeType.getIndex(), type);
    }

    public ValidateCode getValidateCode4Mobile(String mobile, SendCodeType sendCodeType) {
        return getValidateCode(mobile, sendCodeType, UserConstants.SEND_CODE_SMS_TYPE);
    }

    public ValidateCode getValidateCode4Email(String email, SendCodeType sendCodeType) {
        return getValidateCode(email, sendCodeType, UserConstants.SEND_CODE_EMAIL_TYPE);
    }

    public boolean updateValidateCode4Mobile(String mobile, String vCode, SendCodeType sendCodeType) {
        return updateValidateCode(mobile, vCode, sendCodeType, UserConstants.SEND_CODE_SMS_TYPE);
    }

    public void insertOrUpdateValidateCode4Mobile(String mobile, String vCode, SendCodeType sendCodeType) {
        insertOrUpdateValidateCode(mobile, vCode, sendCodeType, UserConstants.SEND_CODE_SMS_TYPE);
    }

    public void insertOrUpdateValidateCode4Email(String email, String vCode, SendCodeType sendCodeType) {
        insertOrUpdateValidateCode(email, vCode, sendCodeType, UserConstants.SEND_CODE_EMAIL_TYPE);
    }

    public boolean clearValidateCode4Mobile(String mobile, SendCodeType sendCodeType) {
        return clearValidateCode(mobile, sendCodeType, UserConstants.SEND_CODE_SMS_TYPE);
    }

    public boolean clearValidateCode4Email(String email, SendCodeType sendCodeType) {
        return clearValidateCode(email, sendCodeType, UserConstants.SEND_CODE_EMAIL_TYPE);
    }

    public boolean clearValidateCode(String mobileOrEmail, SendCodeType sendCodeType, int type) {
        if (StringUtils.isEmpty(mobileOrEmail)
                || sendCodeType == null)
            return false;
        int count = validateCodeDao.clearValidateCode(mobileOrEmail, sendCodeType.getIndex(), type);
        boolean result = count > 0;
        if (result) {
            LogContext.instance().info("清除验证码成功");
        } else {
            LogContext.instance().error("清除验证码失败");
        }
        return result;
    }

    private boolean updateValidateCode(String mobileOrEmail, String vCode, SendCodeType sendCodeType, int type) {
        if (StringUtils.isEmpty(mobileOrEmail) || StringUtils.isEmpty(vCode)
                || sendCodeType == null)
            return false;
        ValidateCode validateCode = new ValidateCode();
        validateCode.setMobileOrEmail(mobileOrEmail);
        validateCode.setPurpose(sendCodeType.getIndex());
        validateCode.setType(type);
        validateCode.setVcode(vCode);
        int count = validateCodeDao.updateValidateCode(validateCode);
        return count > 0;
    }

    private void insertOrUpdateValidateCode(String mobileOrEmail, String vCode, SendCodeType sendCodeType,
                                            int type) {
        if (StringUtils.isEmpty(mobileOrEmail) || StringUtils.isEmpty(vCode)
                || sendCodeType == null)
            return;
        ValidateCode validateCode = new ValidateCode();
        validateCode.setMobileOrEmail(mobileOrEmail);
        validateCode.setPurpose(sendCodeType.getIndex());
        validateCode.setType(type);
        validateCode.setVcode(vCode);
        ValidateCode exist = validateCodeDao.getValidateCode(validateCode.getMobileOrEmail(),
                validateCode.getPurpose(), validateCode.getType());
        if (exist == null) {
            validateCodeDao.insertValidateCode(validateCode);
        } else {
            int count = validateCodeDao.updateValidateCode(validateCode);
            if (count <= 0) {
                LogContext.instance().error("更新验证码信息失败");
            }
        }
    }

    private void insertValidateCode(String mobileOrEmail, String vCode, SendCodeType sendCodeType, int type) {
        if (StringUtils.isEmpty(mobileOrEmail) || StringUtils.isEmpty(vCode)
                || sendCodeType == null)
            return;
        ValidateCode validateCode = new ValidateCode();
        validateCode.setMobileOrEmail(mobileOrEmail);
        validateCode.setPurpose(sendCodeType.getIndex());
        validateCode.setType(type);
        validateCode.setVcode(vCode);
        validateCodeDao.insertValidateCode(validateCode);
    }

}
