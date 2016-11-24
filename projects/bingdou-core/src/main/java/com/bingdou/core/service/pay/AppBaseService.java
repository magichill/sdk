package com.bingdou.core.service.pay;

import com.bingdou.core.model.Application;
import com.bingdou.core.model.Os;
import com.bingdou.core.repository.pay.AppBaseDao;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 */
@Service
public class AppBaseService {

    @Autowired
    private AppBaseDao appBaseDao;

    public Application getAppByAppId(String appId) {
        if (StringUtils.isEmpty(appId))
            return null;
        return appBaseDao.getAppByAppId(appId);
    }

    public Application checkAppStatus4Login(String appId, String packageName, Os os) {
        Application application = getAppByAppIdAndPackageName(appId, packageName, os);
        return application;
    }


    public Application getAppByPackageNameAndOs(String packageName, Os os) {
        if (StringUtils.isEmpty(packageName) || os == null)
            return null;
        if (Os.ANDROID.equals(os)) {
            return appBaseDao.getAppByPackageName4Android(packageName);
        } else if (Os.IOS.equals(os)) {
            return appBaseDao.getAppByPackageName4Ios(packageName);
        } else {
            return null;
        }
    }

    public boolean changeOwnership(List<Application> relations) {
        int effectRows = appBaseDao.changeOwnership(relations);
        // 会有重复的appid ?
        return effectRows >= relations.size();
    }

    public List<Application> getApps(Os os, String name) {
        if (os == Os.IOS) {
            return appBaseDao.getApps4Ios(name);
        } else if (os == Os.ANDROID) {
            return appBaseDao.getApps4Android(name);
        } else {
            return null;
        }
    }

    public boolean isForceUp(String appId, String packageName, Os os) {
        LogContext.instance().info("强制更新检查逻辑");
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(packageName)
                || os == null)
            return false;
        Integer forceUp = 0;
        if (Os.ANDROID.equals(os)) {
            forceUp = appBaseDao.getForceUp4Android(appId, packageName);
        } else if (Os.IOS.equals(os)) {
            forceUp = appBaseDao.getForceUp4Ios(appId, packageName);
        }
        return forceUp != null && forceUp == 1;
    }

    private Application getAppByAppIdAndPackageName(String appId, String packageName, Os os) {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(packageName) || os == null) {
            return null;
        }
        if (Os.ANDROID.equals(os)) {
            return appBaseDao.getAppByAppIdAndPackageName4Android(appId, packageName);
        } else if (Os.IOS.equals(os)) {
            return appBaseDao.getAppByAppIdAndPackageName4Ios(appId, packageName);
        } else {
            return null;
        }
    }

}
