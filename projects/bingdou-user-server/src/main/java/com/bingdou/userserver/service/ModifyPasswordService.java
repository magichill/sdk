package com.bingdou.userserver.service;

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
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.ModifyPasswordRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 修改密码服务类
 */
@Service
public class ModifyPasswordService extends BaseService implements IMethodService {

    @Autowired
    private ValidateCodeService validateCodeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ModifyPasswordRequest modifyPasswordRequest = new ModifyPasswordRequest();
        modifyPasswordRequest.parseRequest(request);
        return modifyPasswordRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        ModifyPasswordRequest modifyPasswordRequest = (ModifyPasswordRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(modifyPasswordRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "modify_password";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ModifyPasswordRequest modifyPasswordRequest = (ModifyPasswordRequest) baseRequest;
        if (StringUtils.isEmpty(modifyPasswordRequest.getAccount())
                || StringUtils.isEmpty(modifyPasswordRequest.getOldPassword())
                || StringUtils.isEmpty(modifyPasswordRequest.getNewPassword())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        return dealModifyPassword(modifyPasswordRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ModifyPasswordRequest modifyPasswordRequest = (ModifyPasswordRequest) baseRequest;
        if (StringUtils.isEmpty(modifyPasswordRequest.getAccount())
                || StringUtils.isEmpty(modifyPasswordRequest.getOldPassword())
                || StringUtils.isEmpty(modifyPasswordRequest.getNewPassword())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        return dealModifyPassword(modifyPasswordRequest, user);
    }

    private ServiceResult dealModifyPassword(ModifyPasswordRequest modifyPasswordRequest, User user) {
        if (!User.isValidPassword(modifyPasswordRequest.getNewPassword())) {
            return ServiceResultUtil.illegal("密码不合法");
        }
        boolean checkValidationCode = false;
        if (StringUtils.isNotEmpty(user.getEmail()) || StringUtils.isNotEmpty(user.getMobile())) {
            checkValidationCode = true;
        }
        if (checkValidationCode) {
            boolean checkMobile = StringUtils.isNotEmpty(user.getMobile());
            ValidateCode validateCode;
            if (checkMobile) {
                validateCode = validateCodeService.getValidateCode4Mobile(user.getMobile(),
                        SendCodeType.MODIFY_PASSWORD);
            } else {
                validateCode = validateCodeService.getValidateCode4Email(user.getEmail(),
                        SendCodeType.MODIFY_PASSWORD);
            }
            if (validateCode == null) {
                return ServiceResultUtil.illegal("验证码与手机号或邮箱不匹配");
            }
            if (!UserUtils.isValidationCodeValid(modifyPasswordRequest.getValidationCode(),
                    validateCode.getVcode(), validateCode.getVcodeTime())) {
                return ServiceResultUtil.illegal("验证码有误或已失效");
            }
        }
        String oldPassword = User.getEncodedPassword(modifyPasswordRequest.getOldPassword(), user.getSalt());
        if (oldPassword.equals(user.getPassword())) {
            String salt = User.generateSalt();
            String encodedNewPassword = User.getEncodedPassword(modifyPasswordRequest.getNewPassword(), salt);
            int updatedRows = userBaseService.updatePassword(user.getId(), encodedNewPassword, salt);
            if (updatedRows > 0) {
                userBaseService.deleteToken(user.getId());
                userBaseService.clearLoginError(user.getId());
                if (checkValidationCode) {
                    boolean checkMobile = StringUtils.isNotEmpty(user.getMobile());
                    LogContext.instance().info("清除验证码信息");
                    if (checkMobile) {
                        validateCodeService.clearValidateCode4Mobile(user.getMobile(), SendCodeType.MODIFY_PASSWORD);
                    } else {
                        validateCodeService.clearValidateCode4Email(user.getEmail(), SendCodeType.MODIFY_PASSWORD);
                    }
                }
                LogContext.instance().info("修改密码成功");
                DataLogUtils.recordHadoopLog(HadoopLogAction.MODIFY_PASSWORD, modifyPasswordRequest, user,
                        RequestUtil.getClientIp(modifyPasswordRequest.getRequest()), "", "", false);
                return ServiceResultUtil.success();
            } else {
                return ServiceResultUtil.illegal("修改密码失败");
            }
        } else {
            return ServiceResultUtil.illegal("原密码不正确");
        }
    }

}
