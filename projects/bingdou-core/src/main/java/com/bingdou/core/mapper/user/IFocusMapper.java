package com.bingdou.core.mapper.user;

import com.bingdou.core.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 17/1/31.
 */
public interface IFocusMapper {

    int getFansCount(@Param("userId") int userId);

    int getFollowerCount(@Param("userId") int userId);

    Integer checkFocusInfoStatus(@Param("userId") Integer userId, @Param("followId") int followId);

    void insertFocusInfo(@Param("userId") Integer userId, @Param("followId") int followId);

    void updateFocusInfo(@Param("userId") Integer userId, @Param("followId") int followId,@Param("status") int status);

    /**
     * 获取用户粉丝列表
     * @param userId
     * @return
     */
    List<User> getFollower(@Param("userId") Integer userId,@Param("start") int start,@Param("limit") int limit);

    /**
     * 获取用户关注列表
     * @param userId
     * @return
     */
    List<User> getFollowing(@Param("userId") Integer userId,@Param("start") int start,@Param("limit") int limit);
}
