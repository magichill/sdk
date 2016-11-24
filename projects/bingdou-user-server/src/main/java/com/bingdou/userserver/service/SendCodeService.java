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
 * 发送验证码服务类
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
            return ServiceResultUtil.illegal("请求参数错误");
        }
        return dealSendCode(request, sendCodeRequest);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        SendCodeRequest sendCodeRequest = (SendCodeRequest) baseRequest;
        if (StringUtils.isEmpty(sendCodeRequest.getPhoneOrEmail())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        return dealSendCode(request, sendCodeRequest);
    }

    private ServiceResult dealSendCode(HttpServletRequest request,
                                       SendCodeRequest sendCodeRequest) throws Exception {
        if (isClientRequest(request)) {
            String device = getDevice4Client(request, sendCodeRequest);
            int sendCount = sendSmsOrEmailCacheManager.getSendCount(device);
            LogContext.instance().info("设备发送次数:" + sendCount);
            if (sendCount >= UserConstants.SEND_SMS_OR_EMAIL_COUNT_EVERY_DAY) {
                return ServiceResultUtil.illegal("您的发送操作过于频繁,请明天再使用");
            }
        }
        String validationCode = CodecUtils.generateValidateCode();
        LogContext.instance().info("生成的验证码 : " + validationCode);
        if (ValidateUtil.isMobileNumber(sendCodeRequest.getPhoneOrEmail())) {
            LogContext.instance().info("下发到手机");
            if (sendCodeRequest.getType() == SendCodeType.BIND.getIndex()) {
                LogContext.instance().info("绑定请求");
                return dealBindMobile(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.LOST_PASSWORD.getIndex()) {
                LogContext.instance().info("忘记密码请求");
                return dealLostPwdMobile(request, sendCodeRequest, validationCode, SendCodeType.LOST_PASSWORD);
            } else if (sendCodeRequest.getType() == SendCodeType.PHONE_REGISTER.getIndex()) {
                LogContext.instance().info("手机号注册请求");
                return dealPhoneRegister(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.MODIFY_PASSWORD.getIndex()) {
                LogContext.instance().info("修改密码请求");
                return dealModifyPwdMobile(request, sendCodeRequest, validationCode);
            } else {
                return ServiceResultUtil.illegal("请求类型有误");
            }
        } else if (ValidateUtil.isEmail(sendCodeRequest.getPhoneOrEmail())) {
            LogContext.instance().info("下发到邮箱");
            if (sendCodeRequest.getType() == SendCodeType.BIND.getIndex()) {
                LogContext.instance().info("绑定请求");
                return dealBindEmail(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.LOST_PASSWORD.getIndex()) {
                LogContext.instance().info("忘记密码请求");
                return dealLostPwdEmail(request, sendCodeRequest, validationCode);
            } else if (sendCodeRequest.getType() == SendCodeType.MODIFY_PASSWORD.getIndex()) {
                LogContext.instance().info("修改密码请求");
                return dealModifyPwdEmail(request, sendCodeRequest, validationCode);
            } else {
                return ServiceResultUtil.illegal("请求类型有误");
            }
        } else {
            return ServiceResultUtil.illegal("手机号或邮箱格式有误");
        }
    }

    private ServiceResult dealBindMobile(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                         String validationCode) throws Exception {
        User user = userBaseService.getDetailByMobile(sendCodeRequest.getPhoneOrEmail());
        if (user != null) {
            return ServiceResultUtil.illegal("该手机号已绑定");
        }
        user = userBaseService.getDetailByIdOrCpIdOrLoginName(sendCodeRequest.getAccount());
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (StringUtils.isNotEmpty(user.getMobile())) {
            return ServiceResultUtil.illegal("该账号已经绑定了手机号");
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
            return ServiceResultUtil.illegal("该邮箱已绑定");
        }
        user = userBaseService.getDetailByIdOrCpIdOrLoginName(sendCodeRequest.getAccount());
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            return ServiceResultUtil.illegal("该邮箱已经绑定，不能再绑定");
        }
        boolean existsEmailLoginName = userBaseService.isExistsLoginName(sendCodeRequest.getPhoneOrEmail());
        if (existsEmailLoginName && user.getLoginName().contains("@")) {
            LogContext.instance().info("用户名是邮箱的老用户判断逻辑");
            if (!user.getLoginName().equals(sendCodeRequest.getPhoneOrEmail())) {
                return ServiceResultUtil.illegal("该邮箱已经绑定，不能再绑定");
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
            return ServiceResultUtil.illegal("该手机号已经注册");
        }
        user = userBaseService.getDetailByLoginName(sendCodeRequest.getPhoneOrEmail());
        if (user != null) {
            return ServiceResultUtil.illegal("该手机号已经注册");
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
            return ServiceResultUtil.illegal("用户名错误");
        }
        if (!sendCodeRequest.getPhoneOrEmail().equals(user.getMobile())) {
            return ServiceResultUtil.illegal("账号和手机号不匹配");
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
            return ServiceResultUtil.illegal("用户名错误");
        }
        if (!sendCodeRequest.getPhoneOrEmail().equals(user.getEmail())) {
            return ServiceResultUtil.illegal("账号和手机号不匹配");
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
            return ServiceResultUtil.illegal("该手机号未绑定");
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
            return ServiceResultUtil.illegal("该邮箱未绑定");
        }
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return sendEmail(request, sendCodeRequest, validationCode, user, SendCodeType.LOST_PASSWORD);
    }

    private String validateCodeTime(long validateCodeTime) {
        if (DateUtil.getCurrentTimeSeconds() - validateCodeTime < UserConstants.VALIDATION_CODE_SEND_INTERVAL_SECONDS) {
            return "两次发送间隔不足" + UserConstants.VALIDATION_CODE_SEND_INTERVAL_SECONDS + "秒";
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
        String title = "冰豆直播";
        String content = validationCode + UserConstants.SEND_CODE_TEMPLATE_4_EMAIL;
        if (EmailUtil.sendEmail(sendCodeRequest.getPhoneOrEmail(), title, content)) {
            LogContext.instance().info("发送邮件成功");
            validateCodeService.insertOrUpdateValidateCode4Email(sendCodeRequest.getPhoneOrEmail(), validationCode,
                    sendCodeType);
            LogContext.instance().info("更新用户验证码和发送时间成功");
            updateSendCount(getDevice4Client(request, sendCodeRequest));
            DataLogUtils.recordHadoopLog(HadoopLogAction.SEND_CODE, sendCodeRequest, user,
                    RequestUtil.getClientIp(sendCodeRequest.getRequest()), "", validationCode, false);
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("邮件发送失败");
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
            LogContext.instance().info("发送短信成功");
            validateCodeService.insertOrUpdateValidateCode4Mobile(sendCodeRequest.getPhoneOrEmail(),
                    validationCode, sendCodeType);
            LogContext.instance().info("更新用户验证码和发送时间成功");
            updateSendCount(device);
            DataLogUtils.recordHadoopLog(HadoopLogAction.SEND_CODE, sendCodeRequest, user,
                    RequestUtil.getClientIp(sendCodeRequest.getRequest()), "", validationCode, false);
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("短信发送失败");
    }

    private ServiceResult sendSMS4PhoneRegister(HttpServletRequest request, SendCodeRequest sendCodeRequest,
                                                String validationCode) throws Exception {
        String content = validationCode + UserConstants.SEND_CODE_TEMPLATE_4_SMS;
        String device = getDevice4Client(request, sendCodeRequest);
        String ip = RequestUtil.getClientIp(request);
        if (smsSendService.sendSMS(sendCodeRequest.getPhoneOrEmail(), content, SendCodeType.PHONE_REGISTER,
                device, ip)) {
            LogContext.instance().info("发送短信成功");
            updateSendCount(device);
            DataLogUtils.recordHadoopLog(HadoopLogAction.SEND_CODE, sendCodeRequest, null,
                    RequestUtil.getClientIp(sendCodeRequest.getRequest()), "", validationCode, false);
            return ServiceResultUtil.success();
        }
        return ServiceResultUtil.illegal("短信发送失败");
    }

    private void updateSendCount(String device) {
        boolean result = sendSmsOrEmailCacheManager.updateSendCount(device);
        LogContext.instance().info("发送次数加1结果:" + result);
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
            LogContext.instance().error(e, "获取设备标识错误");
        }
        return "";
    }

}
