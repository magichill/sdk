package com.bingdou.core.service.pay;

import com.bingdou.core.model.Os;
import com.bingdou.core.model.PayType;
import com.bingdou.core.model.PayTypeModel;
import com.bingdou.core.repository.pay.PayTypeDao;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 支付基础服务类
 */
@Service
public class PayTypeBaseService {

    @Autowired
    private PayTypeDao payTypeDao;

    /**
     * 验证支付方式是否开启
     */
    public boolean valid(PayType payType, Os os, String sdkVersion, float money) {
        if (payType == null || os == null || money <= 0)
            return false;
        int count = payTypeDao.getPayTypeCountByIdAndOsAndVersion(payType.getIndex(), os.getIndex(), sdkVersion, money);
        return count > 0;
    }

    /**
     * 记录PP钱包回调结果
     */
//    public void addPPOrderDetail(Map<String, String> map) {
//        payTypeDao.insertPPOrderDetail(map);
//    }

    /**
     * 记录汇元微信回调结果
     */
//    public void addHeepyWxOrderDetail(Map<String, String> map) {
//        payTypeDao.insertHeepayWxOrderDetail(map);
//    }

    /**
     * 记录支付宝回调结果
     */
    public void addAliOrderDetail(Map<String, String> map) {
        payTypeDao.insertAliOrderDetail(map);
    }

    /**
     * 记录CHINA PAY回调结果
     */
//    public void addChinaPayOrderDetail(Map<String, String> map) {
//        payTypeDao.insetChinaPayUnionOrderDetail(map);
//    }

    /**
     * 记录官方微信回调结果
     */
    public void addWeixinOrderDetail(Map<String, String> map) {
        payTypeDao.insertWeixinOrderDetail(map);
    }

    /**
     * 记录UPMP回调结果
     */
//    public void addUpmpOrderDetail(Map<String, String> map) {
//        payTypeDao.insertUpmpOrderDetail(map);
//    }

    /**
     * 记录19PAY回调结果
     */
//    public void insert19PayOrderDetail(Map<String, String> map) {
//        payTypeDao.insert19PayOrderDetail(map);
//    }

    /**
     * 获取支付列表(服务器)
     */
    public List<PayTypeModel> getPayTypeList4Server() {
        return getPayTypeList(Os.SERVER, "");
    }

    /**
     * 获取支付列表(客户端)
     */
    public List<PayTypeModel> getPayTypeList4Client(Os os, String sdkVersion, boolean isPad) {
        if (StringUtils.isEmpty(sdkVersion))
            return new ArrayList<PayTypeModel>();
        List<PayTypeModel> payTypeModelList = getPayTypeList(os, sdkVersion);
        if ((os == Os.IOS && "1.6.0".compareTo(sdkVersion) > 0)
                || (os == Os.ANDROID && "1.3.0".compareTo(sdkVersion) > 0)) {
            //为了兼容老版本,IOS小于1.6.0版本,ANDROID小于1.3.0版本
            for (PayTypeModel model : payTypeModelList) {
                if (model.getId() == PayType.ALI_NO_PWD.getIndex()) {
                    model.setDisplay(3);
                    break;
                }
            }
        }
        if (!Os.IOS.equals(os) || !isPad) {
            return filterAliScanPayTypeList4Client(payTypeModelList);
        }
        return payTypeModelList;
    }

    private List<PayTypeModel> filterAliScanPayTypeList4Client(List<PayTypeModel> payTypeModelList) {
        List<PayTypeModel> result = new ArrayList<PayTypeModel>();
        for (PayTypeModel payTypeModel : payTypeModelList) {
            if (payTypeModel.getId() == PayType.ALI_SCAN.getIndex())
                continue;
            result.add(payTypeModel);
        }
        return result;
    }

    private List<PayTypeModel> getPayTypeList(Os os, String sdkVersion) {
        LogContext.instance().info("获取支付列表");
        if (os == null)
            return new ArrayList<PayTypeModel>();
        List<PayTypeModel> payTypeList = payTypeDao.getPayTypeList(os.getIndex(), sdkVersion);
        if (payTypeList == null || payTypeList.isEmpty()) {
            LogContext.instance().error("未获取到任何支付方式信息");
            return new ArrayList<PayTypeModel>();
        }
        LogContext.instance().info("DB读取到的支付列表");
        return payTypeList;
    }

}
