package com.bingdou.core.service;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.model.Application;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.SafeInfo;
import com.bingdou.core.model.User;
import com.bingdou.core.service.pay.AppBaseService;
import com.bingdou.core.service.pay.PayTypeBaseService;
import com.bingdou.core.service.pay.RechargeOrderService;
import com.bingdou.core.service.pay.paytype.PayTypeFactory;
import com.bingdou.core.service.user.UserBaseService;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-10-25.
 */
public abstract class BaseService {

    @Autowired
    protected UserBaseService userBaseService;

    @Autowired
    protected AppBaseService appBaseService;

    @Autowired
    protected PayTypeBaseService payTypeBaseService;
    @Autowired
    protected RechargeOrderService rechargeOrderService;
    @Autowired
    protected PayTypeFactory payTypeFactory;
    /**
     * 获取请求父类
     */
    public abstract BaseRequest getBaseRequest(HttpServletRequest request) throws Exception;

    /**
     * 是否检查用户信息
     */
    public abstract boolean checkUser();

    /**
     * 获取用户信息
     */
    public abstract User getUser(BaseRequest baseRequest);

    /**
     * 获取合法的应用信息(客户端用)
     */
    //TODO 注释 暂未做
    public Application getValidApplication4Client(BaseRequest request) {
        LogContext.instance().info("获取合法应用");
        return appBaseService.checkAppStatus4Login(
                request.getOtherInfo().getAppId(),
                request.getOtherInfo().getPackageName(), Os.getOsByIndex(request.getDeviceInfo().getOs()));
    }

    /**
     * 检查客户端应用合法性
     */
    //TODO 注释 暂未做
    public boolean isApplicationValid4Client(BaseRequest request) {
        LogContext.instance().info("检查应用合法性");
        Application application = appBaseService.checkAppStatus4Login(
                request.getOtherInfo().getAppId(),
                request.getOtherInfo().getPackageName(), Os.getOsByIndex(request.getDeviceInfo().getOs()));
        return !(application == null || application.getId() == null || application.getId() <= 0);
    }

    /**
     * 检查服务器端应用合法性
     */
    public boolean isApplicationValid4Server(BaseRequest request) {
        LogContext.instance().info("检查APP ID是否合法");
        if (StringUtils.isEmpty(request.getAppId())) {
            return false;
        }
        Application application = appBaseService.getAppByAppId(request.getAppId());
        return application != null && application.getId() > 0;
    }

    /**
     * 检查TOKEN合法性
     */
    public boolean validateRequestToken(HttpServletRequest request, int userId, BaseRequest baseRequest) throws Exception {
        LogContext.instance().info("检查token逻辑");
        boolean result = true;
        SafeInfo safeInfo = getSafeInfo(request);
        if (isValidateRequestToken(safeInfo, baseRequest)) {
            result = userBaseService.checkUserToken4Server(userId, baseRequest.getToken());
        }
        LogContext.instance().info("检查token结果:" + result);
        return result;
    }

    /**
     * 获取用户状态错误提示语
     */
    public String getUserStatusErrorMessage(User user) {
        LogContext.instance().info("检查用户状态");
        if (user == null || user.getId() <= 0) {
            LogContext.instance().warn("用户不存在");
            return "用户不存在";
        }
        String error = user.checkStatus();
        if (StringUtils.isNotEmpty(error)) {
            LogContext.instance().error(error);
            return error;
        }
        if (user.isLoginLocked()) {
            LogContext.instance().error("用户(" + user.getId() + ")被禁用1小时");
            userBaseService.deleteToken(user.getId());
            return "此帐号1小时内登录错误超过5次，禁用1小时";
        }
        LogContext.instance().info("用户状态正常");
        return "";
    }

    /**
     * 根据请求对象获取客户端OS对象
     */
    public Os getClientOsByRequest(BaseRequest request) {
        if (request == null || request.getDeviceInfo() == null)
            return null;
        if (request.getDeviceInfo().getOs() != Os.IOS.getIndex()
                && request.getDeviceInfo().getOs() != Os.ANDROID.getIndex())
            return null;
        return Os.getOsByIndex(request.getDeviceInfo().getOs());
    }

