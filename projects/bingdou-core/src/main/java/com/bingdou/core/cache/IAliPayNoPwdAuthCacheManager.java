package com.bingdou.core.cache;

/**
 * 支付宝免密授权信息缓存类
 * Created by gaoshan on 16/12/28.
 */
public interface IAliPayNoPwdAuthCacheManager {

    String getAuthInfo(int userId);

    boolean updateAuthStatus(int userId, int status);

}
