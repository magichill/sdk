package com.bingdou.core.cache;

import com.bingdou.core.service.user.chatroom.model.TokenResult;

/**
 * Created by gaoshan on 17/3/14.
 */
public interface ICloudTokenCacheManager {

    TokenResult getCloudToken(int userId);

    boolean setCloudToken(int userId,TokenResult tokenResult);

}
