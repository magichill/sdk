package com.bingdou.tools;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getClientIp(HttpServletRequest request) {
        String ip = "";
        try {
            ip = request.getHeader("x-forwarded-for");
            LogContext.instance().info("x-forwarded-for:" + ip);
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                LogContext.instance().info("Proxy-Client-IP:" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                LogContext.instance().info("WL-Proxy-Client-IP:" + ip);
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                LogContext.instance().info("Remote-Addr:" + ip);
            }
            if (StringUtils.isNotEmpty(ip)) {
                String[] ipArray = ip.split(",");
                if (ipArray.length > 1) {
                    ip = "unknown".equals(ipArray[0].trim()) ? ipArray[1].trim() : ipArray[0].trim();
                }
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "获取IP失败");
        }
        return ip;
    }

}
