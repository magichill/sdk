package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

public interface ILoginMapper {

    void clearLoginError(@Param("userId") Integer userId);

    void setLoginError(@Param("userId") Integer userId, @Param("errorCount") Integer errorCount);

    void setLastLoginInfo(@Param("userId") Integer userId, @Param("ip") String ip,
                          @Param("uid") String oldUid, @Param("ua") String oldUa);
}
