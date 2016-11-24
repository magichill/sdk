package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IAppBaseMapper;
import com.bingdou.core.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *
 */
@Repository
public class AppBaseDao {

    @Autowired
    private IAppBaseMapper appBaseMapper;

    public Application getAppByAppId(String appId) {
        return appBaseMapper.getAppByAppId(appId);
    }

    public Application getAppByAppIdAndPackageName4Android(String appId, String packageName) {
        return appBaseMapper.getAppByAppIdAndPackageName4Android(appId, packageName);
    }

    public Application getAppByAppIdAndPackageName4Ios(String appId, String packageName) {
        return appBaseMapper.getAppByAppIdAndPackageName4Ios(appId, packageName);
    }

    public Application getAppByPackageName4Android(String packageName) {
        return appBaseMapper.getAppByPackageName4Android(packageName);
    }

    public Application getAppByPackageName4Ios(String packageName) {
        return appBaseMapper.getAppByPackageName4Ios(packageName);
    }

    public int changeOwnership(List<Application> relations) {
        return appBaseMapper.changeOwnership(relations);
    }

    public List<Application> getApps4Android(String name) {
        return appBaseMapper.getApps4Android(name);
    }

    public List<Application> getApps4Ios(String name) {
        return appBaseMapper.getApps4Ios(name);
    }

    public Integer getForceUp4Android(String appId, String packageName) {
        return appBaseMapper.getForceUp4Android(appId, packageName);
    }

    public Integer getForceUp4Ios(String appId, String packageName) {
        return appBaseMapper.getForceUp4Ios(appId, packageName);
    }

}
