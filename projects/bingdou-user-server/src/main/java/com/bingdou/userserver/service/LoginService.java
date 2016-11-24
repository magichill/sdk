package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.safe.SafeService;
import com.bingdou.core.service.user.LoginBaseService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.LoginRequest;
import com.bingdou.userserver.response.LoginResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录服务类
 */
@Service
public class LoginService extends BaseService implements IMethodService {

    @Autowired
    private LoginBaseService loginBaseService;
    @Autowired
    private SafeService safeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.parseRequest(request);
        return loginRequest;
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
        return "login";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        LoginRequest loginRequest = (LoginRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(loginRequest.getAppId());
        return dealLogin(loginRequest, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        LoginRequest loginRequest = (LoginRequest) baseRequest;
        if (loginRequest.getOtherInfo() == null) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
//        Application application = getValidApplication4Client(loginRequest);
        return dealLogin(loginRequest, request);
    }

//    private ServiceResult dealLogin(LoginRequest loginRequest, Application application,
//                                    HttpServletRequest request) throws Exception {
    private ServiceResult dealLogin(LoginRequest loginRequest,
                                    HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(loginRequest.getAccount())) {
            return ServiceResultUtil.illegal("帐号为空");
        }
        User user = userBaseService.getUserDetailByAccount(loginRequest.getAccount());
        String errorMessage = getUserStatusErrorMessage(user);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        boolean isClientRequest = isClientRequest(request);
        boolean isAutoLogin = false;
        if (isClientRequest && StringUtils.isEmpty(loginRequest.getPassword())
                && StringUtils.isNotEmpty(loginRequest.getToken())) {
            isAutoLogin = true;
            LogContext.instance().info("自动登录逻辑");
            String tokenDevice = getDeviceNo4Client(loginRequest);

            boolean checkResult = userBaseService.checkUserToken4Client(user.getId(),
                        loginRequest.getToken(), tokenDevice, getSafeInfo(request));
            if (!checkResult) {
                return ServiceResultUtil.illegal("自动登录失效，请重新登录");
            }
        }
        String clientIp = RequestUtil.getClientIp(request);
        if (!isAutoLogin && !user.isPasswordValid(loginRequest.getPassword())) {
            if (isClientRequest(request)) {
                safeService.dealDeviceLoginError(getDeviceNo4Client(loginRequest), user.getId()
                        , getClientOsByRequest(loginRequest));
            }
            return ServiceResultUtil.illegal(dealErrorPwd(user));
        }
//        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
//        user.setVipLevel(userVipGrade.getUserLevelId());
//        boolean isNewUser = userStatisticsService.recordUserActiveRecord(user.getId(), application);
//        LogContext.instance().info("是否是新用户:" + isNewUser);
        boolean isNewDevice = false;
        boolean isSupportLive = false;
        boolean isSupportVirtualMoney = false;
        boolean isSigned = false;
        String tokenDevice = "";
        String oldUid = "";
        String oldUa = "";
        String sdkVersion = "";
        LoginResponse loginResponse = new LoginResponse();
        if (isClientRequest) {
            LogContext.instance().info("客户端专有逻辑");
            sdkVersion = loginRequest.getOtherInfo().getSdkVersion();
            oldUid = UserUtils.getOldUid4Client(loginRequest);
            oldUa = UserUtils.getOldUa4Client(loginRequest);
            Os os = getClientOsByRequest(loginRequest);
            tokenDevice = getDeviceNo4Client(loginRequest);
//            isNewDevice = deviceService.isNewDevice(loginRequest.getDeviceInfo());
//            LogContext.instance().info("是否是新设备:" + isNewDevice);
//            isSupportVirtualMoney = switchRuleService.isSupportVirtualMoney(application.getAppId(), application.getOs(),
//                    loginRequest.getOtherInfo().getChannel());
//            LogContext.instance().info("获取游戏币开关结果:" + isSupportVirtualMoney);
//            if (Os.IOS.equals(os)) {
//                isSupportLive = switchRuleService.isSupportLive(application.getAppId(), os.getIndex(),
//                        loginRequest.getOtherInfo().getChannel());
//                LogContext.instance().info("获取直播开关结果:" + isSupportLive);
//            }
//            List<ConfigMessage> configMessageList = messageService.getConfigMessageBox4Login(
//                    loginRequest, os, clientIp, isNewUser, user, false);
//            loginResponse.setMessageInfoList(UserServerUtils.convertMessageInfoListBy(configMessageList));
//            voucherService.giveVoucher4Login(user, loginRequest, application.getAppId(),
//                    clientIp, isNewUser);

//            int unreadMessageCount = messageService.getMessageCountByUserId(user.getId(),
//                    MessageStatus.UNREAD);
//            LogContext.instance().info("未读消息数量:" + unreadMessageCount);
//            loginResponse.setUnreadMessageNum(unreadMessageCount);
//            String model = os == Os.IOS ? loginRequest.getDeviceInfo().getModel() : "";
//            List<Notice> noticeList = noticeService.getNoticeList4Login(user, loginRequest,
//                    isNewUser, isPad(loginRequest), application.getAppId(), loginRequest.getOtherInfo().getChannel(),
//                    model);
//            loginResponse.setNoticeInfoList(UserServerUtils.covertNoticeInfoListBy(noticeList));
//            userChannelMoveService.addMoveChannelRecord4Client(user.getId(), loginRequest, false);
//            userStatisticsService.recordUserSourceReport4Client(user.getId(), application, loginRequest, false);
        }
        loginBaseService.setLastLoginInfo(user.getId(), clientIp, oldUid, oldUa);
        boolean updateTokenResult = userBaseService.updateToken(user, tokenDevice, getSafeInfo(request), false);
        LogContext.instance().info("更新token结果:" + updateTokenResult);
        DataLogUtils.recordHadoopLog(isAutoLogin ? HadoopLogAction.AUTO_LOGIN : HadoopLogAction.LOGIN,
                loginRequest, user, clientIp, "", "", isNewDevice);
//        int virtualMoneyFen = getVirtualMoneyFen4Show(user.getId(), application.getOs(), sdkVersion,
//                false, isSupportVirtualMoney);
        loginResponse.parseFromUser(user, null, isSupportVirtualMoney, isSupportLive,
                isSigned, 0);
        JsonElement result = JsonUtil.bean2JsonTree(loginResponse);
        LogContext.instance().info("登录成功");
        return ServiceResultUtil.success(result);
    }

    private String dealErrorPwd(User user) throws Exception {
        int errorCount;
        if (user.isLoginErrorInOneHour()) {
            if (user.isTouchLoginErrorTimes()) {
                throw new Exception("逻辑错误，已验证过该路径");
            } else {
                errorCount = user.getLoginError();
            }
        } else {
            loginBaseService.clearLoginError(user.getId());
            errorCount = 0;
        }
        loginBaseService.setLoginError(user.getId(), errorCount + 1);
        if (errorCount > 2 && errorCount < 5) {
            return "密码错误，连续输错5次将禁用1小时，" + "还有" + (5 - errorCount) + "次";
        }
        return "密码错误";
    }

}
