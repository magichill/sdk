package com.bingdou.core.cache;

import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.core.model.SafeInfo;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class SafeJedisCacheManager extends JedisManager implements ISafeCacheManager {

    @Override
    public SafeInfo getSafeInfo(String requestSourceIndex) {
        if (StringUtils.isEmpty(requestSourceIndex))
            return null;
        SafeInfo safeInfo = null;
        try {
            Map<String, String> values = hgetAll(RedisDBConstants.SAFE_DB_INDEX, requestSourceIndex);
            if (values != null && !values.isEmpty()) {
                safeInfo = new SafeInfo();
                safeInfo.setRequestSource(requestSourceIndex);
                safeInfo.setIp(values.get("ip"));
                safeInfo.setKeyGroup(values.get("key_group"));
                safeInfo.setMethodName(values.get("method_name"));
                safeInfo.setValidTokenOnlyUserIdRs(values.get("valid_token_only_mid_rs"));
                String isClientStr = values.get("is_client");
                safeInfo.setIsClient(Integer.parseInt(isClientStr));
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "获取安全加固信息缓存失败");
        }
        return safeInfo;
    }

    @Override
    public boolean setSafeInfo(String requestSourceIndex, SafeInfo safeInfo) {
        if (StringUtils.isEmpty(requestSourceIndex) || safeInfo == null) {
            return false;
        }
        boolean result = false;
        try {
            Map<String, String> values = new HashMap<String, String>();
            values.put("ip", safeInfo.getIp());
            values.put("key_group", safeInfo.getKeyGroup());
            values.put("method_name", safeInfo.getMethodName());
            values.put("is_client", String.valueOf(safeInfo.getIsClient()));
            values.put("valid_token_only_mid_rs", safeInfo.getValidTokenOnlyUserIdRs());
            hmset(RedisDBConstants.SAFE_DB_INDEX, requestSourceIndex, values, -1);
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "设置安全加固信息缓存失败");
        }
        return result;
    }

}
