//package com.bingdou.userserver.service;
//
//import com.bingdou.core.helper.BaseRequest;
//import com.bingdou.core.helper.ServiceResult;
//import com.bingdou.core.helper.ServiceResultUtil;
//import com.bingdou.core.model.Os;
//import com.bingdou.core.model.User;
//import com.bingdou.core.service.BaseService;
//import com.bingdou.core.service.IMethodService;
//import com.bingdou.core.service.user.DeviceService;
//import com.google.gson.JsonElement;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * 初始化服务类
// */
//@Service
//public class InitService extends BaseService implements IMethodService {
//
//    @Autowired
//    private DeviceService deviceService;
//    @Autowired
//    private MessageService messageService;
//
//    @Override
//    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
//        InitRequest initRequest = new InitRequest();
//        initRequest.parseRequest(request);
//        return initRequest;
//    }
//
//    @Override
//    public boolean checkUser() {
//        return false;
//    }
//
//    @Override
//    public User getUser(BaseRequest baseRequest) {
//        return null;
//    }
//
//    @Override
//    public String getMethodName() {
//        return "init";
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
//        return null;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
//        InitRequest initRequest = (InitRequest) baseRequest;
//        if (initRequest.getDeviceInfo() == null) {
//            return ServiceResultUtil.illegal("参数错误");
//        }
//
//        LogContext.instance().info("初始化成功");
//        return ServiceResultUtil.success(result);
//    }
//
//    private void setAppUpdateResult(InitResponse initResponse, Os os, InitRequest initRequest) {
//        LogContext.instance().info("获取应用商店资源逻辑");
//        String updateResult;
//        String url;
//        String packageName = initRequest.getOtherInfo().getPackageName();
//        int isTest = initRequest.getIsTest();
//        try {
//            if (Os.ANDROID.equals(os)) {
//                String channel = initRequest.getOtherInfo().getChannel();
//                url = UserServerProperties.ANDROID_SOFT_UPDATE_URL + "?pkg=" + packageName +
//                        "&t=" + isTest + "&channel=" + channel;
//                LogContext.instance().info("获取海马玩应用商店资源:" + url);
//                updateResult = HttpClientUtil.doGetHttpClient("get-android-resource", url,
//                        UserConstants.INIT_CALL_RESOURCE_APP_STORE_TIME_OUT, UserConstants.INIT_CALL_RESOURCE_APP_STORE_TIME_OUT);
//                LogContext.instance().info("获取海马玩资源包信息结果:" + updateResult);
//                if (StringUtils.isNotEmpty(updateResult)) {
//                    AndroidUpdateResponse androidUpdateResponse = JsonUtil.jsonStr2Bean(updateResult,
//                            AndroidUpdateResponse.class);
//                    initResponse.setAndroidUpdateResponse(androidUpdateResponse);
//                } else {
//                    LogContext.instance().error("未获取到海马玩返回结果");
//                }
//            } else {
//                int iosModel = initRequest.getDeviceInfo().getIosInfo().getIosModel();
//                url = UserServerProperties.IOS_SOFT_UPDATE_URL + "?bundleID=" + packageName
//                        + "&v=" + initRequest.getOtherInfo().getAppVersion()
//                        + "&t=" + isTest + "&d=" + iosModel;
//                LogContext.instance().info("获取海马助手应用商店资源:" + url);
//                updateResult = HttpClientUtil.doGetHttpClient("get-ios-resource", url,
//                        UserConstants.INIT_CALL_RESOURCE_APP_STORE_TIME_OUT, UserConstants.INIT_CALL_RESOURCE_APP_STORE_TIME_OUT);
//                LogContext.instance().info("获取海马助手资源包信息结果:" + updateResult);
//                if (StringUtils.isNotEmpty(updateResult)) {
//                    IosUpdateResponse iosUpdateResponse = JsonUtil.jsonStr2Bean(updateResult, IosUpdateResponse.class);
//                    initResponse.setIosUpdateResponse(iosUpdateResponse);
//                } else {
//                    LogContext.instance().error("未获取到海马助手返回结果");
//                }
//            }
//        } catch (Exception e) {
//            LogContext.instance().error(e, "获取资源包更新信息失败");
//        }
//    }
//
//}
