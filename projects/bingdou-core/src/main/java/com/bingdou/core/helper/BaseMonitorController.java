package com.bingdou.core.helper;

import com.bingdou.core.service.safe.MonitorService;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-24.
 */
public class BaseMonitorController {

    @Autowired
    protected MonitorService monitorService;

    protected String monitor(HttpServletRequest request, boolean isRefreshClientLog) {
        initLogger();
        boolean redisIsConn = monitorService.monitorRedis();
        boolean userDBIsConn = monitorService.monitorUserDB();
        boolean payDBIsConn = monitorService.monitorPayDB();
        boolean liveDBIsConn = monitorService.monitorLiveDB();
        String redisIsConnStr = redisIsConn ? "SUCCESS" : "ERROR";
        String userDBIsConnStr = userDBIsConn ? "SUCCESS" : "ERROR";
        String payDBIsConnStr = payDBIsConn ? "SUCCESS" : "ERROR";
        String liveDBIsConnStr = liveDBIsConn ? "SUCCESS" : "ERROR";
        String result = redisIsConn && userDBIsConn && payDBIsConn ? "SUCCESS" : "ERROR";
        request.setAttribute("redisIsConnStr", redisIsConnStr);
        request.setAttribute("userDBIsConnStr", userDBIsConnStr);
        request.setAttribute("payDBIsConnStr", payDBIsConnStr);
        request.setAttribute("liveDBIsConnStr", liveDBIsConnStr);
        request.setAttribute("result", result);
        return "monitor";
    }

    private void initLogger() {
        LogContext logContext = LogContext.instance();
        logContext.clear();
        logContext.setRequestUUID(CodecUtils.getRequestUUID());
        logContext.setLoggerName(CommonLoggerNameConstants.MONITOR_LOGGER);
    }
}
