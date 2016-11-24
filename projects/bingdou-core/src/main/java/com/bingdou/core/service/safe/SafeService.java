package com.bingdou.core.service.safe;

import com.bingdou.core.cache.ISafeCacheManager;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.SafeInfo;
import com.bingdou.core.repository.safe.SafeDao;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by gaoshan on 16-11-1.
 */
@Service
public class SafeService {

    @Autowired
    private SafeDao safeDao;
    @Autowired
    private ISafeCacheManager safeCacheManager;

    public SafeInfo getSafeInfo(String requestSourceIndex) {
        if (StringUtils.isEmpty(requestSourceIndex))
            return null;
        SafeInfo safeInfo = safeCacheManager.getSafeInfo(requestSourceIndex);
        if (safeInfo == null) {
            safeInfo = safeDao.getSafeInfoByRequestSource(requestSourceIndex);
            if (safeInfo != null) {
                safeCacheManager.setSafeInfo(requestSourceIndex, safeInfo);
            }
        }
        return safeInfo;
    }

    public boolean isInVipBlackList(int userId) {
        int count = safeDao.getCountInVipBlackList(userId);
        boolean inVipBlackList = count > 0;
        LogContext.instance().info("userId " + userId + " in vip black list : " + inVipBlackList);
        return inVipBlackList;
    }

    public boolean isInDeviceBlacklist(String deviceNo) {
        Integer status = safeDao.getDeviceLoginBlacklistStatus(deviceNo);
        return status != null && status == UserConstants.DEVICE_LOGIN_BLACK_LIST_YES_STATUS;
    }

    public void dealDeviceLoginError(String deviceNo, int userId, Os os) {
        LogContext.instance().info("处理设备黑名单逻辑");
        if (StringUtils.isEmpty(deviceNo) || os == null) {
            LogContext.instance().error("参数错误");
            return;
        }
        String date = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        Integer deviceLoginErrorCount = safeDao.getDeviceLoginErrorCount(deviceNo, date);
        if (deviceLoginErrorCount == null) {
            LogContext.instance().info("没有登录错误记录,插入");
            safeDao.insertDeviceLoginError(deviceNo, date);
        } else {
            LogContext.instance().info("有登录错误记录,修改");
            int newCount = deviceLoginErrorCount + 1;
            int updateDeviceLoginErrorCountResult = safeDao.updateDeviceLoginErrorCount(deviceNo, date, newCount);
            if (updateDeviceLoginErrorCountResult < 1) {
                LogContext.instance().error("更新设备登录错误次数失败");
            }
            if (newCount >= UserConstants.DEVICE_LOGIN_ERROR_COUNT_EVERY_DAY) {
                LogContext.instance().info("大于最大登录错误次数,加入黑名单");
                Integer status = safeDao.getDeviceLoginBlacklistStatus(deviceNo);
                if (status == null) {
                    safeDao.insertDeviceLoginBlacklist(deviceNo, userId, os.getName());
                } else {
                    if (status == UserConstants.DEVICE_LOGIN_BLACK_LIST_NO_STATUS) {
                        int updateDeviceBlacklistStatusResult = safeDao.updateDeviceBlacklistStatus(UserConstants.DEVICE_LOGIN_BLACK_LIST_YES_STATUS,
                                deviceNo);
                        if (updateDeviceBlacklistStatusResult < 1) {
                            LogContext.instance().error("更新设备登录黑名单失败");
                        }
                    }
                }
            }
        }
    }
}
