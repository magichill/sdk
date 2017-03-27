package com.bingdou.payserver.utils;

import com.bingdou.tools.LogContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * 支付中心property常量类
 */
public class PayServerProperties {

    public static final String APPLE_INNER_PAY_URL;

    private PayServerProperties() {
    }

    static {
        Properties prop = new Properties();
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("pay-server.properties");
        } catch (Exception e) {
            LogContext.instance().error(e, "读取支付中心常量类错误");
        }
        APPLE_INNER_PAY_URL = prop.getProperty("apple.inner.pay.url");
    }
}