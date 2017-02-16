package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/1/31.
 */
public interface IAnchorCertificateMapper {

    int getAnchorStatus(@Param("userId") Integer userId);

    void insertAnchorCertificate(@Param("userId") Integer userId, @Param("status") int status);
}
