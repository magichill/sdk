package com.bingdou.core.constants;

import com.bingdou.tools.LogContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by gaoshan on 17/3/2.
 */
public class ChatConstant {

    public static final String APP_KEY;
    public static final String APP_SECRET;

    static {
        Properties prop = new Properties();
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("rongcloud.properties");
        } catch (IOException e) {
            LogContext.instance().error(e, "初始化融云数据错误");
        }
        APP_KEY = prop.getProperty("rongcloud.app.key");
        APP_SECRET = prop.getProperty("rongcloud.app.secret");
    }
}
