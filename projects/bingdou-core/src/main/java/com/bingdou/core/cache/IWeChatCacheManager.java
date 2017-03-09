package com.bingdou.core.cache;

/**
 * Created by gaoshan on 17/3/9.
 */
public interface IWeChatCacheManager {

    String getWechatToken();

    boolean setWechatToken(String accessToken,int expireTime);

    String getWechatTicket();

    boolean setWechatTicket(String ticket,int expireTime);
}
