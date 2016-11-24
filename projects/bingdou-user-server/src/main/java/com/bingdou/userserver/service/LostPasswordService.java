package com.bingdou.userserver.service;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.SendCodeType;
import com.bingdou.core.model.User;
import com.bingdou.core.model.ValidateCode;
import com.bingdou.core.repository.user.LostPasswordDao;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.ValidateCodeService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.ValidateUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.LostPasswordRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * �һ����������
 */
@Service
public class LostPasswordService extends BaseService implements IMethodService {

    @Autowired
    private LostPasswordDao lostPasswordDao;
    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        LostPasswordRequest lostPasswordRequest = new LostPasswordRequest();
        lostPasswordRequest.parseRequest(request);
        return lostPasswordRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "lost_password";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        LostPasswordRequest lostPasswordRequest = (LostPasswordRequest) baseRequest;
        return dealLostPassword(lostPasswordRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        LostPasswordRequest lostPasswordRequest = (LostPasswordRequest) baseRequest;
        if (lostPasswordRequest.getOtherInfo() == null) {
            return ServiceResultUtil.illegal("�����������");
        }
        return dealLostPassword(lostPasswordRequest);
    }

    private ServiceResult dealLostPassword(LostPasswordRequest lostPasswordRequest) throws Exception {
        if (StringUtils.isEmpty(lostPasswordRequest.getPhoneOrEmail())
                || StringUtils.isEmpty(lostPasswordRequest.getValidationCode())
                || StringUtils.isEmpty(lostPasswordRequest.getPassword())) {
            return ServiceResultUtil.illegal("�����������");
        }
        String account = lostPasswordRequest.getPhoneOrEmail();
        User user;
        int validateCodeType;
        if (ValidateUtil.isMobileNumber(account)) {
            LogContext.instance().info("ͨ���ֻ����һ�:" + account);
            user = userBaseService.getDetailByMobile(account);
            validateCodeType = UserConstants.SEND_CODE_SMS_TYPE;
        } else if (ValidateUtil.isEmail(account)) {
            LogContext.instance().info("ͨ�������һ�:" + account);
            user = userBaseService.getDetailByEmail(account);
            validateCodeType = UserConstants.SEND_CODE_EMAIL_TYPE;
        } else {
            return ServiceResultUtil.illegal("�ֻ��Ż������ʽ����");
        }
        if (user == null) {
            return ServiceResultUtil.illegal("�û�δ���ֻ��Ż�������");
        }
        ValidateCode validateCode = validateCodeService.getValidateCode(account,
                SendCodeType.LOST_PASSWORD, validateCodeType);
        if (validateCode == null) {
            return ServiceResultUtil.illegal("��֤�����ֻ��Ż����䲻ƥ��");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (!UserUtils.isValidationCodeValid(lostPasswordRequest.getValidationCode(),
                validateCode.getVcode(), validateCode.getVcodeTime())) {
            return ServiceResultUtil.illegal("��֤���������ʧЧ");
        }
        LogContext.instance().info("�û���Ϣ�Ϸ���׼����������");
        String salt = User.generateSalt();
        String encodedPassword = User.getEncodedPassword(lostPasswordRequest.getPassword(), salt);
        int updatedRows = lostPasswordDao.updatePassword(user.getId(), encodedPassword, salt);
        if (updatedRows < 1) {
            throw new Exception("�޸�������Ϣʧ��");
        }
        LogContext.instance().info("�����û���Ϣ�ɹ�");
        userBaseService.deleteToken(user.getId());
        LogContext.instance().info("��¼��������");
        userBaseService.clearLoginError(user.getId());
        LogContext.instance().info("�����֤��");
        validateCodeService.clearValidateCode(account, SendCodeType.LOST_PASSWORD, validateCodeType);
        DataLogUtils.recordHadoopLog(HadoopLogAction.FIND_PWD, lostPasswordRequest, user,
                RequestUtil.getClientIp(lostPasswordRequest.getRequest()), "", lostPasswordRequest.getValidationCode(), false);
        LogContext.instance().info("�������óɹ�");
        return ServiceResultUtil.success();
    }

}