    /**
     * 获取设备信息(IOS为IDFA,ANDROID为ANDROID ID)
     */
    public String getDeviceNo4Client(BaseRequest request) throws Exception {
        if (Os.ANDROID.getIndex() == request.getDeviceInfo().getOs()) {
            return request.getDeviceInfo().getAndroidInfo().getAndroidId();
        } else if (Os.IOS.getIndex() == request.getDeviceInfo().getOs()) {
            return request.getDeviceInfo().getIosInfo().getIdfa();
        } else {
            throw new Exception("非法客户端OS");
        }
    }

    /**
     * 是否是客户端请求
     */
    protected boolean isClientRequest(HttpServletRequest request) throws Exception {
        Object object = request.getAttribute(Constants.REQUEST_SAFE_INFO_NAME);
        if (object == null)
            throw new Exception("安全加固对象为空");
        SafeInfo safeInfo = (SafeInfo) object;
        boolean result = safeInfo.getIsClient() == 1;
        LogContext.instance().info("是否是客户端请求:" + result);
        return result;
    }

    /**
     * 获取安全验证对象
     */
    protected SafeInfo getSafeInfo(HttpServletRequest request) throws Exception {
        Object object = request.getAttribute(Constants.REQUEST_SAFE_INFO_NAME);
        if (object == null) {
            throw new Exception("安全加固对象为空");
        }
        return (SafeInfo) object;
    }

    private boolean isValidateRequestToken(SafeInfo safeInfo, BaseRequest baseRequest) throws Exception {
        if (safeInfo.getIsClient() == 1) {
            LogContext.instance().info("是客户端请求,检查token");
            return true;
        }
        String methods = safeInfo.getMethodName();
        if (StringUtils.isEmpty(methods)) {
            LogContext.instance().info("是服务端请求,未配置不检查token方法列表,检查token");
            return true;
        }
        String[] methodArray = methods.split(",");
        if (methodArray.length <= 0) {
            LogContext.instance().info("是服务端请求,未配置不检查token方法,检查token");
            return true;
        }
        for (String m : methodArray) {
            if (m.equals(baseRequest.getMethod())) {
                LogContext.instance().info("是服务端请求,配置了不检查token方法:" + m);
                return false;
            }
        }
        LogContext.instance().info("需要检查token");
        return true;
    }

    protected int getVirtualMoneyFen4Use(int userId, int osIndex, String appId, String channel) {
//        boolean isSupportVirtualMoney = switchRuleService.isSupportVirtualMoney(appId,
//                osIndex, channel);
//        if (!isSupportVirtualMoney)
//            return 0;
        return userBaseService.getVirtualMoney(userId, osIndex, false);
    }

    protected int getVirtualMoneyFen4Show(int userId, int osIndex, String sdkVersion, String appId,
                                          String channel, boolean isNewUser) {
//        boolean isSupportVirtualMoney = switchRuleService.isSupportVirtualMoney(appId,
//                osIndex, channel);
        return getVirtualMoneyFen4Show(userId, osIndex, sdkVersion, isNewUser, false);
    }

    protected int getVirtualMoneyFen4Show(int userId, int osIndex, String sdkVersion, boolean isNewUser,
                                          boolean isSupportVirtualMoney) {
        int virtualMoneyFen;
        if (isSupportVirtualMoney) {
            virtualMoneyFen = userBaseService.getVirtualMoney(userId, osIndex, isNewUser);
        } else {
            //为了兼容老版本,IOS小于1.6.0版本,ANDROID小于1.3.0版本,余额不加游戏币余额
            if (osIndex == Os.IOS.getIndex() && "1.6.0".compareTo(sdkVersion) > 0) {
                return 0;
            } else if (osIndex == Os.ANDROID.getIndex() && "1.3.0".compareTo(sdkVersion) > 0) {
                return 0;
            }
            virtualMoneyFen = userBaseService.getVirtualMoney(userId, osIndex, isNewUser);
        }
        return virtualMoneyFen;
    }


}
