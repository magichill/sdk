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
import com.bingdou.core.service.user.ThirdFastLoginAuthBaseService;
import com.bingdou.core.service.user.UserStatisticsService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.ThirdFastLoginRequest;
import com.bingdou.userserver.response.*;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/2/21.
 * 第三方登录接口
 */
@Service
public class ThirdFastLoginService extends BaseService implements IMethodService {

    @Autowired
    private ThirdFastLoginAuthBaseService thirdFastLoginAuthBaseService;
    @Autowired
    private VipGradeService vipGradeService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private UserStatisticsService userStatisticsService;

    @Autowired
    private LoginBaseService loginBaseService;
    @Override
    public String getMethodName() {
        return "third_login";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return dealThirdLogin(request,baseRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return dealThirdLogin(request,baseRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ThirdFastLoginRequest registerRequest = new ThirdFastLoginRequest();
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

    private ServiceResult dealThirdLogin(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ThirdFastLoginRequest thirdFastLoginRequest = (ThirdFastLoginRequest) baseRequest;
        if (thirdFastLoginRequest.getAuthResult() == null) {
            return ServiceResultUtil.illegal("参数错误");
        }
        FastLoginAuthResult fastLoginAuthResult;
        if (thirdFastLoginRequest.getThirdAuthType() == ThirdFastLoginType.ALI.getIndex()) {
            fastLoginAuthResult = new AliFastLoginAuthResult();
            boolean isSuccess = fastLoginAuthResult.execute(thirdFastLoginRequest.getAuthResult());
            if (!isSuccess)
                return ServiceResultUtil.illegal("支付宝授权登录失败");
            boolean exist = thirdFastLoginAuthBaseService.existTargetId(
                    getDeviceNo4Client(thirdFastLoginRequest));
            if (!exist) {
                LogContext.instance().error("TARGET ID错误");
                return ServiceResultUtil.illegal("支付宝授权登录失败");
            }
        } else if(thirdFastLoginRequest.getThirdAuthType() == ThirdFastLoginType.WEIXIN.getIndex()){
            fastLoginAuthResult = new WeixinFastLoginAuthResult();
            boolean isSuccess = fastLoginAuthResult.execute(thirdFastLoginRequest.getAuthResult());
            if (!isSuccess)
                return ServiceResultUtil.illegal("微信授权登录失败");
//            boolean exist = thirdFastLoginAuthBaseService.existTargetId(
//                    getDeviceNo4Client(thirdFastLoginRequest));
//            if (!exist) {
//                LogContext.instance().error("TARGET ID错误");
//                return ServiceResultUtil.illegal("微信授权登录失败");
//            }
        } else {
            return ServiceResultUtil.illegal("非法授权登录类型");
        }
        int userId = thirdFastLoginAuthBaseService.getBingDouUserIdByOpenId(fastLoginAuthResult.getOpenId(),
                thirdFastLoginRequest.getThirdAuthType());
        String clientIp = RequestUtil.getClientIp(request);

        boolean isClient = isClientRequest(request);
        String appId = "";
        if(isClient){
            appId = thirdFastLoginRequest.getOtherInfo().getAppId();
        }else{
            appId = thirdFastLoginRequest.getAppId();
        }
        if (userId <= 0) {
            LogContext.instance().info("不存在此授权记录,创建新账号");
            WexinUserInfoResponse weixinUserInfo = getUserInfoFromWechat(fastLoginAuthResult.getOpenId(),fastLoginAuthResult.getAuthCode());
            if(StringUtils.isNotEmpty(weixinUserInfo.getErrcode())){
                LogContext.instance().info("信息用户信息获取失败，"+weixinUserInfo.getErrmsg());
                return ServiceResultUtil.illegal("获取微信用户信息失败");
            }
            User newUser = new User();
            String loginName = newUser.generateThirdFastLoginName();
            boolean exist = userBaseService.isExistsLoginName(loginName);
            if (exist) {
                return ServiceResultUtil.illegal("授权登录失败");
            }
            newUser.setLoginName(loginName);
            newUser.setPassword("");
            newUser.setSalt("");
            newUser.setDevice("");
            newUser.setMobile("");
            newUser.setNickName(weixinUserInfo.getNickname());
            newUser.setGender(weixinUserInfo.getSex());
            newUser.setAvatar(weixinUserInfo.getHeadimgurl());

            boolean createSuccess = userBaseService.createUser(newUser, appId,
                    clientIp, "", "", UserConstants.SECURE_LEVEL_5);
            if (createSuccess) {
                LogContext.instance().info("插入授权登录信息");
                ThirdFastLogin thirdFastLogin = new ThirdFastLogin();
                thirdFastLogin.setAuthToken(fastLoginAuthResult.getAuthCode());
                thirdFastLogin.setOpenId(fastLoginAuthResult.getOpenId());
                thirdFastLogin.setOpenType(thirdFastLoginRequest.getThirdAuthType());
                thirdFastLogin.setUserId(newUser.getId());
                thirdFastLoginAuthBaseService.insertLoginInfo(thirdFastLogin);
                User alreadyAddUser = userBaseService.getDetailById(newUser.getId());
                if(isClient) {
                    return dealWithClient(alreadyAddUser, thirdFastLoginRequest, request, true, clientIp);
                }else{
                    return dealWithServer(alreadyAddUser,thirdFastLoginRequest,request,true,clientIp);
                }
            } else {
                return ServiceResultUtil.serverError("授权登录失败");
            }
        } else {
            LogContext.instance().info("存在此授权记录");
            User userByUserId = userBaseService.getDetailById(userId);
            String errorMessage = userByUserId.checkStatus();
            if (StringUtils.isNotEmpty(errorMessage)) {
                return ServiceResultUtil.illegal(errorMessage);
            }
            boolean updateResult = thirdFastLoginAuthBaseService.updateAuthToken(fastLoginAuthResult.getOpenId(),
                    ThirdFastLoginType.getByIndex(thirdFastLoginRequest.getThirdAuthType()), fastLoginAuthResult.getAuthCode());
            LogContext.instance().info("更新授权码结果:" + updateResult);
            if (!updateResult) {
                return ServiceResultUtil.serverError("授权登录失败");
            }
            if(isClient) {
                return dealWithClient(userByUserId, thirdFastLoginRequest, request, false, clientIp);
            }else{
                return dealWithServer(userByUserId,thirdFastLoginRequest,request,false,clientIp);
            }
        }
    }

    private ServiceResult dealWithServer(User user, ThirdFastLoginRequest thirdFastLoginRequest,
                                         HttpServletRequest request, boolean isNewThirdUser,
                                         String clientIp) throws Exception {
        LogContext.instance().info("更新token");
        boolean updateTokenResult = userBaseService.updateToken(user, "", getSafeInfo(request), isNewThirdUser);
        LogContext.instance().info("更新token:" + updateTokenResult);
        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());

        if (!isNewThirdUser) {
            loginBaseService.setLastLoginInfo(user.getId(), clientIp,"","");
        }

        LogContext.instance().info("授权登录成功");
        DataLogUtils.recordHadoopLog(isNewThirdUser ?
                        HadoopLogAction.THIRD_FAST_LOGIN_REGISTER : HadoopLogAction.THIRD_FAST_LOGIN,
                thirdFastLoginRequest, user, clientIp, "", "", false);
        ThirdFastLoginResponse thirdFastLoginResponse = new ThirdFastLoginResponse();
        thirdFastLoginResponse.parseFromUser(user, userVipGrade, 1, 0,null);
        JsonElement result = JsonUtil.bean2JsonTree(thirdFastLoginResponse);
        return ServiceResultUtil.success(result);
    }

    private ServiceResult dealWithClient(User user, ThirdFastLoginRequest thirdFastLoginRequest, HttpServletRequest request, boolean isNewThirdUser,
                               String clientIp) throws Exception {
        Application application = getValidApplication4Client(thirdFastLoginRequest);
        LogContext.instance().info("更新token");
        String tokenDevice = getDeviceNo4Client(thirdFastLoginRequest);
        boolean updateTokenResult = userBaseService.updateToken(user, tokenDevice, getSafeInfo(request), isNewThirdUser);
        LogContext.instance().info("更新token:" + updateTokenResult);
//        userStatisticsService.recordUserSourceReport4Client(user.getId(), application, thirdFastLoginRequest, isNewThirdUser);

        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        boolean isNewDevice = deviceService.isNewDevice(thirdFastLoginRequest.getDeviceInfo());
        thirdFastLoginAuthBaseService.clearLoginAuth(tokenDevice);
        if (!isNewThirdUser) {
            loginBaseService.setLastLoginInfo(user.getId(), clientIp,"","");
        }

        LogContext.instance().info("授权登录成功");
        DataLogUtils.recordHadoopLog(isNewThirdUser ?
                        HadoopLogAction.THIRD_FAST_LOGIN_REGISTER : HadoopLogAction.THIRD_FAST_LOGIN,
                thirdFastLoginRequest, user, clientIp, "", "", isNewDevice);
        ThirdFastLoginResponse thirdFastLoginResponse = new ThirdFastLoginResponse();
        thirdFastLoginResponse.parseFromUser(user, userVipGrade, 1, 0,null);
        JsonElement result = JsonUtil.bean2JsonTree(thirdFastLoginResponse);
        return ServiceResultUtil.success(result);
    }

    private WexinUserInfoResponse getUserInfoFromWechat(String openId,String token) throws Exception {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId;
        String userInfoResult = HttpClientUtil.doGetHttpClient("获取微信用户信息",url);
        WexinUserInfoResponse userInfoResponse = JsonUtil.jsonStr2Bean(userInfoResult,WexinUserInfoResponse.class);
        if(userInfoResponse.getSex()==2){
            userInfoResponse.setSex(0);
        }
        return userInfoResponse;
    }
}
