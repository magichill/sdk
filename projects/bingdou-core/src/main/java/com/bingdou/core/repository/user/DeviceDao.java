package com.bingdou.core.repository.user;

import com.bingdou.core.helper.DeviceInfo;
import com.bingdou.core.mapper.user.IDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 16-11-24.
 */
@Repository
public class DeviceDao {

    @Autowired
    private IDeviceMapper deviceMapper;

    public int getAndroidDeviceCount(DeviceInfo deviceInfo) {
        return deviceMapper.getAndroidDeviceCount(deviceInfo.getImei(), deviceInfo.getMac(),
                deviceInfo.getAndroidInfo().getAndroidId(),
                deviceInfo.getAndroidInfo().getAndroidSerialNumber());
    }

    public int getIosDeviceCountByIdfa(String idfa) {
        return deviceMapper.getIosDeviceCountByIdfa(idfa);
    }

    public void insertAndroidDevice(DeviceInfo deviceInfo) {
        deviceMapper.insertAndroidDevice(deviceInfo.getMac(), deviceInfo.getImei(),
                deviceInfo.getAndroidInfo().getAndroidId(),
                deviceInfo.getAndroidInfo().getAndroidSerialNumber());
    }

    public void insertIosDevice(DeviceInfo.IosInfo iosInfo) {
        deviceMapper.insertIosDevice(iosInfo.getIdfa());
    }
}
