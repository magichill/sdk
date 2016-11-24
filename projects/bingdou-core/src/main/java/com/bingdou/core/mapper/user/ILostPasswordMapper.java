package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

public interface ILostPasswordMapper {

    int updatePassword(@Param("userId") int userId,
                       @Param("encodedPassword") String encodedPassword,
                       @Param("salt") String salt);

}
