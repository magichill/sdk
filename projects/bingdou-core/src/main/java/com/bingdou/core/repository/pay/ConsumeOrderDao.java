package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IConsumeOrderMapper;
import com.bingdou.core.model.ConsumeOrder;
import com.bingdou.core.model.UserMoneyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConsumeOrderDao {

    @Autowired
    private IConsumeOrderMapper consumeOrderMapper;

    public int getOrderCountByUserOrderIdAndAppId(String userOrderId, String appId) {
        return consumeOrderMapper.getOrderCountByUserOrderIdAndAppId(userOrderId, appId);
    }

    public void addConsumeOrder(ConsumeOrder consumeOrder) {
        consumeOrderMapper.addConsumeOrder(consumeOrder);
    }

    public ConsumeOrder getOrderByUserOrderIdAndAppId(String userOrderId, String appId) {
        return consumeOrderMapper.getOrderByUserOrderIdAndAppId(userOrderId, appId);
    }

    public int getCountByOrderId(String orderId) {
        return consumeOrderMapper.getCountByOrderId(orderId);
    }

    public int getCountByUserIdAndDate(int userId, long beginTime, long endTime) {
        return consumeOrderMapper.getCountByUserIdAndDate(userId, beginTime, endTime);
    }

    public List<ConsumeOrder> getUserConsumeOrdersLately(int userId, int limit) {
        return consumeOrderMapper.getUserConsumeOrdersLately(userId, limit);
    }

    public int updateConsumeOrder(ConsumeOrder consumeOrder) {
        return consumeOrderMapper.updateConsumeOrder(consumeOrder);
    }

    public ConsumeOrder getOrderByOrderId(String orderId) {
        return consumeOrderMapper.getOrderByOrderId(orderId);
    }

    public void insertUserMoneyOrder(int userId, String orderId, int moneyType, int moneyFen, int status) {
        consumeOrderMapper.insertUserMoneyOrder(userId, orderId, moneyType, moneyFen, status);
    }

    public List<UserMoneyOrder> getUnDoneUserMoneyOrderList(int userId, String orderId) {
        return consumeOrderMapper.getUnDoneUserMoneyOrderList(userId, orderId);
    }

    public int updateUserMoneyOrderByOrderId(String orderId) {
        return consumeOrderMapper.updateUserMoneyOrderByOrderId(orderId);
    }

    public Integer getStatusByUserOrderIdAndUserId(int userId, String userOrderId) {
        return consumeOrderMapper.getStatusByUserOrderIdAndUserId(userId, userOrderId);
    }

    public int getCountByOrderIdAndStatus(String orderId, int status) {
        return consumeOrderMapper.getCountByOrderIdAndStatus(orderId, status);
    }

}
