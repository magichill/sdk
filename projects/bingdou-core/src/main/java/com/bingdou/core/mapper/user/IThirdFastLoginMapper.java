package com.bingdou.core.mapper.user;

import com.bingdou.core.model.ThirdFastLogin;
import org.apache.ibatis.annotations.Param;

public interface IThirdFastLoginMapper {

    void insertLoginInfo(ThirdFastLogin thirdFastLogin);

    int getLoginAuthCount(String device);

    Integer getBingDouUserIdByOpenId(@Param("openId") String openId, @Param("openType") int openType);
    Integer getBingDouUserIdByUnionId(@Param("unionId") String unionId, @Param("openType") int openType);

    int updateAuthToken(@Param("openId") String openId, @Param("openType") int openType,
                        @Param("authToken") String authToken);

    void insertLoginAuth(@Param("device") String device, @Param("targetId") String targetId);

    String getTargetId(String device);

    int updateLoginAuth(@Param("device") String device, @Param("targetId") String targetId);

}
