package com.bingdou.core.constants;

import com.bingdou.tools.LogContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by gaoshan on 17/6/5.
 */
public class SendSmsConstant {

    /**
     * 短信通道后缀(签名)
     */
    public static final String SMS_SIGN;

    /**
     * luosimao发送短信接口URL
     */
    public static final String LUOSIMAO_SEND_SMS_URL;

    /**
     * luosimao auth key
     */
//    private static final String LUOSIMAO_AUTH_KEY = "key-5c1af9d7296729b730735d792bb4625";
    public static final String LUOSIMAO_AUTH_KEY;

    static {
        Properties prop = new Properties();
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("sms.properties");
        } catch (IOException e) {
            LogContext.instance().error(e, "初始化直播数据错误");
        }
        SMS_SIGN = prop.getProperty("sms.sign");
        LUOSIMAO_SEND_SMS_URL = prop.getProperty("sms.url");
        LUOSIMAO_AUTH_KEY = prop.getProperty("sms.auth.key");
    }
}
