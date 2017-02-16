package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.AppReport;
import org.apache.ibatis.annotations.Param;

public interface IAppStatisticsMapper {

    AppReport getAppReportByAidAndReportDate(@Param("applicationId") int applicationId,
                                             @Param("reportDate") String reportDate);

    void insertAppReportRecord(AppReport appReport);

    Integer getAppPayMonthMoney(@Param("applicationId") int applicationId);

    Integer getAppPayMonthId(@Param("applicationId") int applicationId,
                             @Param("reportDate") String reportDate);

    int updateAppPayMonth(@Param("id") int id, @Param("moneyFen") int moneyFen);

    void insertAppPayMonth(@Param("appName") String appName, @Param("applicationId") int applicationId,
                           @Param("appMemberId") int appMemberId, @Param("reportDate") String reportDate,
                           @Param("moneyFen") int moneyFen);

    void update4NormalUser(@Param("appReportId") int appReportId);

    void update4NewUser(@Param("appReportId") int appReportId);

    void update4ActiveUser(@Param("appReportId") int appReportId);

    void update4ActiveUserAndAgainUser(@Param("appReportId") int appReportId);

    int update4PayMoneyAndPayMember(@Param("appReportId") int appReportId, @Param("moneyFen") int moneyFen);

    int update4PayMoney(@Param("appReportId") int appReportId, @Param("moneyFen") int moneyFen);
}
