package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IPayTypeMapper;
import com.bingdou.core.model.PayTypeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PayTypeDao {

    @Autowired
    private IPayTypeMapper payTypeMapper;

    public int getPayTypeCountByIdAndOsAndVersion(int payTypeIndex, int osIndex, String sdkVersion, float money) {
        return payTypeMapper.getPayTypeCountByIdAndOsAndVersion(payTypeIndex, osIndex, sdkVersion, money);
    }

    public void insertPPOrderDetail(Map<String, String> map) {
        payTypeMapper.insertPPOrderDetail(map);
    }

    public void insertHeepayWxOrderDetail(Map<String, String> map) {
        payTypeMapper.insertHeepayWxOrderDetail(map);
    }

    public void insertAliOrderDetail(Map<String, String> map) {
        payTypeMapper.insertAliOrderDetail(map);
    }

    public void insetChinaPayUnionOrderDetail(Map<String, String> map) {
        payTypeMapper.insertChinaPayUnionOrderDetail(map);
    }

    public void insertWeixinOrderDetail(Map<String, String> map) {
        payTypeMapper.insertWeixinOrderDetail(map);
    }

    public List<PayTypeModel> getPayTypeList(int osIndex, String sdkVersion) {
        return payTypeMapper.getPayTypeList(osIndex, sdkVersion);
    }

    public void insertUpmpOrderDetail(Map<String, String> map) {
        payTypeMapper.insertUpmpOrderDetail(map);
    }

    public void insert19PayOrderDetail(Map<String, String> map) {
        payTypeMapper.insert19PayOrderDetail(map);
    }

}
