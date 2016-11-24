package com.bingdou.userserver.service;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.RegisterRequest;
import com.bingdou.userserver.response.RegisterResponse;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-10-31.
 */
@Service
public class RegisterService extends BaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.parseRequest(request);
        return registerRequest;
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
        return "register";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RegisterRequest registerRequest = (RegisterRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(registerRequest.getAppId());
        return dealRegister(registerRequest, request, "", "");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RegisterRequest registerRequest = (RegisterRequest) baseRequest;
//        Application application = getValidApplication4Client(registerRequest);
        String ua = UserUtils.getOldUa4Client(baseRequest);
        String uid = UserUtils.getOldUid4Client(baseRequest);
        return dealRegister(registerRequest, request, uid, ua);
    }

    @SuppressWarnings("Duplicates")
    //TODO 暂时注释掉Application对象
//    private ServiceResult dealRegister(RegisterRequest registerRequest, HttpServletRequest request,
//                                       String uid, String ua, Application application) throws Exception {
    private ServiceResult dealRegister(RegisterRequest registerRequest, HttpServletRequest request,
                                       String uid, String ua) throws Exception {
        if (!User.isValidLoginName(registerRequest.getLoginName())) {
            return ServiceResultUtil.illegal("用户名不合法");
        }
        if (!User.isValidPassword(registerRequest.getPassword())) {
            return ServiceResultUtil.illegal("密码不合法");
        }
        boolean exist = userBaseService.isExistsLoginName(registerRequest.getLoginName());
        if (exist) {
            return ServiceResultUtil.illegal("用户名已经存在");
        }
        String clientIp = RequestUtil.getClientIp(request);
        String salt = User.generateSalt();
        String encodePwd = User.getEncodedPassword(registerRequest.getPassword(), salt);
        User user = new User();
        user.setLoginName(registerRequest.getLoginName());
        user.setPassword(encodePwd);
        user.setSalt(salt);
        user.setDevice("");
        user.setMobile("");
        String appId = isClientRequest(request) ? registerRequest.getOtherInfo().getAppId()
                : registerRequest.getAppId();
        boolean createSuccess = userBaseService.createUser(user, appId, clientIp, uid, ua,
                UserConstants.SECURE_LEVEL_1);
        boolean isNewDevice = false;
//        boolean isSupportVirtualMoney = false;
        boolean isSupportLive = false;
        if (createSuccess) {
            RegisterResponse registerResponse = new RegisterResponse();
            LogContext.instance().info("插入用户成功");
            User newUser = userBaseService.getDetailByLoginName(registerRequest.getLoginName());
            String tokenDevice = "";
            String sdkVersion = "";
            //TODO vip等级查询
//            UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(newUser.getId());
            boolean updateTokenResult = userBaseService.updateToken(newUser, tokenDevice, getSafeInfo(request), true);
            LogContext.instance().info("更新用户token:" + updateTokenResult);
//            boolean isNewUser = userStatisticsService.recordUserActiveRecord(newUser.getId(), application);
//            LogContext.instance().info("是否是新用户:" + isNewUser);
            DataLogUtils.recordHadoopLog(HadoopLogAction.REGISTER, registerRequest, newUser,
                    clientIp, "", "", isNewDevice);
//            int virtualMoneyFen = getVirtualMoneyFen4Show(newUser.getId(), application.getOs(),
//                    sdkVersionVersion, true, isSupportVirtualMoney);
            registerResponse.parseFromUser(newUser, null, false, isSupportLive,
                    false, 0);
            JsonElement result = JsonUtil.bean2JsonTree(registerResponse);
            LogContext.instance().info("注册成功");
            return ServiceResultUtil.success(result);
        } else {
            return ServiceResultUtil.illegal("注册用户失败");
        }
    }
}
