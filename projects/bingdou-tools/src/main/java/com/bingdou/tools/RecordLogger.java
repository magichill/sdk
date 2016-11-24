package com.bingdou.tools;

import com.bingdou.tools.constants.ClientHadoopLogObject;
import com.bingdou.tools.constants.CommonLoggerNameConstants;
import com.bingdou.tools.constants.HadoopLogObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * 记录类日志帮助类
 *
 */
public class RecordLogger {

    private static final String SEPARATOR = " - ";
    private static final String HADOOP_SEPARATOR = "\t";

    private RecordLogger() {
    }

    public static void timeLog(String methodName, long time) {
        try {
            Logger logger = LogManager.getLogger(CommonLoggerNameConstants.TIME_CALCULATOR_LOGGER);
            logger.info(format(false, methodName, time));
        } catch (Exception e) {
            LogContext.instance().error(e, "请求耗时日志记录失败");
        }
    }

    public static void mailLog(String from, String to, String title, String content) {
        try {
            Logger logger = LogManager.getLogger(CommonLoggerNameConstants.MAIL_LOGGER);
            logger.info(format(false, "FROM:" + from, "TO:" + to, "TITLE:" + title, "CONTENT:" + content));
        } catch (Exception e) {
            LogContext.instance().error(e, "发送邮件日志记录失败");
        }
    }

    public static void clientHadoopLog(ClientHadoopLogObject object, boolean isIos) {
        try {
            String loggerName = isIos ? CommonLoggerNameConstants.CLIENT_IOS_HADOOP_LOGGER :
                    CommonLoggerNameConstants.CLIENT_ANDROID_HADOOP_LOGGER;
            Date now = new Date();
            object.setServerTime(now.getTime() + "");
            Logger logger = LogManager.getLogger(loggerName);
            logger.info(format(true, object.getScene(), object.getAction(), object.getServerTime(),
                    object.getClientIp(), object.getAppId(), object.getMac(),
                    object.getIosUdidOrAndroidImei(), object.getIosOpenUdid(),
                    object.getIosIdfaOrAndroidId(), object.getIosIdfvOrAndroidSerialNumber(),
                    "", "", object.getBrand(), object.getModel(), object.getOsName(),
                    object.getOsVersion(), object.getSdkVersion(), object.getAppVersion(),
                    object.getPackageName(), object.getImsi(), object.getChannelId(), object.getLoginName(),
                    object.getUserId(), object.getNetworkType(), object.getClientTime(), -1,
                    DateUtil.format(now, DateUtil.YYYY_MM_DD_HH_MM_SS), object.getIsForceUp(),
                    object.getIsAutoLogin(), object.getActivityType(), object.getOrderMoney(),
                    object.getPayType(), object.getMessageId(), object.getMessageType(),
                    object.getDid(), object.getIsPad(), object.getScreenDirection(),
                    -1, 0, object.getIsShowFloatingBoxUserCenter(),
                    object.getIsShowFloatingBoxLive(), -1, 0, object.getGoToSafeCenterOrUpdatePwd(),
                    object.getIosBreakout(), object.getIsFixRechargeMoney(), object.getIsCommonPayType(),
                    object.getQuickPayType(), object.getBannerMessageIds4Show(),
                    object.getBannerMessageId4Click())
            );
        } catch (Exception e) {
            LogContext.instance().error(e, "客户端行为日志记录失败");
        }
    }

    public static void hadoopLog(HadoopLogObject object) {
        try {
            object.setServerTime(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            Logger logger = LogManager.getLogger(CommonLoggerNameConstants.HADOOP_LOGGER);
            logger.info(format(true, object.getAction().getIndex(), object.getServerTime(),
                    object.getClientIp(), object.getAppId(), object.getMac(),
                    object.getIosUdidOrAndroidImei(), object.getIosOpenUdid(),
                    object.getIosIdfaOrAndroidId(), object.getIosIdfvOrAndroidSerialNumber(),
                    object.getIosMainboardSerial(), object.getIosSerial(),
                    object.getBrand(), object.getModel(), object.getOs(),
                    object.getOsVersion(), object.getSdkVersion(), object.getAppVersion(),
                    object.getPackageName(), object.getImsi(), object.getChannelId(), object.getNetworkType(),
                    object.getIsNewDevice(), object.getLoginName(), object.getUserId(),
                    object.getMobile(), object.getEmail(), object.getPayType(),
                    object.getOrderId(), NumberUtil.convertYuanFromFen(object.getOrderMoney()),
                    NumberUtil.convertYuanFromFen(object.getPayedMoney()),
                    object.getOrderType(), object.getOrderStatus(), object.getActivityType(),
                    object.getIsUseVoucher(), object.getBizId(), object.getRequestSource(),
                    object.getOtherCode(), object.getIosBreakout()));
        } catch (Exception e) {
            LogContext.instance().error(e, "服务器日志记录失败");
        }
    }

    @SuppressWarnings("Duplicates")
    private static String format(boolean isHadoopLog, Object... messages) {
        String formatSeparator = SEPARATOR;
        if (isHadoopLog)
            formatSeparator = HADOOP_SEPARATOR;
        if (messages == null || messages.length < 1)
            return "";
        StringBuilder ftMessage = new StringBuilder();
        int count = 0;
        for (Object msg : messages) {
            ftMessage.append(msg == null ? "" : msg);
            if (count < messages.length - 1)
                ftMessage.append(formatSeparator);
            count++;
        }
        return ftMessage.toString();
    }

}
