package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.RechargeOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRechargeOrderMapper {

    void addRechargeOrder(RechargeOrder rechargeOrder);

    int getRechargeOrderCountByOrderId(String orderId);

    List<RechargeOrder> getUserRechargeOrdersLately(@Param("userId") int userId, @Param("limit") int limit);

    void addPayedRechargeOrder(RechargeOrder rechargeOrder);

    RechargeOrder getByOrderIdAndUserId(@Param("orderId") String orderId, @Param("userId") int userId);

    RechargeOrder getByUnionOrderIdAndUserId(@Param("orderId") String orderId, @Param("userId") int userId);

    RechargeOrder getByOrderId(String orderId);

    RechargeOrder getByConsumeOrderIdAndUserId(@Param("consumeOrderId") String consumeOrderId,
                                               @Param("userId") int userId);

    RechargeOrder getByConsumeOrderId(String consumeOrderId);

    int updateRechargeOrder(RechargeOrder rechargeOrder);

    int updateRechargeOrderBackInfo(@Param("bufferId") int bufferId, @Param("ruleId") int ruleId,
                                    @Param("orderId") String orderId);

    int getRechargeOrderCountByOrderIdAndStatus(@Param("orderId") String orderId, @Param("status") int status);

}
