package com.bingdou.core.cache;

import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发送短信和邮件的记录次数缓存
 */
@Component
public class SendSmsOrEmailJedisCacheManager extends JedisManager implements ISendSmsOrEmailCacheManager {

    private static final String PREFIX = "device_no:";

    @Override
    public int getSendCount(String deviceNo) {
        int result = 0;
        try {
            String key = PREFIX + deviceNo;
            String value = get(RedisDBConstants.SEND_SMS_OR_EMAIL_DB_INDEX, key);
            if (StringUtils.isNotEmpty(value)) {
                result = Integer.parseInt(value);
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "获取发送短信或者邮件缓存错误");
        }
        return result;
    }

    @Override
    public boolean updateSendCount(String deviceNo) {
        boolean result = false;
        try {
            String key = PREFIX + deviceNo;
            Date today = DateUtil.parse(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD),
                    DateUtil.YYYY_MM_DD);
            Date tom = DateUtils.addDays(today, 1);
            long unixTime = tom.getTime() / 1000;
            Long seconds = unixTime - DateUtil.getCurrentTimeSeconds();
            incr(RedisDBConstants.SEND_SMS_OR_EMAIL_DB_INDEX, key, seconds.intValue());
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "更新发送短信或者邮件缓存错误");
        }
        return result;
    }
}
