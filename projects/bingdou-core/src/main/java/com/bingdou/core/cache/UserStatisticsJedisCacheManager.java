package com.bingdou.core.cache;

import com.bingdou.core.constants.RedisDBConstants;
import com.bingdou.tools.LogContext;
import org.springframework.stereotype.Component;

/**
 * Created by gaoshan on 16/12/17.
 */
@Component
public class UserStatisticsJedisCacheManager extends JedisManager implements IUserStatisticsCacheManager {

    @Override
    public String getMemberBelong(int applicationId, int userId) {
        if (userId <= 0 || applicationId <= 0) {
            return "";
        }
        String redisKey = "aid:" + applicationId + "mid:" + userId;
        String amid = "";
        try {
            amid = get(RedisDBConstants.USER_STATISTICS_DB_INDEX, redisKey);
        } catch (Exception e) {
            LogContext.instance().error(e, "获取用户注册应用历史失败");
        }
        return amid;
    }

    @Override
    public boolean setMemberBelong(int applicationId, int userId, int appMemberId) {
        if (userId <= 0 || applicationId <= 0 || appMemberId <= 0) {
            return false;
        }
        String redisKey = "aid:" + applicationId + "mid:" + userId;
        boolean result = false;
        try {
            set(RedisDBConstants.USER_STATISTICS_DB_INDEX, redisKey, "amid:" + appMemberId, -1);
            result = true;
        } catch (Exception e) {
            LogContext.instance().error(e, "设置用户注册应用历史失败");
        }
        return result;
    }

}
