package com.bingdou.core.cache;

import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by gaoshan on 16-11-18.
 */
@Component
public class CdnConfigManager extends JedisManager implements ICdnConfigManager {

    private static final String REDIS_CDN_CONFIG = "cdn_config";

    @Override
    public String getCdnConfig() {
        String cdnServer = "";
        try {
            cdnServer = get(RedisDBConstants.SAFE_DB_INDEX,REDIS_CDN_CONFIG);
        } catch (Exception e) {
            LogContext.instance().error(e, "获取CDN服务信息缓存失败");
        }
        return cdnServer;
    }

    @Override
    public boolean setCdnConfig(String value) {
        boolean result = false;
        if(StringUtils.isNotEmpty(value)){
            try {
                set(RedisDBConstants.SAFE_DB_INDEX, REDIS_CDN_CONFIG, value, UserConstants.USER_VALIDATE_TOKEN_EXPIRE_SECONDS);
                result = true;
            }catch (Exception e){
                LogContext.instance().error(e, "设置cdn服务缓存失败");
            }
        }
        return result;
    }
}
