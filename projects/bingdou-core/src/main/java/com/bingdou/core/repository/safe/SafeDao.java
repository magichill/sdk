package com.bingdou.core.repository.safe;

import com.bingdou.core.mapper.safe.ISafeMapper;
import com.bingdou.core.model.SafeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class SafeDao {

    @Autowired
    private ISafeMapper safeMapper;

    public SafeInfo getSafeInfoByRequestSource(String requestSourceIndex) {
        return safeMapper.getSafeInfoByRequestSource(requestSourceIndex);
    }

    public int getCountInVipBlackList(int userId) {
        return safeMapper.getCountInVipBlackList(userId);
    }

    public Integer getDeviceLoginErrorCount(String deviceNo, String date) {
        return safeMapper.getDeviceLoginErrorCount(deviceNo, date);
    }

    public Integer getDeviceLoginBlacklistStatus(String deviceNo) {
        return safeMapper.getDeviceLoginBlacklistStatus(deviceNo);
    }

    public void insertDeviceLoginError(String deviceNo, String date) {
        safeMapper.insertDeviceLoginError(deviceNo, date);
    }

    public void insertDeviceLoginBlacklist(String deviceNo, int userId, String osName) {
        safeMapper.insertDeviceLoginBlacklist(deviceNo, userId, osName);
    }

    public int updateDeviceLoginErrorCount(String deviceNo, String date, int count) {
        return safeMapper.updateDeviceLoginErrorCount(deviceNo, date, count);
    }

    public int updateDeviceBlacklistStatus(int status, String deviceNo) {
        return safeMapper.updateDeviceBlacklistStatus(status, deviceNo);
    }
}
