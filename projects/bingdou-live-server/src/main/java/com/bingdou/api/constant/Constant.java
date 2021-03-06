package com.bingdou.api.constant;

import com.bingdou.tools.LogContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by gaoshan on 17/1/9.
 */
public class Constant {


    public static final String APP_KEY;
    public static final String APP_SECRET;
    public static final String BINGDOU_DOMAIN;

    static {
        Properties prop = new Properties();
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("live.properties");
        } catch (IOException e) {
            LogContext.instance().error(e, "初始化直播数据错误");
        }
        APP_KEY = prop.getProperty("chat.app.key");
        APP_SECRET = prop.getProperty("chat.app.secret");
        BINGDOU_DOMAIN = prop.getProperty("bingdou.domain");

    }

}
