package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IAliPayNoPwdAuthMapper;
import com.bingdou.core.model.AliPayNoPwdAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AliPayNoPwdAuthDao {

    @Autowired
    private IAliPayNoPwdAuthMapper aliPayNoPwdAuthMapper;

    public int getStatusCountByStatus(int userId, int status) {
        return aliPayNoPwdAuthMapper.getStatusCountByStatus(userId, status);
    }

    public AliPayNoPwdAuth getAuthInfo(int userId) {
        return aliPayNoPwdAuthMapper.getAuthInfo(userId);
    }

    public int updateSignStatus(int userId, int status) {
        return aliPayNoPwdAuthMapper.updateSignStatus(userId, status);
    }

    public void createAuth(int userId, String externalSignNo) {
        aliPayNoPwdAuthMapper.createAuth(userId, externalSignNo);
    }

    public AliPayNoPwdAuth getUserIdByExternalSignNo(String externalSignNo) {
        return aliPayNoPwdAuthMapper.getUserIdByExternalSignNo(externalSignNo);
    }

    public int updateByExternalSignNo(Map<String, String> signMap) {
        return aliPayNoPwdAuthMapper.updateByExternalSignNo(signMap);
    }

    public void insertAuthSignDetail(Map<String, String> signMap) {
        aliPayNoPwdAuthMapper.insertAuthSignDetail(signMap);
    }

}
