package com.bingdou.core.service.pay;

import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.AppReport;
import com.bingdou.core.model.Application;
import com.bingdou.core.repository.pay.AppStatisticsDao;
import com.bingdou.core.repository.user.UserStatisticsDao;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Ӧ�ñ���ͳ�Ʒ�����
 * Created by liuyang on 15/11/10.
 */
@Service
public class AppStatisticsService {

    @Autowired
    private AppStatisticsDao appStatisticsDao;
    @Autowired
    private UserStatisticsDao userStatisticsDao;

    public void recordAppReportRecord4PayCallBackCp(Application application, boolean isNewPayMember, int moneyFen) {
        LogContext.instance().info("Ӧ������ͳ���߼�(CP�ص�)");
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        int applicationId = application.getId();
        AppReport appReport = appStatisticsDao.getAppReportByAidAndReportDate(applicationId, todayStr);
        if (appReport != null && appReport.getId() > 0) {
            LogContext.instance().info("����Ӧ��ID��" + applicationId + "�ı����¼");
            if (isNewPayMember) {
                appStatisticsDao.update4PayMoneyAndPayMember(appReport.getId(), moneyFen);
            } else {
                appStatisticsDao.update4PayMoney(appReport.getId(), moneyFen);
            }
        } else {
            LogContext.instance().info("������Ӧ��ID��" + applicationId + "�ı����¼");
            AppReport insertAppReport = setTotalInfo4AppReport(applicationId, moneyFen);
            insertAppReport.setOs(application.getOs());
            insertAppReport.setAppName(application.getName());
            insertAppReport.setApplicationId(applicationId);
            insertAppReport.setAppMemberId(application.getAppMemberId());
            insertAppReport.setReportDate(todayStr);
            insertAppReport.setNewMember(0);
            insertAppReport.setLoginMember(1);
            insertAppReport.setActiveMember(0);
            insertAppReport.setAgainMember(0);
            insertAppReport.setPayMember(1);
            insertAppReport.setPayMoney(moneyFen);
            insertAppReport.setAddFrom(2);
            appStatisticsDao.insertAppReportRecord(insertAppReport);
        }
    }

