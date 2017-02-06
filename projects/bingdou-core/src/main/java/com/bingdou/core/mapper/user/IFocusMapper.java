package com.bingdou.core.mapper.user;

import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/1/31.
 */
public interface IFocusMapper {

    int getFansCount(@Param("userId") String userId);

    int getFollowerCount(@Param("userId") String userId);

    void insertFocusInfo(@Param("userId") Integer userId, @Param("followId") int followId);
}
