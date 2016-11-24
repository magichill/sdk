package com.bingdou.core.cache;

/**
 * 发送短信和邮件的记录次数缓存
 */
public interface ISendSmsOrEmailCacheManager {

    int getSendCount(String deviceNo);

    boolean updateSendCount(String deviceNo);

}