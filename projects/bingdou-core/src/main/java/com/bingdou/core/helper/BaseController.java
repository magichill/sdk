package com.bingdou.core.helper;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.model.SafeInfo;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.MethodServiceFactory;
import com.bingdou.core.service.safe.SafeService;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.KeyGroup;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-10-29.
 */
public abstract class BaseController {

    @Autowired
    protected SafeService safeService;
    @Autowired
    private MethodServiceFactory methodServiceFactory;

    protected abstract boolean isServerRequest();

    protected void setErrorMessage2Request(HttpServletRequest request, String errorMessage) {
        request.setAttribute(Constants.REQUEST_ERROR_MESSAGE_NAME, errorMessage);
    }

    protected void setKeyGroup2Request(HttpServletRequest request, KeyGroup keyGroup) {
        request.setAttribute(Constants.REQUEST_KEY_GROUP_NAME, keyGroup);
    }

    protected void setSafeInfo2Request(HttpServletRequest request, SafeInfo safeInfo) {
        request.setAttribute(Constants.REQUEST_SAFE_INFO_NAME, safeInfo);
    }

    protected String baseDispatch(HttpServletRequest request, String requestParamLoggerName, String method)
            throws Exception {
        LogContext logContext = LogContext.instance();
        logContext.clear();
        logContext.setRequestUUID(CodecUtils.getRequestUUID());
        logContext.setLoggerName(requestParamLoggerName);
//        String method = request.getParameter("method");
        String sign = request.getParameter("sign");
        String version = request.getParameter("version");
        String param = request.getParameter("param");
        String requestSource = request.getParameter("request_source_index");
        try {
            logContext.info("请求方法:" + method);
            logContext.info("请求签名:" + sign);
            logContext.info("请求版本:" + version);
            logContext.info("请求来源:" + requestSource);
            logContext.info("请求内容(原始):" + param);
            if (StringUtils.isEmpty(method)
                    || StringUtils.isEmpty(sign)
                    || StringUtils.isEmpty(param)
                    || StringUtils.isEmpty(requestSource)) {
                logContext.error("请求内容参数为空");
                return "请求内容参数为空";
            }
            String clientIp = RequestUtil.getClientIp(request);
            logContext.info("请求IP:" + clientIp);
            SafeInfo safeInfo = safeService.getSafeInfo(requestSource);
            if (safeInfo == null) {
                setErrorMessage2Request(request, "非法请求来源");
                throw new Exception("非法请求来源");
            }
            //验证服务器端请求,必须来自IP白名单
            if (!isClientRequest(safeInfo)) {
                LogContext.instance().info("是来自服务器端的请求,判断IP白名单和是否验证token逻辑");
                if (!isValidClient(clientIp, safeInfo)) {
                    setErrorMessage2Request(request, "非法IP");
                    throw new Exception("非法IP");
                }
            }
            if (!methodServiceFactory.getMethodServiceMap().containsKey(method)) {
                setErrorMessage2Request(request, "非法请求方法");
                throw new Exception("非法请求方法");
            }
            KeyGroup keyGroup = KeyGroup.valueOf(safeInfo.getKeyGroup());
            setKeyGroup2Request(request, keyGroup);
            setSafeInfo2Request(request, safeInfo);
            IMethodService methodService = methodServiceFactory.getMethodServiceMap().get(method);
            BaseService baseService = (BaseService) methodService;
            BaseRequest baseRequest = baseService.getBaseRequest(request);
            ServiceResult serviceResult;

            if (!isClientRequest(safeInfo)) {
                LogContext.instance().info("服务器请求APP ID合法性检查");
                //TODO 注释掉服务端请求安全校验处理,考虑接入第三方应用
                if (!baseService.isApplicationValid4Server(baseRequest)) {
                    return new RootResponse(ReturnCode.ILLEGAL_REQUEST.getIndex(),
                            "非法应用").convert2Result(keyGroup);
                }
            } else {
                //设备黑名单检查
                if (!method.equals("init")) {
                    LogContext.instance().info("客户端请求设备黑名单检查");
                    String deviceNo = baseService.getDeviceNo4Client(baseRequest);
                    boolean deviceInBlacklist = safeService.isInDeviceBlacklist(deviceNo);
                    if (deviceInBlacklist) {
                        return new RootResponse(ReturnCode.ILLEGAL_REQUEST.getIndex(),
                                "当前设备已被禁用,请联系客服人员").convert2Result(keyGroup);
                    }
                }
                LogContext.instance().info("客户端请求APP ID合法性检查");
                //TODO 注释掉客户端请求安全校验,检查客户端应用第三方接入
                if (!baseService.isApplicationValid4Client(baseRequest)) {
                    return new RootResponse(ReturnCode.ILLEGAL_REQUEST.getIndex(),
                            "应用被禁用").convert2Result(keyGroup);
                }

                //TODO 验证第三方客户端接入系统合法性,后加
//                LogContext.instance().info("客户端OS合法性检查");
//                Os os = baseService.getClientOsByRequest(baseRequest);
//                if (os == null) {
//                    return new RootResponse(ReturnCode.ILLEGAL_REQUEST.getIndex(),
//                            "非法平台").convert2Result(keyGroup);
//                }
            }
            User user = null;
            if (baseService.checkUser()) {
                LogContext.instance().info("用户状态合法性检查");
                user = baseService.getUser(baseRequest);
                String message = baseService.getUserStatusErrorMessage(user);
                if (StringUtils.isNotEmpty(message)) {
                    LogContext.instance().warn("用户状态异常:" + message);
                    return new RootResponse(ReturnCode.ILLEGAL_REQUEST.getIndex(), message)
                            .convert2Result(keyGroup);
                }
                if (!baseService.validateRequestToken(request, user.getId(), baseRequest)) {
                    return new RootResponse(ReturnCode.TOKEN_EXPIRED.getIndex(),
                            "令牌检查失败").convert2Result(keyGroup);
                }
            }
            if (!isClientRequest(safeInfo)) {
                serviceResult = methodService.execute4Server(request, baseRequest, user);
            } else {
                serviceResult = methodService.execute4Client(request, baseRequest, user);
            }
            JsonElement result = serviceResult.getResult();
            ReturnCode returnCode = serviceResult.getReturnCode();
            String errorMessage = serviceResult.getErrorMessage();
            RootResponse rootResponse = new RootResponse(returnCode.getIndex(), result);
            if (StringUtils.isNotEmpty(errorMessage)) {
                LogContext.instance().warn(errorMessage);
                setErrorMessage2Request(request, errorMessage);
                rootResponse.setErrorMessage(errorMessage);
            }
            return rootResponse.convert2Result(keyGroup);
        } catch (Exception e) {
            logContext.error(e, method + "请求错误");
            throw e;
        }
    }

    /**
     * 服务端请求过来的IP白名单功能验证
     */
    private boolean isValidClient(String clientIp, SafeInfo safeInfo) {
        String ip = safeInfo.getIp();
        if (StringUtils.isEmpty(ip)) {
            LogContext.instance().info("服务端请求来源未配置ip白名单");
            return false;
        }
        String[] validIps = ip.split(",");
        for (String str : validIps) {
            if (str.equals(clientIp)) {
                return true;
            }
        }
        LogContext.instance().warn("非法IP来源, 白名单ip=" + ip);
        return false;
    }

    private boolean isClientRequest(SafeInfo safeInfo) {
        return safeInfo.getIsClient() == 1  && !isServerRequest();
    }



}
