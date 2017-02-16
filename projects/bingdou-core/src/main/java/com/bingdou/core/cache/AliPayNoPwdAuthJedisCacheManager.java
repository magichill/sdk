package com.bingdou.core.cache;

import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * ÷ß∏∂±¶√‚√‹ ⁄»®–≈œ¢ª∫¥Ê¿‡
 * Created by gaoshan on 16/12/28.
 */
@Component
public class AliPayNoPwdAuthJedisCacheManager extends JedisManager implements IAliPayNoPwdAuthCacheManager {

    private static final String REDIS_PRE_KEY = "alipaysign:";

    @Override
    public String getAuthInfo(int userId) {
        if (userId <= 0)
            return "";
        String result = "";
        try {
            String key = REDIS_PRE_KEY + userId;
            String statusStr = get(RedisDBConstants.ALI_NO_PWD_AUTH_DB_INDEX, key);
            if (StringUtils.isNotEmpty(statusStr)) {
                result = statusStr;
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "ªÒ»°÷ß∏∂±¶√‚√‹ «∑Ò ⁄»®ª∫¥Ê¥ÌŒÛ");
        }
        return result;
    }

    @Override
    public boolean updateAuthStatus(int userId, int status) {
        if (userId <= 0)
            return false;
        boolean result = false;
        try {
            String key = REDIS_PRE_KEY + userId;
            set(RedisDBConstants.ALI_NO_PWD_AUTH_DB_INDEX, key, status + "", -1);
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "ªÒ»°÷ß∏∂±¶√‚√‹ «∑Ò ⁄»®ª∫¥Ê¥ÌŒÛ");
        }
        return result;
    }

}
