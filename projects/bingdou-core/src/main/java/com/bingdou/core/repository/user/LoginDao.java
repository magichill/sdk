package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.ILoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

    @Autowired
    private ILoginMapper loginMapper;

    public void clearLoginError(int userId) {
        loginMapper.clearLoginError(userId);
    }

    public void setLoginError(int userId, int errorCount) {
        loginMapper.setLoginError(userId, errorCount);
    }

    public void setLastLoginInfo(int userId, String ip, String oldUid, String oldUa) {
        loginMapper.setLastLoginInfo(userId, ip, oldUid, oldUa);
    }
}
