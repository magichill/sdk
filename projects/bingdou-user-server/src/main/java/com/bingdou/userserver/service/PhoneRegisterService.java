package com.bingdou.userserver.service;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.SendCodeType;
import com.bingdou.core.model.User;
import com.bingdou.core.model.ValidateCode;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.ValidateCodeService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.*;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.PhoneRegisterRequest;
import com.bingdou.userserver.response.RegisterResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 手机注册服务类
 */
@Service
public class PhoneRegisterService extends BaseService implements IMethodService {

    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        PhoneRegisterRequest phoneRegisterRequest = new PhoneRegisterRequest();
        phoneRegisterRequest.parseRequest(request);
        return phoneRegisterRequest;
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
        return "phone_register";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        PhoneRegisterRequest phoneRegisterRequest = (PhoneRegisterRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(phoneRegisterRequest.getAppId());
        return dealRegister(phoneRegisterRequest, request, "", "");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        PhoneRegisterRequest phoneRegisterRequest = (PhoneRegisterRequest) baseRequest;
//        Application application = getValidApplication4Client(phoneRegisterRequest);
        String ua = UserUtils.getOldUa4Client(baseRequest);
        String uid = UserUtils.getOldUid4Client(baseRequest);
        return dealRegister(phoneRegisterRequest, request, uid, ua);
    }

    @SuppressWarnings("Duplicates")
//    private ServiceResult dealRegister(PhoneRegisterRequest phoneRegisterRequest, HttpServletRequest request,
//                                       String uid, String ua, Application application) throws Exception {
    private ServiceResult dealRegister(PhoneRegisterRequest phoneRegisterRequest, HttpServletRequest request,
                                       String uid, String ua) throws Exception {
        if (!ValidateUtil.isMobileNumber(phoneRegisterRequest.getMobile())) {
            return ServiceResultUtil.illegal("手机号不合法");
        }
        if (!User.isValidPassword(phoneRegisterRequest.getPassword())) {
            return ServiceResultUtil.illegal("密码不合法");
        }
        if (StringUtils.isEmpty(phoneRegisterRequest.getCode())) {
            return ServiceResultUtil.illegal("验证码不能为空");
        }
        User user = userBaseService.getDetailByMobile(phoneRegisterRequest.getMobile());
        if (user != null) {
            return ServiceResultUtil.illegal("该手机号已经注册");
        }
        ValidateCode validateCode = validateCodeService.getValidateCode4Mobile(phoneRegisterRequest.getMobile(),
                SendCodeType.PHONE_REGISTER);
        if (validateCode == null) {
            return ServiceResultUtil.illegal("验证码和手机号不匹配");
        }
        if (!UserUtils.isValidationCodeValid(phoneRegisterRequest.getCode(),
                validateCode.getVcode(), validateCode.getVcodeTime())) {
            return ServiceResultUtil.illegal("验证码有误或已失效");
        }
        String loginName = "PR" + phoneRegisterRequest.getMobile() + NumberUtil.getRandomNum(1000, 9999);
        boolean exist = userBaseService.isExistsLoginName(loginName);
        if (exist) {
            return ServiceResultUtil.illegal("该手机号已经注册");
        }
        String clientIp = RequestUtil.getClientIp(request);
        String salt = User.generateSalt();
        String encodePwd = User.getEncodedPassword(phoneRegisterRequest.getPassword(), salt);
        User createUser = new User();
        createUser.setLoginName(loginName);
        createUser.setPassword(encodePwd);
        createUser.setSalt(salt);
        createUser.setDevice("");
        createUser.setMobile(phoneRegisterRequest.getMobile());
        String appId = isClientRequest(request) ? phoneRegisterRequest.getOtherInfo().getAppId()
                : phoneRegisterRequest.getAppId();
        boolean createSuccess = userBaseService.createUser(createUser, appId, clientIp, uid, ua,
                UserConstants.SECURE_LEVEL_3);
        boolean isNewDevice = false;
        boolean isSupportVirtualMoney = false;
        boolean isSupportLive = false;
        if (createSuccess) {
            LogContext.instance().info("插入用户成功");
            LogContext.instance().info("清除验证码");
            validateCodeService.clearValidateCode4Mobile(phoneRegisterRequest.getMobile(),
                    SendCodeType.PHONE_REGISTER);
            String tokenDevice = "";
            User newUser = userBaseService.getDetailByMobile(phoneRegisterRequest.getMobile());
//            userStatisticsService.recordUserActiveRecord(newUser.getId(), application);
            RegisterResponse registerResponse = new RegisterResponse();
            String sdkVersion = "";

            boolean updateTokenResult = userBaseService.updateToken(newUser, tokenDevice, getSafeInfo(request), true);
            LogContext.instance().info("更新用户token:" + updateTokenResult);
            DataLogUtils.recordHadoopLog(HadoopLogAction.PHONE_REGISTER, phoneRegisterRequest, newUser,
                    clientIp, "", "", isNewDevice);
//            int virtualMoneyFen = getVirtualMoneyFen4Show(newUser.getId(), application.getOs(),
//                    sdkVersion, true, isSupportVirtualMoney);
            registerResponse.parseFromUser(newUser, null, isSupportVirtualMoney, isSupportLive,
                    false, 0);
            JsonElement result = JsonUtil.bean2JsonTree(registerResponse);
            LogContext.instance().info("手机号注册成功");
            return ServiceResultUtil.success(result);
        } else {
            return ServiceResultUtil.illegal("手机号注册失败");
        }
    }

}
