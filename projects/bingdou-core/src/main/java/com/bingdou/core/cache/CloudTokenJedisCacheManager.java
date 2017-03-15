package com.bingdou.core.cache;


import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.core.service.user.chatroom.model.TokenResult;
import com.bingdou.tools.LogContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by gaoshan on 17/3/14.
 */
@Component
public class CloudTokenJedisCacheManager  extends JedisManager implements ICloudTokenCacheManager {

    private static final String REDIS_USER_CLOUD_TOKEN_NAME = "cloud_token";

    @Override
    public TokenResult getCloudToken(int userId) {
        if (userId <= 0) {
            return null;
        }
        String tokenKey = REDIS_USER_CLOUD_TOKEN_NAME + ":" + userId;
        TokenResult cloudToken = null;
        try {

            Map<String, String> map = hgetAll(RedisDBConstants.USER_CLOUD_TOKEN_INDEX, tokenKey);
            if (map != null && !map.isEmpty()) {
                cloudToken = new TokenResult();
                cloudToken.setUserId(map.get("userId"));
                cloudToken.setToken(map.get("token"));
            }

        } catch (Exception e) {
            LogContext.instance().error(e, "从缓存中获取用户融云token失败");
        }
        return cloudToken;
    }

    @Override
    public boolean setCloudToken(int userId,TokenResult tokenResult) {
        if (userId <= 0 || tokenResult == null) {
            return false;
        }
        String tokenKey = REDIS_USER_CLOUD_TOKEN_NAME + ":" + userId;
        boolean result = false;
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("token", tokenResult.getToken());
            map.put("userId", tokenResult.getUserId());
            hmset(RedisDBConstants.USER_CLOUD_TOKEN_INDEX, tokenKey,
                    map, -1);
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "设置用户融云token缓存失败");
        }
        return result;
    }
}
