package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.PayTypeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IPayTypeMapper {

    int getPayTypeCountByIdAndOsAndVersion(@Param("id") int payTypeIndex, @Param("os") int osIndex,
                                           @Param("sdkVersion") String sdkVersion, @Param("moneyYuan") float money);

    void insertPPOrderDetail(Map<String, String> map);

    void insertHeepayWxOrderDetail(Map<String, String> map);

    void insertAliOrderDetail(Map<String, String> map);

    void insertChinaPayUnionOrderDetail(Map<String, String> map);

    List<PayTypeModel> getPayTypeList(@Param("os") int osIndex, @Param("sdkVersion") String sdkVersion);

    void insertWeixinOrderDetail(Map<String, String> map);

    void insertUpmpOrderDetail(Map<String, String> map);

    void insert19PayOrderDetail(Map<String, String> map);
}
