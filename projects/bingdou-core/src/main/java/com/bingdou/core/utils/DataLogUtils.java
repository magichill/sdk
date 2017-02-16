package com.bingdou.core.utils;


import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.DeviceInfo;
import com.bingdou.core.helper.OtherInfo;
import com.bingdou.core.model.Order;
import com.bingdou.core.model.Os;
import com.bingdou.core.model.RechargeOrder;
import com.bingdou.core.model.User;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RecordLogger;
import com.bingdou.tools.constants.ClientHadoopLogObject;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.tools.constants.HadoopLogObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 入库日志工具类
 */
public class DataLogUtils {

    private DataLogUtils() {
    }

    public static void recordClientHadoopLog4Ios(ClientHadoopLogObject object) {
        LogContext.instance().info("记录客户端(IOS)行为统计日志");
        if (object == null)
            return;
        RecordLogger.clientHadoopLog(object, true);
    }

    public static void recordClientHadoopLog4Android(ClientHadoopLogObject object) {
        LogContext.instance().info("记录客户端(ANDROID)统计日志");
        if (object == null)
            return;
        RecordLogger.clientHadoopLog(object, false);
    }

    public static void recordHadoopLog(HadoopLogAction action, BaseRequest request,
                                       User user, String clientIp,
                                       String bizId, String otherCode, boolean isNewDevice) {
        LogContext.instance().info("记录服务器关键点统计日志");
        HadoopLogObject object = createHadoopLogObject(action, request, user, clientIp,
                isNewDevice ? 1 : 0, bizId, otherCode, null);
        RecordLogger.hadoopLog(object);
    }

    public static void recordHadoopLog(HadoopLogAction action, BaseRequest request,
                                       User user, String clientIp,
                                       String bizId, Order order, boolean isRecharge,
                                       boolean isUseConsumeVoucher) {
        LogContext.instance().info("记录服务器关键点统计日志");
        DataLogPayObject payObject = new DataLogPayObject();
        if (order != null) {
            payObject.setPayType(order.getPayType() == null ? -1 : order.getPayType());
            payObject.setOrderId(order.getOrderId());
            payObject.setPayedMoney(order.getPayedMoney() == null ? 0 : order.getPayedMoney());
            payObject.setOrderStatus(order.getStatus() == null ? -1 : order.getStatus());
            if (isRecharge) {
                RechargeOrder rechargeOrder = (RechargeOrder) order;
                payObject.setOrderMoney(rechargeOrder.getOrderMoney());
                payObject.setOrderType(1);
                payObject.setActivityType(rechargeOrder.getActivityType());
            } else {
                payObject.setOrderType(2);
                payObject.setIsUseVoucher(isUseConsumeVoucher ? 1 : 0);
            }
        }
        HadoopLogObject object = createHadoopLogObject(action, request, user, clientIp, 0, bizId, "", payObject);
        RecordLogger.hadoopLog(object);
    }

    private static HadoopLogObject createHadoopLogObject(HadoopLogAction action, BaseRequest request,
                                                         User user, String clientIp, int isNewDevice,
                                                         String bizId, String otherCode, DataLogPayObject payObject) {
        HadoopLogObject object = new HadoopLogObject();
        try {
            DeviceInfo deviceInfo = null;
            OtherInfo otherInfo = null;
            if (request != null) {
                deviceInfo = request.getDeviceInfo();
                otherInfo = request.getOtherInfo();
//                object.setDid(request.getDid());
                object.setRequestSource(request.getRequestSource());
            }
            object.setAction(action);
            object.setClientIp(clientIp);
            object.setIsNewDevice(isNewDevice);
            object.setBizId(bizId);
            object.setOtherCode(otherCode);
            if (otherInfo != null) {
                object.setAppId(otherInfo.getAppId());
                object.setSdkVersion(otherInfo.getSdkVersion());
                object.setAppVersion(otherInfo.getAppVersion());
                object.setPackageName(otherInfo.getPackageName());
                object.setChannelId(otherInfo.getChannel());
            }
            if (StringUtils.isEmpty(object.getAppId()) && request != null) {
                object.setAppId(request.getAppId());
            }
            if (StringUtils.isEmpty(object.getChannelId()) && request != null) {
                object.setChannelId(request.getChannel());
            }
            if (deviceInfo != null) {
                object.setMac(deviceInfo.getMac());
                object.setBrand(deviceInfo.getBrand());
                object.setModel(deviceInfo.getModel());
                object.setOs(deviceInfo.getOs());
                object.setOsVersion(deviceInfo.getOsVersion());
                object.setImsi(deviceInfo.getImsi());
                object.setNetworkType(deviceInfo.getNetType());
            }
            if (user != null) {
                object.setLoginName(user.getLoginName());
                object.setUserId(user.getId());
                object.setMobile(user.getMobile());
                object.setEmail(user.getEmail());
            }
            if (deviceInfo != null && deviceInfo.getAndroidInfo() != null
                    && deviceInfo.getOs() == Os.ANDROID.getIndex()) {
                DeviceInfo.AndroidInfo androidInfo = deviceInfo.getAndroidInfo();
                object.setIosUdidOrAndroidImei(deviceInfo.getImei());
                object.setIosIdfaOrAndroidId(androidInfo.getAndroidId());
                object.setIosIdfvOrAndroidSerialNumber(androidInfo.getAndroidSerialNumber());
            } else if (deviceInfo != null && deviceInfo.getIosInfo() != null
                    && deviceInfo.getOs() == Os.IOS.getIndex()) {
                DeviceInfo.IosInfo iosInfo = deviceInfo.getIosInfo();
                object.setIosUdidOrAndroidImei(iosInfo.getUdid());
                object.setIosOpenUdid(iosInfo.getOpenUdid());
                object.setIosIdfaOrAndroidId(iosInfo.getIdfa());
                object.setIosIdfvOrAndroidSerialNumber(iosInfo.getIdfv());
                object.setIosMainboardSerial("");
                object.setIosSerial("");
                object.setIosBreakout(iosInfo.getBreakout());
            }
            if (payObject != null) {
                object.setPayType(payObject.getPayType());
                object.setOrderId(payObject.getOrderId());
                object.setOrderMoney(payObject.getOrderMoney());
                object.setPayedMoney(payObject.getPayedMoney());
                object.setOrderType(payObject.getOrderType());
                object.setOrderStatus(payObject.getOrderStatus());
                object.setActivityType(payObject.getActivityType());
                object.setIsUseVoucher(payObject.getIsUseVoucher());
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "创建服务器入库日志对象失败");
        }
        return object;
    }

}

