package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 16-11-24.
 */
public interface IDeviceMapper {

    int getAndroidDeviceCount(@Param("imei") String imei, @Param("mac") String mac,
                              @Param("androidId") String androidId,
                              @Param("androidSn") String androidSn);

    int getIosDeviceCountByIdfa(@Param("idfa") String idfa);

    void insertAndroidDevice(@Param("mac") String mac, @Param("imei") String imei,
                             @Param("androidId") String androidId,
                             @Param("androidSn") String androidSn);

    void insertIosDevice(@Param("idfa") String idfa);
}
