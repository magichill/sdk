package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

public interface IBindMapper {

    int updateMobile(@Param("userId") int userId, @Param("mobile") String mobile);

    int updateEmail(@Param("userId") int userId, @Param("email") String email);

    int updateMobileInMemberIndex(@Param("userId") int userId, @Param("mobile") String mobile);

    int updateEmailInMemberIndex(@Param("userId") int userId, @Param("email") String email);

    int updateSafeLevel(@Param("userId") int userId, @Param("safeLevel") int safeLevel);
}
