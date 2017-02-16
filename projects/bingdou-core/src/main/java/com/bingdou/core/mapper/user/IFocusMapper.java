package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/1/31.
 */
public interface IFocusMapper {

    int getFansCount(@Param("userId") int userId);

    int getFollowerCount(@Param("userId") int userId);

    int checkFocusInfo(@Param("userId") Integer userId, @Param("followId") int followId);

    void insertFocusInfo(@Param("userId") Integer userId, @Param("followId") int followId);

    void updateFocusInfo(@Param("userId") Integer userId, @Param("followId") int followId,@Param("status") int status);
}
