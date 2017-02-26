package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IThirdFastLoginMapper;
import com.bingdou.core.model.ThirdFastLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ThirdFastLoginDao {

    @Autowired
    private IThirdFastLoginMapper thirdFastLoginMapper;

    public int getLoginAuthCount(String device) {
        return thirdFastLoginMapper.getLoginAuthCount(device);
    }

    public String getTargetId(String device) {
        return thirdFastLoginMapper.getTargetId(device);
    }

    public void insertLoginAuth(String device, String targetId) {
        thirdFastLoginMapper.insertLoginAuth(device, targetId);
    }

    public void insertLoginInfo(ThirdFastLogin thirdFastLogin) {
        thirdFastLoginMapper.insertLoginInfo(thirdFastLogin);
    }

    public int updateAuthToken(String openId, int openType, String authToken) {
        return thirdFastLoginMapper.updateAuthToken(openId, openType, authToken);
    }

    public Integer getBingDouUserIdByOpenInfo(String openId,int openType) {
        return thirdFastLoginMapper.getBingDouUserIdByOpenId(openId,openType);
    }

    public int updateLoginAuth(String device, String targetId) {
        return thirdFastLoginMapper.updateLoginAuth(device, targetId);
    }

}
