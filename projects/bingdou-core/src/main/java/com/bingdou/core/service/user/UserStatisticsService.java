package com.bingdou.core.service.user;

import com.bingdou.core.cache.IUserStatisticsCacheManager;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.DeviceInfo;
import com.bingdou.core.model.Application;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.UserSourceReport;
import com.bingdou.core.repository.user.UserStatisticsDao;
import com.bingdou.core.service.pay.AppStatisticsService;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * �û�ͳ�Ʒ�����
 * Created by gaoshan on 16/11/10.
 */
@Service
public class UserStatisticsService {

    @Autowired
    private UserStatisticsDao userStatisticsDao;
    @Autowired
    private IUserStatisticsCacheManager userStatisticsCacheManager;
    @Autowired
    private AppStatisticsService appStatisticsService;

    public void recordUserSourceReport4Client(int userId, Application application,
                                              BaseRequest request, boolean isRegister) {
        LogContext.instance().info("�û�����ͳ���߼�(SOURCE REPORT)");
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        String deviceStr = "";
        if (request.getDeviceInfo().getOs() == Os.ANDROID.getIndex()) {
            deviceStr = request.getDeviceInfo().getAndroidInfo().getAndroidId();
        } else if (request.getDeviceInfo().getOs() == Os.IOS.getIndex()) {
            deviceStr = request.getDeviceInfo().getIosInfo().getIdfa();
        }
        if (StringUtils.isEmpty(deviceStr))
            return;
        int count = userStatisticsDao.getUserSourceReportCount(application.getId(), userId,
                deviceStr, todayStr);
        if (count > 0) {
            LogContext.instance().info("�û����ݴ���,ֱ�ӽ���");
            return;
        }
        DeviceInfo deviceInfo = request.getDeviceInfo();
        UserSourceReport userSourceReport = new UserSourceReport();
        userSourceReport.setUserId(userId);
        userSourceReport.setAppId(application.getAppId());
        userSourceReport.setApplicationId(application.getId());
        userSourceReport.setAppMemberId(application.getAppMemberId());
        userSourceReport.setMac(deviceInfo.getMac());
        userSourceReport.setIsRegister(isRegister ? 1 : 0);
        userSourceReport.setReportDate(todayStr);
        userSourceReport.setOs(Os.getOsNameByIndex(deviceInfo.getOs()));
        userSourceReport.setBaseBrand(deviceInfo.getBaseBand());
        userSourceReport.setBrand(deviceInfo.getBrand());
        userSourceReport.setChannel(request.getOtherInfo().getChannel());
        userSourceReport.setCellId(deviceInfo.getCellId());
        userSourceReport.setKernel(deviceInfo.getKernel());
        userSourceReport.setLac(deviceInfo.getLac());
        userSourceReport.setVersion(deviceInfo.getOsVersion());
        userSourceReport.setModel(deviceInfo.getModel());
        if (Os.ANDROID.getIndex() == request.getDeviceInfo().getOs()) {
            userSourceReport.setIdfa(deviceInfo.getAndroidInfo().getAndroidId());
            userSourceReport.setImei(deviceInfo.getImei());
            userSourceReport.setOpenudid("");
        } else if (Os.IOS.getIndex() == request.getDeviceInfo().getOs()) {
            userSourceReport.setIdfa(deviceInfo.getIosInfo().getIdfa());
            userSourceReport.setImei(deviceInfo.getIosInfo().getUdid());
            userSourceReport.setOpenudid(deviceInfo.getIosInfo().getOpenUdid());
        }
        LogContext.instance().info("�û����ݲ�����,�����¼�¼");
        userStatisticsDao.insertUserSourceReport(userSourceReport);
    }

    public void recordUserActiveRecord4PayCallBackCp(int userId, Application application, int moneyFen) {
        LogContext.instance().info("�û�����ͳ���߼�(CP�ص�)");
        if (moneyFen <= 0)
            return;
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        int applicationId = application.getId();
        Map<String, Object> existRecordMap = userStatisticsDao.getUserActiveRecord(userId, applicationId, todayStr);
        int isNewPayMember;
        if (existRecordMap != null && !existRecordMap.isEmpty()) {
            LogContext.instance().info(todayStr + "�Ѿ������û���Ծ��¼");
            isNewPayMember = 1;
            int payMoneyFromDB = Integer.parseInt(existRecordMap.get("pay_money").toString());
            int id = Integer.parseInt(existRecordMap.get("id").toString());
            if (payMoneyFromDB > 0) {
                isNewPayMember = 0;
            }
            int payMoney = payMoneyFromDB + moneyFen;
            userStatisticsDao.updateUserActiveMoney(id, payMoney);
        } else {
            LogContext.instance().info("�������û���Ծ��¼,�����Ծ��¼");
            userStatisticsDao.insertUserActiveRecord(userId, applicationId, application.getAppMemberId(),
                    moneyFen, 0, 0, 0, todayStr);
            isNewPayMember = 1;
        }
        appStatisticsService.recordAppReportRecord4PayCallBackCp(application, isNewPayMember == 1, moneyFen);
        appStatisticsService.recordMonthAppReport(application, moneyFen);
    }

