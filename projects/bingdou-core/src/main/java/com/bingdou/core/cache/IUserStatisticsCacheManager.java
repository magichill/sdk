package com.bingdou.core.cache;

/**
 * Created by gaoshan on 16/12/15.
 */
public interface IUserStatisticsCacheManager {

    String getMemberBelong(int applicationId, int userId);

    boolean setMemberBelong(int applicationId, int userId, int appMemberId);

}
