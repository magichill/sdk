package com.bingdou.cdn.controller;

import com.bingdou.cdn.service.RecordCallbackService;
import com.bingdou.core.model.live.CncRecordStartRequest;
import com.bingdou.core.model.live.RecordType;
import com.bingdou.core.service.live.IRecordCallTypeService;
import com.bingdou.core.service.live.RecordCallTypeFactory;
import com.bingdou.core.service.live.RecordTypeCallBackResponse;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "cnc", method = RequestMethod.POST)
    @ResponseBody
    public String cnc_callback(HttpServletRequest request, @RequestBody String cncRecordRequest) {
        boolean isSuccess = false;
        try {
            initLogger();
            String param = new String(Base64.decodeBase64(cncRecordRequest),"UTF-8");
            LogContext.instance().info("网宿录播回调开始");
            request.setAttribute("cncRequest", param);
            getResponse(request,RecordType.CNC);
        } catch (Exception e) {
            LogContext.instance().error(e, "网宿录播回调失败");
        } finally {
            LogContext.instance().info("网宿录播回调回调结果:" + isSuccess);
            LogContext.instance().info("网宿录播回调结束");
        }
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
            LogContext.instance().info("又拍云录播回调回调结果:" + isSuccess);
            LogContext.instance().info("又牌运录播回调结束");
        }
        return isSuccess ? "Y" : "N";
    }

    private RecordTypeCallBackResponse getResponse(HttpServletRequest request, RecordType recordType) {
        IRecordCallTypeService recordCallTypeService = recordCallTypeFactory.getRecordTypeService(recordType);
        recordCallTypeService.dealRecordCallback(request);
        return null;
    }

    private void initLogger() {
        LogContext logContext = LogContext.instance();
        logContext.clear();
        logContext.setRequestUUID(CodecUtils.getRequestUUID());
        logContext.setLoggerName(CommonLoggerNameConstants.RECORD_TYPE_CALL_BACK_LOGGER);
    }

}
