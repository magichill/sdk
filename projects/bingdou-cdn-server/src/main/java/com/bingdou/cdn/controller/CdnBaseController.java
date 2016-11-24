package com.bingdou.cdn.controller;

import com.bingdou.cdn.constant.CdnType;
import com.bingdou.cdn.service.CdnServiceFactory;
import com.bingdou.cdn.service.ICdnService;
import com.bingdou.core.cache.ICdnConfigManager;
import com.bingdou.core.constants.Constants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ReturnCode;
import com.bingdou.core.helper.RootResponse;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.SafeInfo;
import com.bingdou.core.service.BaseService;
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
 * Created by gaoshan on 16-11-14.
 */
public abstract class CdnBaseController {

    @Autowired
    protected SafeService safeService;

    @Autowired
    private CdnServiceFactory cdnServiceFactory;

    @Autowired
    private ICdnConfigManager cdnConfigManager;

    protected void setErrorMessage2Request(HttpServletRequest request, String errorMessage) {
        request.setAttribute(Constants.REQUEST_ERROR_MESSAGE_NAME, errorMessage);
    }

    protected void setKeyGroup2Request(HttpServletRequest request, KeyGroup keyGroup) {
        request.setAttribute(Constants.REQUEST_KEY_GROUP_NAME, keyGroup);
    }

    protected void setSafeInfo2Request(HttpServletRequest request, SafeInfo safeInfo) {
        request.setAttribute(Constants.REQUEST_SAFE_INFO_NAME, safeInfo);
    }

    protected String baseDispatch(HttpServletRequest request, String requestParamLoggerName)
            throws Exception {
        LogContext logContext = LogContext.instance();
        logContext.clear();
        logContext.setRequestUUID(CodecUtils.getRequestUUID());
        logContext.setLoggerName(requestParamLoggerName);

        //TODO 需查询mysql
        String method = cdnConfigManager.getCdnConfig();
        if(StringUtils.isBlank(method)){
            method = CdnType.UPYUN.getName();
            cdnConfigManager.setCdnConfig(method);
        }
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
            if (!cdnServiceFactory.getCdnServiceMap().containsKey(method)) {
                setErrorMessage2Request(request, "非法请求方法");
                throw new Exception("非法请求方法");
            }
            KeyGroup keyGroup = KeyGroup.valueOf(safeInfo.getKeyGroup());
            setKeyGroup2Request(request, keyGroup);
            setSafeInfo2Request(request, safeInfo);

            ICdnService cdnService = cdnServiceFactory.getCdnServiceMap().get(method);
            BaseService baseService = (BaseService) cdnService;
            BaseRequest baseRequest = baseService.getBaseRequest(request);
            ServiceResult serviceResult = cdnService.createCdnLive(baseRequest);
            JsonElement result = serviceResult.getResult();
            ReturnCode returnCode = serviceResult.getReturnCode();
            String errorMessage = serviceResult.getErrorMessage();
            RootResponse rootResponse = new RootResponse(returnCode.getIndex(), result);
            if (StringUtils.isNotEmpty(errorMessage)) {
                LogContext.instance().warn(errorMessage);
                setErrorMessage2Request(request, errorMessage);
                rootResponse.setErrorMessage(errorMessage);
            }
            return rootResponse.convert2Result(KeyGroup.DEFAULT);
        }catch (Exception e) {
            logContext.error(e, method + "请求错误");
            throw e;
        }


    }
}
