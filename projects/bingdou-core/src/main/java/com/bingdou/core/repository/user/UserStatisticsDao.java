package com.bingdou.core.repository.user;

import com.bingdou.core.mapper.user.IUserStatisticsMapper;
import com.bingdou.core.model.UserSourceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserStatisticsDao {

    @Autowired
    private IUserStatisticsMapper userStatisticsMapper;

    public int getUserActiveRecordCount(int userId, int applicationId, String date) {
        return userStatisticsMapper.getUserActiveRecordCount(userId, applicationId, date);
    }

    public Map<String, Object> getUserActiveRecord(int userId, int applicationId, String date) {
        return userStatisticsMapper.getUserActiveRecord(userId, applicationId, date);
    }

    public int getUserActiveCountByAid(int applicationId) {
        return userStatisticsMapper.getUserActiveCountByAid(applicationId);
    }

    public int isNewUserActiveRecord(int userId, int applicationId, String date) {
        Integer result = userStatisticsMapper.isNewUserActiveRecord(userId, applicationId, date);
        if (result == null)
            return 0;
        return result;
    }

    public int getUserRegister4AppRecordCount(int userId, int applicationId) {
        return userStatisticsMapper.getUserRegister4AppRecordCount(userId, applicationId);
    }

    public void insertUserRegister4AppRecord(int userId, int applicationId,
                                             int appMemberId, long addTime) {
        userStatisticsMapper.insertUserRegister4AppRecord(userId, applicationId,
                appMemberId, addTime);
    }

    public void insertUserActiveRecord(int userId, int applicationId,
                                       int appMemberId, int payMoney,
                                       int isNewMember, int isActiveMember,
                                       int isAgainMember, String reportDate) {
        userStatisticsMapper.insertUserActiveRecord(userId, applicationId, appMemberId,
                payMoney, isNewMember, isActiveMember, isAgainMember, reportDate);
    }

    public int getUserSourceReportCount(int applicationId, int userId, String deviceStr, String reportDate) {
        return userStatisticsMapper.getUserSourceReportCount(applicationId, userId, deviceStr, reportDate);
    }

    public void insertUserSourceReport(UserSourceReport userSourceReport) {
        userStatisticsMapper.insertUserSourceReport(userSourceReport);
    }

    public int updateUserActiveMoney(int id, int moneyFen) {
        return userStatisticsMapper.updateUserActiveMoney(id, moneyFen);
    }

}