    public void recordAppReportRecord(Application application, int userStatisticsTypeIndex,
                                      int againMember) {
        LogContext.instance().info("Ӧ������ͳ���߼�");
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        int applicationId = application.getId();
        AppReport appReport = appStatisticsDao.getAppReportByAidAndReportDate(applicationId, todayStr);
        if (appReport != null && appReport.getId() > 0) {
            LogContext.instance().info("����Ӧ��ID��" + applicationId + "�ı����¼");
            if (UserConstants.NORMAL_USER_TYPE_INDEX_4_STATISTICS == userStatisticsTypeIndex) {
                LogContext.instance().info("����ͨ�û�ͳ������,��¼����1");
                appStatisticsDao.update4NormalUser(appReport.getId());
            } else if (UserConstants.NEW_USER_TYPE_INDEX_4_STATISTICS == userStatisticsTypeIndex) {
                LogContext.instance().info("�������û�ͳ������,��¼����1,��������1,�ۼ�����1");
                appStatisticsDao.update4NewUser(appReport.getId());
            } else if (UserConstants.ACTIVE_USER_TYPE_INDEX_4_STATISTICS == userStatisticsTypeIndex) {
                LogContext.instance().info("�ǻ�Ծ�û�ͳ������");
                if (againMember == 1) {
                    LogContext.instance().info("�Ǵε��û�,��¼����1,��Ծ����1,�ε�����1");
                    appStatisticsDao.update4ActiveUserAndAgainUser(appReport.getId());
                } else {
                    LogContext.instance().info("���Ǵε��û�,��¼����1,��Ծ����1");
                    appStatisticsDao.update4ActiveUser(appReport.getId());
                }
            }
        } else {
            LogContext.instance().info("������Ӧ��ID��" + applicationId + "�ı����¼");
            AppReport insertAppReport = setTotalInfo4AppReport(applicationId, 0);
            insertAppReport.setOs(application.getOs());
            insertAppReport.setAppName(application.getName());
            insertAppReport.setApplicationId(applicationId);
            insertAppReport.setAppMemberId(application.getAppMemberId());
            insertAppReport.setReportDate(todayStr);
            insertAppReport.setPayMember(0);
            insertAppReport.setPayMoney(0);
            insertAppReport.setLoginMember(1);
            int newMember = 0;
            int activeMember = 0;
            int totalMember = 0;
            int updateAgainMember = 0;
            if (UserConstants.NORMAL_USER_TYPE_INDEX_4_STATISTICS == userStatisticsTypeIndex) {
                LogContext.instance().info("����ͨ�û�ͳ������");
                totalMember = insertAppReport.getTotalMember();
            } else if (UserConstants.NEW_USER_TYPE_INDEX_4_STATISTICS == userStatisticsTypeIndex) {
                LogContext.instance().info("�������û�ͳ������");
                newMember = 1;
                totalMember = insertAppReport.getTotalMember() + 1;
            } else if (UserConstants.ACTIVE_USER_TYPE_INDEX_4_STATISTICS == userStatisticsTypeIndex) {
                LogContext.instance().info("�ǻ�Ծ�û�ͳ������");
                activeMember = 1;
                updateAgainMember = againMember;
                totalMember = insertAppReport.getTotalMember();
            }
            insertAppReport.setNewMember(newMember);
            insertAppReport.setActiveMember(activeMember);
            insertAppReport.setTotalMember(totalMember);
            insertAppReport.setAgainMember(updateAgainMember);
            insertAppReport.setAddFrom(1);
            appStatisticsDao.insertAppReportRecord(insertAppReport);
        }
    }

    public void recordMonthAppReport(Application application, int moneyFen) {
        LogContext.instance().info("Ӧ������ͳ���߼�(�¶�)");
        String monthStr = DateUtil.format(new Date(), DateUtil.YYYY_MM) + "-01";
        Integer id = appStatisticsDao.getAppPayMonthId(application.getId(), monthStr);
        if (id == null) {
            LogContext.instance().info("������Ӧ������(�¶�)��¼,����");
            appStatisticsDao.insertAppPayMonth(application.getName(), application.getId(),
                    application.getAppMemberId(), monthStr, moneyFen);
        } else {
            LogContext.instance().info("����Ӧ������(�¶�)��¼,����");
            appStatisticsDao.updateAppPayMonth(id, moneyFen);
        }
    }

    private AppReport setTotalInfo4AppReport(int applicationId, int totalMoney) {
        Date yesterday = DateUtils.addDays(new Date(), -1);
        String yesterdayStr = DateUtil.format(yesterday, DateUtil.YYYY_MM_DD);
        AppReport appReportYesterday = appStatisticsDao.
                getAppReportByAidAndReportDate(applicationId, yesterdayStr);
        AppReport result = new AppReport();
        int totalMember;
        int yesterdayRegister = 0;
        int updateTotalMoney = 0;
        if (appReportYesterday != null && appReportYesterday.getId() > 0) {
            totalMember = appReportYesterday.getTotalMember();
            yesterdayRegister = appReportYesterday.getNewMember();
            updateTotalMoney = appReportYesterday.getTotalMoney() + totalMoney;
        } else {
            totalMember = userStatisticsDao.getUserActiveCountByAid(applicationId);
            Integer appMonthMoney = appStatisticsDao.getAppPayMonthMoney(applicationId);
            if (appMonthMoney != null && appMonthMoney > 0) {
                updateTotalMoney = appMonthMoney + totalMoney;
            }
        }
        result.setTotalMember(totalMember);
        result.setYesterdayRegister(yesterdayRegister);
        result.setTotalMoney(updateTotalMoney);
        return result;
    }

}
