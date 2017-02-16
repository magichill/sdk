package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.ConsumeOrder;
import com.bingdou.core.model.UserMoneyOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IConsumeOrderMapper {

    int getOrderCountByUserOrderIdAndAppId(@Param("userOrderId") String userOrderId,
                                           @Param("appId") String appId);

    void addConsumeOrder(ConsumeOrder consumeOrder);

    ConsumeOrder getOrderByUserOrderIdAndAppId(@Param("userOrderId") String userOrderId,
                                               @Param("appId") String appId);

    int getCountByOrderId(String orderId);

    int getCountByUserIdAndDate(@Param("userId") int userId,
                                @Param("beginTime") long beginTime, @Param("endTime") long endTime);

    List<ConsumeOrder> getUserConsumeOrdersLately(@Param("userId") int userId, @Param("limit") int limit);

    int updateConsumeOrder(ConsumeOrder consumeOrder);

    ConsumeOrder getOrderByOrderId(String orderId);

    void insertUserMoneyOrder(@Param("userId") int userId, @Param("orderId") String orderId,
                              @Param("moneyType") int moneyType, @Param("moneyFen") int moneyFen,
                              @Param("status") int status);

    List<UserMoneyOrder> getUnDoneUserMoneyOrderList(@Param("userId") int userId, @Param("orderId") String orderId);

    int updateUserMoneyOrderByOrderId(String orderId);

    Integer getStatusByUserOrderIdAndUserId(@Param("userId") int userId, @Param("userOrderId") String userOrderId);

    int getCountByOrderIdAndStatus(@Param("orderId") String orderId, @Param("status") int status);

}
