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
 * 用户统计服务类
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
        LogContext.instance().info("用户数据统计逻辑(SOURCE REPORT)");
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
            LogContext.instance().info("用户数据存在,直接结束");
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
        LogContext.instance().info("用户数据不存在,插入新记录");
        userStatisticsDao.insertUserSourceReport(userSourceReport);
    }

    public void recordUserActiveRecord4PayCallBackCp(int userId, Application application, int moneyFen) {
        LogContext.instance().info("用户数据统计逻辑(CP回调)");
        if (moneyFen <= 0)
            return;
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        int applicationId = application.getId();
        Map<String, Object> existRecordMap = userStatisticsDao.getUserActiveRecord(userId, applicationId, todayStr);
        int isNewPayMember;
        if (existRecordMap != null && !existRecordMap.isEmpty()) {
            LogContext.instance().info(todayStr + "已经存在用户活跃记录");
            isNewPayMember = 1;
            int payMoneyFromDB = Integer.parseInt(existRecordMap.get("pay_money").toString());
            int id = Integer.parseInt(existRecordMap.get("id").toString());
            if (payMoneyFromDB > 0) {
                isNewPayMember = 0;
            }
            int payMoney = payMoneyFromDB + moneyFen;
            userStatisticsDao.updateUserActiveMoney(id, payMoney);
        } else {
            LogContext.instance().info("不存在用户活跃记录,加入活跃记录");
            userStatisticsDao.insertUserActiveRecord(userId, applicationId, application.getAppMemberId(),
                    moneyFen, 0, 0, 0, todayStr);
            isNewPayMember = 1;
        }
        appStatisticsService.recordAppReportRecord4PayCallBackCp(application, isNewPayMember == 1, moneyFen);
        appStatisticsService.recordMonthAppReport(application, moneyFen);
    }

    public boolean recordUserActiveRecord(int userId, Application application) {
        LogContext.instance().info("用户数据统计逻辑");
        String todayStr = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD);
        int applicationId = application.getId();
        int appMemberId = application.getAppMemberId();
        if (isExistUserActiveRecord(userId, applicationId, todayStr)) {
            LogContext.instance().info(todayStr + "已经存在用户活跃记录,直接结束用户统计逻辑");
            return false;
        }
        if (isExistUserRegister4AppRecord(userId, applicationId, appMemberId)) {
            LogContext.instance().info("非新增用户");
            Date yesterday = DateUtils.addDays(new Date(), -1);
            String yesterdayStr = DateUtil.format(yesterday, DateUtil.YYYY_MM_DD);
            if (isExistUserActiveRecord(userId, applicationId, yesterdayStr)) {
                LogContext.instance().info(yesterdayStr + "活跃用户");
                int isNewUserInYesterday = userStatisticsDao.isNewUserActiveRecord(userId, applicationId,
                        yesterdayStr);
                LogContext.instance().info(yesterdayStr + "是否是新增用户:" + isNewUserInYesterday);
                int isAgainMember = isNewUserInYesterday == 1 ? 1 : 0;
                LogContext.instance().info("是此登用户:" + isAgainMember);
                LogContext.instance().info("加入活跃记录");
                userStatisticsDao.insertUserActiveRecord(userId, applicationId, appMemberId,
                        0, 0, 1, isAgainMember, todayStr);
                LogContext.instance().info("更新应用统计信息");
                appStatisticsService.recordAppReportRecord(application,
                        UserConstants.ACTIVE_USER_TYPE_INDEX_4_STATISTICS, isAgainMember);
            } else {
                LogContext.instance().info(yesterdayStr + "不是活跃用户");
                LogContext.instance().info("加入活跃用户记录");
                userStatisticsDao.insertUserActiveRecord(userId, applicationId, appMemberId,
                        0, 0, 0, 0, todayStr);
                LogContext.instance().info("更新应用统计信息");
                appStatisticsService.recordAppReportRecord(application,
                        UserConstants.NORMAL_USER_TYPE_INDEX_4_STATISTICS, 0);
            }
            return false;
        } else {
            LogContext.instance().info("新增用户");
            LogContext.instance().info("加入用户注册新应用记录");
            insertUserRegister4AppRecord(userId, applicationId, appMemberId);
            LogContext.instance().info("加入用户活跃记录");
            userStatisticsDao.insertUserActiveRecord(userId, applicationId, appMemberId,
                    0, 1, 0, 0, todayStr);
            LogContext.instance().info("更新应用统计信息");
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
            LogContext.instance().info("非法参数");
            return;
        }
        userStatisticsCacheManager.setMemberBelong(applicationId, userId, appMemberId);
        long nowSeconds = DateUtil.getCurrentTimeSeconds();
        userStatisticsDao.insertUserRegister4AppRecord(userId, applicationId, appMemberId, nowSeconds);
    }

}