    public boolean recordUserActiveRecord(int userId, Application application) {
        LogContext.instance().info("�û�����ͳ���߼�");
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        int applicationId = application.getId();
        int appMemberId = application.getAppMemberId();
        if (isExistUserActiveRecord(userId, applicationId, todayStr)) {
            LogContext.instance().info(todayStr + "�Ѿ������û���Ծ��¼,ֱ�ӽ����û�ͳ���߼�");
            return false;
        }
        if (isExistUserRegister4AppRecord(userId, applicationId, appMemberId)) {
            LogContext.instance().info("�������û�");
            Date yesterday = DateUtils.addDays(new Date(), -1);
            String yesterdayStr = DateUtil.format(yesterday, DateUtil.YYYY_MM_DD);
            if (isExistUserActiveRecord(userId, applicationId, yesterdayStr)) {
                LogContext.instance().info(yesterdayStr + "��Ծ�û�");
                int isNewUserInYesterday = userStatisticsDao.isNewUserActiveRecord(userId, applicationId,
                        yesterdayStr);
                LogContext.instance().info(yesterdayStr + "�Ƿ��������û�:" + isNewUserInYesterday);
                int isAgainMember = isNewUserInYesterday == 1 ? 1 : 0;
                LogContext.instance().info("�Ǵ˵��û�:" + isAgainMember);
                LogContext.instance().info("�����Ծ��¼");
                userStatisticsDao.insertUserActiveRecord(userId, applicationId, appMemberId,
                        0, 0, 1, isAgainMember, todayStr);
                LogContext.instance().info("����Ӧ��ͳ����Ϣ");
                appStatisticsService.recordAppReportRecord(application,
                        UserConstants.ACTIVE_USER_TYPE_INDEX_4_STATISTICS, isAgainMember);
            } else {
                LogContext.instance().info(yesterdayStr + "���ǻ�Ծ�û�");
                LogContext.instance().info("�����Ծ�û���¼");
                userStatisticsDao.insertUserActiveRecord(userId, applicationId, appMemberId,
                        0, 0, 0, 0, todayStr);
                LogContext.instance().info("����Ӧ��ͳ����Ϣ");
                appStatisticsService.recordAppReportRecord(application,
                        UserConstants.NORMAL_USER_TYPE_INDEX_4_STATISTICS, 0);
            }
            return false;
        } else {
            LogContext.instance().info("�����û�");
            LogContext.instance().info("�����û�ע����Ӧ�ü�¼");
            insertUserRegister4AppRecord(userId, applicationId, appMemberId);
            LogContext.instance().info("�����û���Ծ��¼");
            userStatisticsDao.insertUserActiveRecord(userId, applicationId, appMemberId,
                    0, 1, 0, 0, todayStr);
            LogContext.instance().info("����Ӧ��ͳ����Ϣ");
            appStatisticsService.recordAppReportRecord(application,
                    UserConstants.NEW_USER_TYPE_INDEX_4_STATISTICS, 0);
            return true;
        }
    }

    private boolean isExistUserRegister4AppRecord(int userId, int applicationId, int appMemberId) {
        String appMemberIdStr = userStatisticsCacheManager.getMemberBelong(applicationId, userId);
        if (StringUtils.isEmpty(appMemberIdStr)) {
            int count = userStatisticsDao.getUserRegister4AppRecordCount(userId, applicationId);
            if (count > 0) {
                userStatisticsCacheManager.setMemberBelong(applicationId, userId, appMemberId);
            }
            return count > 0;
        }
        return true;
    }

    private boolean isExistUserActiveRecord(int userId, int applicationId, String date) {
        int count = userStatisticsDao.getUserActiveRecordCount(userId, applicationId, date);
        return count > 0;
    }

    private void insertUserRegister4AppRecord(int userId, int applicationId, int appMemberId) {
        if (userId <= 0 || applicationId <= 0 || appMemberId <= 0) {
            LogContext.instance().info("�Ƿ�����");
            return;
        }
        userStatisticsCacheManager.setMemberBelong(applicationId, userId, appMemberId);
        long nowSeconds = DateUtil.getCurrentTimeSeconds();
        userStatisticsDao.insertUserRegister4AppRecord(userId, applicationId, appMemberId, nowSeconds);
    }

}
