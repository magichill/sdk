package com.bingdou.userserver.service;

import com.bingdou.core.cache.ISendSmsOrEmailCacheManager;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.SendCodeType;
import com.bingdou.core.model.User;
import com.bingdou.core.model.ValidateCode;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.SmsSendService;
import com.bingdou.core.service.user.ValidateCodeService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.tools.*;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.SendCodeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * ������֤�������
 */
@Service
public class SendCodeService extends BaseService implements IMethodService {

    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private ISendSmsOrEmailCacheManager sendSmsOrEmailCacheManager;
    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        SendCodeRequest sendCodeRequest = new SendCodeRequest();
        sendCodeRequest.parseRequest(request);
        return sendCodeRequest;
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
        return "send_code";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        SendCodeRequest sendCodeRequest = (SendCodeRequest) baseRequest;
        if (StringUtils.isEmpty(sendCodeRequest.getPhoneOrEmail())) {
            return ServiceResultUtil.illegal("�����������");
        }
        return dealSendCode(request, sendCodeRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        SendCodeRequest sendCodeRequest = (SendCodeRequest) baseRequest;
        if (StringUtils.isEmpty(sendCodeRequest.getPhoneOrEmail())) {
            return ServiceResultUtil.illegal("�����������");
        }
        return dealSendCode(request, sendCodeRequest);
    }

