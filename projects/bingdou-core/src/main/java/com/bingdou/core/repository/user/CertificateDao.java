package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IAnchorCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/1/31.
 */
@Repository
public class CertificateDao {

    @Autowired
    private IAnchorCertificateMapper anchorCertificateMapper;

    public Integer getAnchorStatus(int userId) {
        return anchorCertificateMapper.getAnchorStatus(userId);
    }

    public void insertAnchorCertificate(int userId, int status) {
        anchorCertificateMapper.insertAnchorCertificate(userId, status);
    }
}
