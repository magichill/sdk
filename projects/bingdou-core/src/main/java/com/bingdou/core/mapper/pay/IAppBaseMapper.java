package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.Application;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAppBaseMapper {

    Application getAppByAppId(String appId);

    Application getAppByAppIdAndPackageName4Android(@Param("appId") String appId,
                                                    @Param("packageName") String packageName);

    Application getAppByAppIdAndPackageName4Ios(@Param("appId") String appId,
                                                @Param("packageName") String packageName);

    int changeOwnership(List<Application> relations);

    List<Application> getApps4Android(@Param("name") String name);

    List<Application> getApps4Ios(@Param("name") String name);

    Application getAppByPackageName4Android(String packageName);

    Application getAppByPackageName4Ios(String packageName);

    Integer getForceUp4Android(@Param("appId") String appId,
                               @Param("packageName") String packageName);

    Integer getForceUp4Ios(@Param("appId") String appId,
                           @Param("packageName") String packageName);

    Integer getForceUpFromAibei(String packageName);
}