    private ServiceResult dealSendCode(HttpServletRequest request,
                                       SendCodeRequest sendCodeRequest) throws Exception {
        if (isClientRequest(request)) {
            String device = getDevice4Client(request, sendCodeRequest);
            int sendCount = sendSmsOrEmailCacheManager.getSendCount(device);
            LogContext.instance().info("�豸���ʹ���:" + sendCount);
            if (sendCount >= UserConstants.SEND_SMS_OR_EMAIL_COUNT_EVERY_DAY) {
                return ServiceResultUtil.illegal("���ķ��Ͳ�������Ƶ��,��������ʹ��");
            }
        }
        String validationCode = CodecUtils.generateValidateCode();
        LogContext.instance().info("���ɵ���֤�� : " + validationCode);
        if (ValidateUtil.isMobileNumber(sendCodeRequest.getPhoneOrEmail())) {
            LogContext.instance().info("�·����ֻ�");
            if (sendCodeRequest.getType() == SendCodeType.BIND.getIndex()) {
                LogContext.instance().info("������");
                return dealBindMobile(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.LOST_PASSWORD.getIndex()) {
                LogContext.instance().info("������������");
                return dealLostPwdMobile(request, sendCodeRequest, validationCode, SendCodeType.LOST_PASSWORD);
            } else if (sendCodeRequest.getType() == SendCodeType.PHONE_REGISTER.getIndex()) {
                LogContext.instance().info("�ֻ���ע������");
                return dealPhoneRegister(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.MODIFY_PASSWORD.getIndex()) {
                LogContext.instance().info("�޸���������");
                return dealModifyPwdMobile(request, sendCodeRequest, validationCode);
            } else {
                return ServiceResultUtil.illegal("������������");
            }
        } else if (ValidateUtil.isEmail(sendCodeRequest.getPhoneOrEmail())) {
            LogContext.instance().info("�·�������");
            if (sendCodeRequest.getType() == SendCodeType.BIND.getIndex()) {
                LogContext.instance().info("������");
                return dealBindEmail(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.LOST_PASSWORD.getIndex()) {
                LogContext.instance().info("������������");
                return dealLostPwdEmail(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.MODIFY_PASSWORD.getIndex()) {
                LogContext.instance().info("�޸���������");
                return dealModifyPwdEmail(request, sendCodeRequest, validationCode);
            } else {
                return ServiceResultUtil.illegal("������������");
            }
        } else {
            return ServiceResultUtil.illegal("�ֻ��Ż������ʽ����");
        }
    }

    private ServiceResult dealBindMobile(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                         String validationCode) throws Exception {
        User user = userBaseService.getDetailByMobile(sendCodeRequest.getPhoneOrEmail());
        if (user != null) {
            return ServiceResultUtil.illegal("���ֻ����Ѱ�");
        }
        user = userBaseService.getDetailByIdOrCpIdOrLoginName(sendCodeRequest.getAccount());
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (StringUtils.isNotEmpty(user.getMobile())) {
            return ServiceResultUtil.illegal("���˺��Ѿ������ֻ���");
        }
        if (!validateRequestToken(request, user.getId(), sendCodeRequest)) {
            return ServiceResultUtil.tokenExpired();
        }
        return sendSMS4BindAndLostPwdAndModifyPwd(sendCodeRequest, validationCode, user, SendCodeType.BIND,
                request);
    }

    private ServiceResult dealBindEmail(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                        String validationCode) throws Exception {
        User user = userBaseService.getDetailByEmail(sendCodeRequest.getPhoneOrEmail());
        if (user != null) {
            return ServiceResultUtil.illegal("�������Ѱ�");
        }
        user = userBaseService.getDetailByIdOrCpIdOrLoginName(sendCodeRequest.getAccount());
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            return ServiceResultUtil.illegal("�������Ѿ��󶨣������ٰ�");
        }
        boolean existsEmailLoginName = userBaseService.isExistsLoginName(sendCodeRequest.getPhoneOrEmail());
        if (existsEmailLoginName && user.getLoginName().contains("@")) {
            LogContext.instance().info("�û�������������û��ж��߼�");
            if (!user.getLoginName().equals(sendCodeRequest.getPhoneOrEmail())) {
                return ServiceResultUtil.illegal("�������Ѿ��󶨣������ٰ�");
            }
        }
        if (!validateRequestToken(request, user.getId(), sendCodeRequest)) {
            return ServiceResultUtil.tokenExpired();
        }
        return sendEmail(request, sendCodeRequest, validationCode, user, SendCodeType.BIND);
    }

    private ServiceResult dealPhoneRegister(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                            String validationCode) throws Exception {
        User user = userBaseService.getDetailByMobile(sendCodeRequest.getPhoneOrEmail());
        if (user != null) {
            return ServiceResultUtil.illegal("���ֻ����Ѿ�ע��");
        }
        user = userBaseService.getDetailByLoginName(sendCodeRequest.getPhoneOrEmail());
        if (user != null) {
            return ServiceResultUtil.illegal("���ֻ����Ѿ�ע��");
        }
        ValidateCode validateCode = validateCodeService.getValidateCode4Mobile(sendCodeRequest.getPhoneOrEmail(),
                SendCodeType.PHONE_REGISTER);
        if (validateCode == null) {
            validateCodeService.insertValidateCode4Mobile(sendCodeRequest.getPhoneOrEmail(), validationCode,
                    SendCodeType.PHONE_REGISTER);
        } else {
            String errorMessage = validateCodeTime(validateCode.getVcodeTime());
            if (StringUtils.isNotEmpty(errorMessage)) {
                return ServiceResultUtil.illegal(errorMessage);
            }
            validateCodeService.updateValidateCode4Mobile(sendCodeRequest.getPhoneOrEmail(), validationCode,
                    SendCodeType.PHONE_REGISTER);
        }
        return sendSMS4PhoneRegister(request, sendCodeRequest, validationCode);
    }

    private ServiceResult dealModifyPwdMobile(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                              String validationCode) throws Exception {
        User user = userBaseService.getDetailByIdOrCpIdOrLoginName(sendCodeRequest.getAccount());
        if (user == null) {
            return ServiceResultUtil.illegal("�û�������");
        }
        if (!sendCodeRequest.getPhoneOrEmail().equals(user.getMobile())) {
            return ServiceResultUtil.illegal("�˺ź��ֻ��Ų�ƥ��");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (!validateRequestToken(request, user.getId(), sendCodeRequest)) {
            return ServiceResultUtil.tokenExpired();
        }
        return sendSMS4BindAndLostPwdAndModifyPwd(sendCodeRequest, validationCode,
                user, SendCodeType.MODIFY_PASSWORD, request);
    }

    private ServiceResult dealModifyPwdEmail(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                             String validationCode) throws Exception {
        User user = userBaseService.getDetailByIdOrCpIdOrLoginName(sendCodeRequest.getAccount());
        if (user == null) {
            return ServiceResultUtil.illegal("�û�������");
        }
        if (!sendCodeRequest.getPhoneOrEmail().equals(user.getEmail())) {
            return ServiceResultUtil.illegal("�˺ź��ֻ��Ų�ƥ��");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (!validateRequestToken(request, user.getId(), sendCodeRequest)) {
            return ServiceResultUtil.tokenExpired();
        }
        return sendEmail(request, sendCodeRequest, validationCode, user, SendCodeType.MODIFY_PASSWORD);
    }

    private ServiceResult dealLostPwdMobile(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                            String validationCode, SendCodeType sendCodeType) throws Exception {
        User user = userBaseService.getDetailByMobile(sendCodeRequest.getPhoneOrEmail());
        if (user == null) {
            return ServiceResultUtil.illegal("���ֻ���δ��");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return sendSMS4BindAndLostPwdAndModifyPwd(sendCodeRequest, validationCode, user, sendCodeType, request);
    }

    private ServiceResult dealLostPwdEmail(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                           String validationCode) throws Exception {
        User user = userBaseService.getDetailByEmail(sendCodeRequest.getPhoneOrEmail());
        if (user == null) {
            return ServiceResultUtil.illegal("������δ��");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return sendEmail(request, sendCodeRequest, validationCode, user, SendCodeType.LOST_PASSWORD);
    }

    private String validateCodeTime(long validateCodeTime) {
        if (DateUtil.getCurrentTimeSeconds() - validateCodeTime < UserConstants.VALIDATION_CODE_SEND_INTERVAL_SECONDS) {
            return "���η��ͼ������" + UserConstants.VALIDATION_CODE_SEND_INTERVAL_SECONDS + "��";
        }
        return "";
    }

    private ServiceResult sendEmail(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                    String validationCode, User user, SendCodeType sendCodeType) throws Exception {
        ValidateCode validateCode = validateCodeService.getValidateCode4Email(sendCodeRequest.getPhoneOrEmail(),
                sendCodeType);
        if (validateCode != null) {
            String errorMessage = validateCodeTime(validateCode.getVcodeTime());
            if (StringUtils.isNotEmpty(errorMessage)) {
                return ServiceResultUtil.illegal(errorMessage);
            }
        }
        String title = "����ֱ��";
        String content = validationCode + UserConstants.SEND_CODE_TEMPLATE_4_EMAIL;
        if (EmailUtil.sendEmail(sendCodeRequest.getPhoneOrEmail(), title, content)) {
            LogContext.instance().info("�����ʼ��ɹ�");
            validateCodeService.insertOrUpdateValidateCode4Email(sendCodeRequest.getPhoneOrEmail(), validationCode,
                    sendCodeType);
            LogContext.instance().info("�����û���֤��ͷ���ʱ��ɹ�");
            updateSendCount(getDevice4Client(request, sendCodeRequest));
            DataLogUtils.recordHadoopLog(HadoopLogAction.SEND_CODE, sendCodeRequest, user,
                    RequestUtil.getClientIp(sendCodeRequest.getRequest()), "", validationCode, false);
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("�ʼ�����ʧ��");
    }

    private ServiceResult sendSMS4BindAndLostPwdAndModifyPwd(SendCodeRequest sendCodeRequest, String validationCode,
                                                             User user, SendCodeType sendCodeType,
                                                             HttpServletRequest request) throws Exception {
        ValidateCode validateCode = validateCodeService.getValidateCode4Mobile(sendCodeRequest.getPhoneOrEmail(),
                sendCodeType);
        if (validateCode != null) {
            String errorMessage = validateCodeTime(validateCode.getVcodeTime());
            if (StringUtils.isNotEmpty(errorMessage)) {
                return ServiceResultUtil.illegal(errorMessage);
            }
        }
        String content = validationCode + UserConstants.SEND_CODE_TEMPLATE_4_SMS;
        String device = getDevice4Client(request, sendCodeRequest);
        String ip = RequestUtil.getClientIp(request);
        if (smsSendService.sendSMS(sendCodeRequest.getPhoneOrEmail(), content, sendCodeType, device, ip)) {
            LogContext.instance().info("���Ͷ��ųɹ�");
            validateCodeService.insertOrUpdateValidateCode4Mobile(sendCodeRequest.getPhoneOrEmail(),
                    validationCode, sendCodeType);
            LogContext.instance().info("�����û���֤��ͷ���ʱ��ɹ�");
            updateSendCount(device);
            DataLogUtils.recordHadoopLog(HadoopLogAction.SEND_CODE, sendCodeRequest, user,
                    RequestUtil.getClientIp(sendCodeRequest.getRequest()), "", validationCode, false);
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("���ŷ���ʧ��");
    }

    private ServiceResult sendSMS4PhoneRegister(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                                String validationCode) throws Exception {
        String content = validationCode + UserConstants.SEND_CODE_TEMPLATE_4_SMS;
        String device = getDevice4Client(request, sendCodeRequest);
        String ip = RequestUtil.getClientIp(request);
        if (smsSendService.sendSMS(sendCodeRequest.getPhoneOrEmail(), content, SendCodeType.PHONE_REGISTER,
                device, ip)) {
            LogContext.instance().info("���Ͷ��ųɹ�");
            updateSendCount(device);
            DataLogUtils.recordHadoopLog(HadoopLogAction.SEND_CODE, sendCodeRequest, null,
                    RequestUtil.getClientIp(sendCodeRequest.getRequest()), "", validationCode, false);
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("���ŷ���ʧ��");
    }

    private void updateSendCount(String device) {
        boolean result = sendSmsOrEmailCacheManager.updateSendCount(device);
        LogContext.instance().info("���ʹ�����1���:" + result);
    }

    private String getDevice4Client(HttpServletRequest request, SendCodeRequest sendCodeRequest) {
        try {
            if (!isClientRequest(request))
                return "";
            String device = "";
            Os os = getClientOsByRequest(sendCodeRequest);
            if (Os.IOS.equals(os)) {
                device = sendCodeRequest.getDeviceInfo().getIosInfo().getIdfa();
            } else if (Os.ANDROID.equals(os)) {
                device = sendCodeRequest.getDeviceInfo().getAndroidInfo().getAndroidId();
            }
            return device;
        } catch (Exception e) {
            LogContext.instance().error(e, "��ȡ�豸��ʶ����");
        }
        return "";
    }

}
