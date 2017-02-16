package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IAppStatisticsMapper;
import com.bingdou.core.model.AppReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 16/12/10.
 */
@Repository
public class AppStatisticsDao {

    @Autowired
    private IAppStatisticsMapper appStatisticsMapper;

    public AppReport getAppReportByAidAndReportDate(int applicationId, String date) {
        return appStatisticsMapper.getAppReportByAidAndReportDate(applicationId, date);
    }

    public Integer getAppPayMonthMoney(int applicationId) {
        return appStatisticsMapper.getAppPayMonthMoney(applicationId);
    }

    public Integer getAppPayMonthId(int applicationId, String date) {
        return appStatisticsMapper.getAppPayMonthId(applicationId, date);
    }

    public int updateAppPayMonth(int id, int moneyFen) {
        return appStatisticsMapper.updateAppPayMonth(id, moneyFen);
    }

    public void insertAppPayMonth(String appName, int applicationId, int appMemberId,
                                  String reportDate, int moneyFen) {
        appStatisticsMapper.insertAppPayMonth(appName, applicationId, appMemberId, reportDate, moneyFen);
    }

    public void insertAppReportRecord(AppReport appReport) {
        appStatisticsMapper.insertAppReportRecord(appReport);
    }

    public void update4NormalUser(int appReportId) {
        appStatisticsMapper.update4NormalUser(appReportId);
    }

    public void update4NewUser(int appReportId) {
        appStatisticsMapper.update4NewUser(appReportId);
    }

    public void update4ActiveUser(int appReportId) {
        appStatisticsMapper.update4ActiveUser(appReportId);
    }

    public void update4ActiveUserAndAgainUser(int appReportId) {
        appStatisticsMapper.update4ActiveUserAndAgainUser(appReportId);
    }

    public int update4PayMoneyAndPayMember(int appReportId, int moneyFen) {
        return appStatisticsMapper.update4PayMoneyAndPayMember(appReportId, moneyFen);
    }

    public int update4PayMoney(int appReportId, int moneyFen) {
        return appStatisticsMapper.update4PayMoney(appReportId, moneyFen);
    }

}
