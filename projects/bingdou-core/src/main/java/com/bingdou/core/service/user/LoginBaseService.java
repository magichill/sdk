package com.bingdou.core.service.user;

import com.bingdou.core.repository.user.LoginDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginBaseService {

    @Autowired
    private LoginDao loginDao;

    public void clearLoginError(int userId) {
        LogContext.instance().info("登录错误次数清零");
        if (userId <= 0)
            return;
        loginDao.clearLoginError(userId);
    }

    public void setLoginError(int userId, int errorCount) {
        LogContext.instance().info("更新登录错误信息");
        if (userId <= 0)
            return;
        loginDao.setLoginError(userId, errorCount);
    }

    public void setLastLoginInfo(int userId, String ip, String oldUid, String oldUa) {
        LogContext.instance().info("更新最后登录信息");
        if (userId <= 0)
            return;
        loginDao.setLastLoginInfo(userId, ip, oldUid, oldUa);
    }
}
