package com.bingdou.payserver.util;

import com.bingdou.tools.LogContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * ֧������property������
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
            LogContext.instance().error(e, "��ȡ֧�����ĳ��������");
        }
        APPLE_INNER_PAY_URL = prop.getProperty("apple.inner.pay.url");
    }
}