package com.bingdou.core.cache;

import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by gaoshan on 17/3/9.
 */
@Component
public class WeChatJedisCacheManager extends JedisManager implements IWeChatCacheManager {

    private static final String REDIS_WECHAT_TOKEN_NAME = "wechat_token";
    private static final String REDIS_WECHAT_JSAPI_TICKET_NAME = "wechat_jsapi_ticket";

    @Override
    public String getWechatToken() {
        String accessToken = "";
        try {
            accessToken = get(RedisDBConstants.USER_TOKEN_DB_INDEX, REDIS_WECHAT_TOKEN_NAME);
        } catch (Exception e) {
            LogContext.instance().error(e, "获取微信access_token缓存失败");
        }
        return accessToken;
    }

    @Override
    public boolean setWechatToken(String accessToken, int expireTime) {
        if (StringUtils.isEmpty(accessToken)) {
            return false;
        }
        boolean result = false;
        try {
            set(RedisDBConstants.USER_TOKEN_DB_INDEX, REDIS_WECHAT_TOKEN_NAME,
                    accessToken, expireTime);
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "设置微信access_token缓存失败");
        }
        return result;
    }

    @Override
    public String getWechatTicket() {
        String ticket = "";
        try {
            ticket = get(RedisDBConstants.USER_TOKEN_DB_INDEX, REDIS_WECHAT_JSAPI_TICKET_NAME);
        } catch (Exception e) {
            LogContext.instance().error(e, "获取微信ticket缓存失败");
        }
        return ticket;
    }

    @Override
    public boolean setWechatTicket(String ticket, int expireTime) {
        if (StringUtils.isEmpty(ticket)) {
            return false;
        }
        boolean result = false;
        try {
            set(RedisDBConstants.USER_TOKEN_DB_INDEX, REDIS_WECHAT_JSAPI_TICKET_NAME,
                    ticket, expireTime);
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "设置微信ticket缓存失败");
        }
        return result;
    }
}
