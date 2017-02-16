package com.bingdou.core.mapper.user;

import com.bingdou.core.model.UserSourceReport;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IUserStatisticsMapper {

    int getUserActiveRecordCount(@Param("userId") int userId, @Param("aid") int applicationId,
                                 @Param("date") String date);

    Map<String, Object> getUserActiveRecord(@Param("userId") int userId, @Param("aid") int applicationId,
                                            @Param("date") String date);

    int getUserActiveCountByAid(@Param("aid") int applicationId);

    Integer isNewUserActiveRecord(@Param("userId") int userId, @Param("aid") int applicationId,
                                  @Param("date") String date);

    int getUserRegister4AppRecordCount(@Param("userId") int userId, @Param("aid") int applicationId);

    void insertUserRegister4AppRecord(@Param("userId") int userId, @Param("aid") int applicationId,
                                      @Param("amid") int appMemberId, @Param("addTime") long addTime);

    void insertUserActiveRecord(@Param("userId") int userId, @Param("aid") int applicationId,
                                @Param("amid") int appMemberId, @Param("payMoney") int payMoney,
                                @Param("isNewMember") int isNewMember, @Param("isActiveMember") int isActiveMember,
                                @Param("isAgainMember") int isAgainMember, @Param("reportDate") String reportDate);

    int getUserSourceReportCount(@Param("applicationId") int applicationId, @Param("userId") int userId,
                                 @Param("deviceStr") String deviceStr, @Param("reportDate") String reportDate);

    void insertUserSourceReport(UserSourceReport userSourceReport);

    int updateUserActiveMoney(@Param("id") int id, @Param("money") int money);

}
