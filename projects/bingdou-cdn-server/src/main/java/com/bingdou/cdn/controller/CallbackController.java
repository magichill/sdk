package com.bingdou.cdn.controller;

import com.bingdou.core.model.live.RecordType;
import com.bingdou.core.service.live.IRecordCallTypeService;
import com.bingdou.core.service.live.RecordCallTypeFactory;
import com.bingdou.core.service.live.RecordCallbackService;
import com.bingdou.core.service.live.RecordTypeCallBackResponse;
import com.bingdou.core.service.live.callback.CncLiveStatusRequest;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaoshan on 16-11-24.
 */
@RequestMapping("callback")
@Controller
public class CallbackController {

    @Autowired
    private RecordCallTypeFactory recordCallTypeFactory;

    @Autowired
    private RecordCallbackService recordCallbackService;

    @RequestMapping(value = "live_start", method = RequestMethod.GET)
    @ResponseBody
    public String live_start_callback(HttpServletRequest request, HttpServletResponse response) {
        boolean isSuccess = false;
        try {
            initLogger();
            CncLiveStatusRequest liveStatusRequest = getCncLiveStatusParam(request);
            LogContext.instance().info("网宿开启推流回调开始");
            if(liveStatusRequest != null) {
                LogContext.instance().info("网宿开启推流回调参数：" + liveStatusRequest.toString());
                if(recordCallbackService.checkLiveExist(liveStatusRequest.getStreamName())){
                    isSuccess = recordCallbackService.notifyAppServer(liveStatusRequest.getStreamName(),true,"");
                }

            }
        } catch (Exception e) {
            LogContext.instance().error(e, "网宿开启推流回调失败");
        } finally {
            LogContext.instance().info("网宿开启推流回调结果:" + isSuccess);
            LogContext.instance().info("网宿开启推流回调结束");
        }

        return isSuccess ? "1" : "0";
    }

    @RequestMapping(value = "live_end", method = RequestMethod.GET)
    @ResponseBody
    public String live_end_callback(HttpServletRequest request, HttpServletResponse response) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("网宿关闭推流回调开始");
            CncLiveStatusRequest liveStatusRequest = getCncLiveStatusParam(request);
            if(liveStatusRequest != null) {
                LogContext.instance().info("网宿关闭推流回调参数：" + liveStatusRequest.toString());
                if(recordCallbackService.checkLiveExist(liveStatusRequest.getStreamName())){
                    isSuccess = recordCallbackService.notifyAppServer(liveStatusRequest.getStreamName(),false,"");
                }
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "网宿关闭推流回调失败");
        } finally {
            LogContext.instance().info("网宿关闭推流回调结果:" + isSuccess);
            LogContext.instance().info("网宿关闭推流回调结束");
        }

        return isSuccess ? "1" : "0";
    }


    @RequestMapping(value = "cnc", method = RequestMethod.POST)
    @ResponseBody
    public String cnc_callback(HttpServletRequest request, HttpServletResponse response, @RequestBody String cncRecordRequest) {
        boolean isSuccess = false;
        try {
            initLogger();
            String param = new String(Base64.decodeBase64(cncRecordRequest),"UTF-8");
            LogContext.instance().info("网宿录播回调开始");
            request.setAttribute("cncRequest", param);
            isSuccess = getResponse(request,RecordType.CNC);
        } catch (Exception e) {
            LogContext.instance().error(e, "网宿录播回调失败");
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } finally {
            LogContext.instance().info("网宿录播回调结果:" + isSuccess);
            LogContext.instance().info("网宿录播回调结束");
        }
        if(!isSuccess)
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        return isSuccess ? "Y" : "N";
    }

    @RequestMapping(value = "cc", method = RequestMethod.POST)
    @ResponseBody
    public String cc_callback(HttpServletRequest request, HttpServletResponse response, @RequestBody String ccRecordRequest) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("CC回调开始");
            request.setAttribute("ccRequest", ccRecordRequest);
            isSuccess = getResponse(request,RecordType.CC);
        } catch (Exception e) {
            LogContext.instance().error(e, "CC回调失败");
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        } finally {
            LogContext.instance().info("CC回调结果:" + isSuccess);
            LogContext.instance().info("CC回调结束");
        }
        if(!isSuccess)
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        return isSuccess ? "Y" : "N";
    }

    @RequestMapping(value = "upyun", method = RequestMethod.POST)
    @ResponseBody
    public String upyun_callback(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("又拍云录播回调开始");

        } catch (Exception e) {
            LogContext.instance().error(e, "又拍云录播回调失败");
        } finally {
            LogContext.instance().info("又拍云录播回调结果:" + isSuccess);
            LogContext.instance().info("又拍云录播回调结束");
        }
        return isSuccess ? "Y" : "N";
    }

    private boolean getResponse(HttpServletRequest request, RecordType recordType) {
        IRecordCallTypeService recordCallTypeService = recordCallTypeFactory.getRecordTypeService(recordType);
        return recordCallTypeService.dealRecordCallback(request);
    }

    private CncLiveStatusRequest getCncLiveStatusParam(HttpServletRequest request){
        CncLiveStatusRequest cncLiveStatusRequest = new CncLiveStatusRequest();
        String ip = request.getParameter("ip");
        String id = request.getParameter("id");
        String node = request.getParameter("node");
        String app = request.getParameter("app");
        String appName = request.getParameter("appName");
        if(StringUtils.isBlank(id)){
            LogContext.instance().warn("回调参数流名称为空");
            return null;
        }
        cncLiveStatusRequest.setIp(ip);
        cncLiveStatusRequest.setStreamName(id);
        cncLiveStatusRequest.setNode(node);
        cncLiveStatusRequest.setApp(app);
        cncLiveStatusRequest.setAppName(appName);
        return cncLiveStatusRequest;
    }
    private void initLogger() {
        LogContext logContext = LogContext.instance();
        logContext.clear();
        logContext.setRequestUUID(CodecUtils.getRequestUUID());
        logContext.setLoggerName(CommonLoggerNameConstants.RECORD_TYPE_CALL_BACK_LOGGER);
    }

}
