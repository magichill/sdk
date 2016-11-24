package com.bingdou.core.cache;


import com.bingdou.core.model.UserToken;

/**
 *
 */
public interface IUserCacheManager {

    UserToken getUserToken(int userId);

    String getUserValidateToken(int userId);

    boolean setUserToken(int userId, String userToken, String device, String requestSource);

    boolean setUserValidateToken(int userId, String userValidateToken);

    boolean deleteUserToken(int userId);

    boolean deleteUserValidateToken(int userId);
}
