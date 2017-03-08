package com.bingdou.payserver.aop;

import com.bingdou.tools.RecordLogger;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录请求时间
 */
@SuppressWarnings("Duplicates")
public class TimerCalculator {

    public Object calculateTime(ProceedingJoinPoint pjp) throws Throwable {
        Object object = null;
        String methodName = null;
        String method = null;
        long startTime = System.currentTimeMillis();
        try {
            Signature signature = pjp.getSignature();
            Object[] args = pjp.getArgs();
            String requestMethod = "";
            if (args != null && args.length > 0) {
                HttpServletRequest request = (HttpServletRequest) args[0];
                requestMethod = request.getParameter("method");
            }
            if (signature != null) {
                methodName = signature.getName();
                object = pjp.proceed();
            }
            if (StringUtils.isEmpty(requestMethod)) {
                method = methodName;
            } else {
                if (requestMethod.contains("_"))
                    method = methodName + "/payserver-" + requestMethod.replaceAll("_", "-");
                else
                    method = methodName + "/payserver-" + requestMethod;
            }
        } catch (Throwable e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long runningTime = endTime - startTime;
            RecordLogger.timeLog(method, runningTime);
        }
        return object;
    }

}
