package com.bingdou.userserver.service;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.SendCodeType;
import com.bingdou.core.model.User;
import com.bingdou.core.model.ValidateCode;
import com.bingdou.core.repository.user.BindDao;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.ValidateCodeService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.ValidateUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.BindRequest;
import com.bingdou.userserver.response.BindResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 绑定服务类
 */
@Service
public class BindService extends BaseService implements IMethodService {

    @Autowired
    private BindDao bindDao;
    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        BindRequest bindRequest = new BindRequest();
        bindRequest.parseRequest(request);
        return bindRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        BindRequest bindRequest = (BindRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(bindRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "bind";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BindRequest bindRequest = (BindRequest) baseRequest;
        String errorMessage = checkRequest(bindRequest);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return dealBind(bindRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BindRequest bindRequest = (BindRequest) baseRequest;
        String errorMessage = checkRequest(bindRequest);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return dealBind(bindRequest, user);
    }

    private ServiceResult dealBind(BindRequest bindRequest, User user) throws Exception {
        String phoneOrMail = bindRequest.getPhoneOrMail();
        final int levelId = getLevelId(phoneOrMail, user.getMobile(), user.getEmail());
        if (levelId == -1) {
            return ServiceResultUtil.illegal("邮箱或手机号格式有误");
        }
        if (ValidateUtil.isMobileNumber(phoneOrMail)) {
            LogContext.instance().info("绑定手机");
            if (StringUtils.isNotEmpty(user.getMobile())) {
                return ServiceResultUtil.illegal("该账号已经绑定了手机号,不能再绑定");
            }
            ValidateCode validateCode = validateCodeService.getValidateCode4Mobile(phoneOrMail, SendCodeType.BIND);
            if (validateCode == null) {
                return ServiceResultUtil.illegal("验证码与手机号或邮箱不匹配");
            }
            if (!UserUtils.isValidationCodeValid(bindRequest.getValidationCode(), validateCode.getVcode(),
                    validateCode.getVcodeTime())) {
                return ServiceResultUtil.illegal("验证码有误或已失效");
            }
            User userByMobile = userBaseService.getDetailByMobile(phoneOrMail);
            if (userByMobile != null) {
                return ServiceResultUtil.illegal("该手机号已经绑定了其他账号,不能再绑定");
            }
            int updateCount = bindDao.updateMobile(user.getId(), phoneOrMail);
            if (updateCount < 2) {
                throw new Exception("更新绑定手机信息失败");
            }
            validateCodeService.clearValidateCode4Mobile(phoneOrMail, SendCodeType.BIND);
        }
        if (ValidateUtil.isEmail(phoneOrMail)) {
            LogContext.instance().info("绑定邮箱");
            if (StringUtils.isNotEmpty(user.getEmail())) {
                return ServiceResultUtil.illegal("该账号已经绑定了邮箱,不能再绑定");
            }
            ValidateCode validateCode = validateCodeService.getValidateCode4Email(phoneOrMail, SendCodeType.BIND);
            if (validateCode == null) {
                return ServiceResultUtil.illegal("验证码与手机号或邮箱不匹配");
            }
            if (!UserUtils.isValidationCodeValid(bindRequest.getValidationCode(), validateCode.getVcode(),
                    validateCode.getVcodeTime())) {
                return ServiceResultUtil.illegal("验证码有误或已失效");
            }
            User userByEmail = userBaseService.getDetailByEmail(phoneOrMail);
            if (userByEmail != null) {
                return ServiceResultUtil.illegal("该邮箱已经绑定了其他账号,不能再绑定");
            }
            int updateCount = bindDao.updateEmail(user.getId(), phoneOrMail);
            if (updateCount < 2) {
                throw new Exception("更新绑定邮箱信息失败");
            }
            validateCodeService.clearValidateCode4Email(phoneOrMail, SendCodeType.BIND);
        }
        int updateSafeLevelCount = bindDao.updateSafeLevel(user.getId(), levelId);
        if (updateSafeLevelCount < 1) {
            throw new Exception("更新安全等级信息失败");
        }
        LogContext.instance().info("绑定成功");
        DataLogUtils.recordHadoopLog(HadoopLogAction.BIND_MOBILE_OR_EMAIL, bindRequest, user,
                RequestUtil.getClientIp(bindRequest.getRequest()), phoneOrMail, bindRequest.getValidationCode(), false);
        return ServiceResultUtil.success(buildResponse(user.getId()));
    }

    private BindResponse buildResponse(int userId) {
        User user = userBaseService.getDetailById(userId);
        int safeLevel = UserUtils.getSafeLevel(user);
        BindResponse bindResponse = new BindResponse();
        bindResponse.setSafeLevel(safeLevel);
        return bindResponse;
    }

    private int getLevelId(String phoneOrMail, String phone, String email) {
        if (ValidateUtil.isMobileNumber(phoneOrMail)) {
            if (StringUtils.isNotEmpty(email)) {
                return UserConstants.SECURE_LEVEL_4;
            } else {
                return UserConstants.SECURE_LEVEL_3;
            }
        } else if (ValidateUtil.isEmail(phoneOrMail)) {
            if (StringUtils.isNotEmpty(phone)) {
                return UserConstants.SECURE_LEVEL_4;
            } else {
                return UserConstants.SECURE_LEVEL_2;
            }
        } else {
            return -1;
        }
    }

    private String checkRequest(BindRequest bindRequest) {
        if (StringUtils.isEmpty(bindRequest.getAccount())) {
            return "账号不能为空";
        }
        if (StringUtils.isEmpty(bindRequest.getValidationCode())) {
            return "验证码不能为空";
        }
        if (StringUtils.isEmpty(bindRequest.getPhoneOrMail())) {
            return "手机或邮箱不能为空";
        }
        return "";
    }

}
