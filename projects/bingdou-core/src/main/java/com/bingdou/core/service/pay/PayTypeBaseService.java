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
 * ֧������������
 */
@Service
public class PayTypeBaseService {

    @Autowired
    private PayTypeDao payTypeDao;

    /**
     * ��֤֧����ʽ�Ƿ���
     */
    public boolean valid(PayType payType, Os os, String sdkVersion, float money) {
        if (payType == null || os == null || money <= 0)
            return false;
        int count = payTypeDao.getPayTypeCountByIdAndOsAndVersion(payType.getIndex(), os.getIndex(), sdkVersion, money);
        return count > 0;
    }

    /**
     * ��¼PPǮ���ص����
     */
//    public void addPPOrderDetail(Map<String, String> map) {
//        payTypeDao.insertPPOrderDetail(map);
//    }

    /**
     * ��¼��Ԫ΢�Żص����
     */
//    public void addHeepyWxOrderDetail(Map<String, String> map) {
//        payTypeDao.insertHeepayWxOrderDetail(map);
//    }

    /**
     * ��¼֧�����ص����
     */
    public void addAliOrderDetail(Map<String, String> map) {
        payTypeDao.insertAliOrderDetail(map);
    }

    /**
     * ��¼CHINA PAY�ص����
     */
//    public void addChinaPayOrderDetail(Map<String, String> map) {
//        payTypeDao.insetChinaPayUnionOrderDetail(map);
//    }

    /**
     * ��¼�ٷ�΢�Żص����
     */
    public void addWeixinOrderDetail(Map<String, String> map) {
        payTypeDao.insertWeixinOrderDetail(map);
    }

    /**
     * ��¼UPMP�ص����
     */
//    public void addUpmpOrderDetail(Map<String, String> map) {
//        payTypeDao.insertUpmpOrderDetail(map);
//    }

    /**
     * ��¼19PAY�ص����
     */
//    public void insert19PayOrderDetail(Map<String, String> map) {
//        payTypeDao.insert19PayOrderDetail(map);
//    }

    /**
     * ��ȡ֧���б�(������)
     */
    public List<PayTypeModel> getPayTypeList4Server() {
        return getPayTypeList(Os.SERVER, "");
    }

    /**
     * ��ȡ֧���б�(�ͻ���)
     */
    public List<PayTypeModel> getPayTypeList4Client(Os os, String sdkVersion, boolean isPad) {
        if (StringUtils.isEmpty(sdkVersion))
            return new ArrayList<PayTypeModel>();
        List<PayTypeModel> payTypeModelList = getPayTypeList(os, sdkVersion);
        if ((os == Os.IOS && "1.6.0".compareTo(sdkVersion) > 0)
                || (os == Os.ANDROID && "1.3.0".compareTo(sdkVersion) > 0)) {
            //Ϊ�˼����ϰ汾,IOSС��1.6.0�汾,ANDROIDС��1.3.0�汾
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
        LogContext.instance().info("��ȡ֧���б�");
        if (os == null)
            return new ArrayList<PayTypeModel>();
        List<PayTypeModel> payTypeList = payTypeDao.getPayTypeList(os.getIndex(), sdkVersion);
        if (payTypeList == null || payTypeList.isEmpty()) {
            LogContext.instance().error("δ��ȡ���κ�֧����ʽ��Ϣ");
            return new ArrayList<PayTypeModel>();
        }
        LogContext.instance().info("DB��ȡ����֧���б�");
        return payTypeList;
    }

}
