package com.bingdou.userserver.service;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.VipGradeService;
import com.bingdou.core.service.user.DeviceService;
import com.bingdou.core.service.user.LoginBaseService;
import com.bingdou.core.service.user.ValidateCodeService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.*;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.PhoneLoginRequest;
import com.bingdou.userserver.response.LoginResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/1/24.
 */
@Service
public class PhoneLoginService extends BaseService implements IMethodService {

    @Autowired
    private LoginBaseService loginBaseService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private VipGradeService vipGradeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        PhoneLoginRequest phoneLoginRequest = new PhoneLoginRequest();
        phoneLoginRequest.parseRequest(request);
        return phoneLoginRequest;
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
        return "phone_login";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        PhoneLoginRequest phoneLoginRequest = (PhoneLoginRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(loginRequest.getAppId());
        return dealPhoneLogin(phoneLoginRequest, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        PhoneLoginRequest phoneLoginRequest = (PhoneLoginRequest) baseRequest;
        if (phoneLoginRequest.getOtherInfo() == null) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
//        Application application = getValidApplication4Client(loginRequest);
        return dealPhoneLogin(phoneLoginRequest, request);
    }

    private ServiceResult dealPhoneLogin(PhoneLoginRequest phoneLoginRequest, HttpServletRequest request) throws Exception {

        if (!ValidateUtil.isMobileNumber(phoneLoginRequest.getMobile())) {
            return ServiceResultUtil.illegal("手机号不合法");
        }

        User user = null;
        //TODO 增加测试手机号逻辑
        if(phoneLoginRequest.getMobile().equals("13161862580")){
            user = userBaseService.getDetailByMobile(phoneLoginRequest.getMobile());
        }else {
            if (StringUtils.isEmpty(phoneLoginRequest.getCode())) {
                return ServiceResultUtil.illegal("验证码不能为空");
            }
            user = userBaseService.getDetailByMobile(phoneLoginRequest.getMobile());

            ValidateCode validateCode = validateCodeService.getValidateCode4Mobile(phoneLoginRequest.getMobile(),
                    SendCodeType.PHONE_LOGIN);
            if (validateCode == null) {
                return ServiceResultUtil.illegal("验证码和手机号不匹配");
            }
            if (!UserUtils.isValidationCodeValid(phoneLoginRequest.getCode(),
                    validateCode.getVcode(), validateCode.getVcodeTime())) {
                return ServiceResultUtil.illegal("验证码有误或已失效");
            }
        }

        String clientIp = RequestUtil.getClientIp(request);
        boolean isClientRequest = isClientRequest(request);
        LoginResponse loginResponse = new LoginResponse();

        boolean createSuccess = true;
        if (user == null) {
            LogContext.instance().info("用户不存在，自动注册为新用户");
            String loginName = "PR" + phoneLoginRequest.getMobile() + NumberUtil.getRandomNum(1000, 9999);
            User createUser = new User();
            createUser.setLoginName(loginName);
            createUser.setPassword("");
            createUser.setSalt("");
            createUser.setDevice("");
            createUser.setMobile(phoneLoginRequest.getMobile());
            String appId = isClientRequest ? phoneLoginRequest.getOtherInfo().getAppId()
                    : phoneLoginRequest.getAppId();
            createSuccess = userBaseService.createUser(createUser, appId, clientIp, "", "",
                    UserConstants.SECURE_LEVEL_3);
            user = userBaseService.getDetailByMobile(phoneLoginRequest.getMobile());
        }

        if(createSuccess) {
            boolean isNewDevice = false;
            boolean isSupportVirtualMoney = false;
            boolean isSigned = false;
            String tokenDevice = "";
            String oldUid = "";
            String oldUa = "";
            String sdkVersion = "";
            if (isClientRequest) {
                LogContext.instance().info("客户端专有逻辑");
                sdkVersion = phoneLoginRequest.getOtherInfo().getSdkVersion();
                Os os = getClientOsByRequest(phoneLoginRequest);
                tokenDevice = getDeviceNo4Client(phoneLoginRequest);
                isNewDevice = deviceService.isNewDevice(phoneLoginRequest.getDeviceInfo());
                LogContext.instance().info("是否是新设备:" + isNewDevice);
            }
            UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
            loginBaseService.setLastLoginInfo(user.getId(), clientIp, oldUid, oldUa);
            boolean updateTokenResult = userBaseService.updateToken(user, tokenDevice, getSafeInfo(request), false);
            LogContext.instance().info("更新token结果:" + updateTokenResult);
            DataLogUtils.recordHadoopLog(HadoopLogAction.LOGIN,
                    phoneLoginRequest, user, clientIp, "", "", isNewDevice);
            loginResponse.parseFromUser(user, userVipGrade, isSupportVirtualMoney, isSigned, 0);
            JsonElement result = JsonUtil.bean2JsonTree(loginResponse);
            LogContext.instance().info("登录成功");
            return ServiceResultUtil.success(result);
        }else{
            return ServiceResultUtil.illegal("创建登录用户失败");
        }
    }


}
