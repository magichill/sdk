package com.bingdou.core.cache;

/**
 * ֧����������Ȩ��Ϣ������
 * Created by gaoshan on 16/12/28.
 */
public interface IAliPayNoPwdAuthCacheManager {

    String getAuthInfo(int userId);

    boolean updateAuthStatus(int userId, int status);

}
