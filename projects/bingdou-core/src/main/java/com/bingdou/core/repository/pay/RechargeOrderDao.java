package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IRechargeOrderMapper;
import com.bingdou.core.model.RechargeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RechargeOrderDao {

    @Autowired
    private IRechargeOrderMapper rechargeOrderMapper;

    public void addRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrderMapper.addRechargeOrder(rechargeOrder);
    }

    public int getRechargeOrderCountByOrderId(String orderId) {
        return rechargeOrderMapper.getRechargeOrderCountByOrderId(orderId);
    }

    public List<RechargeOrder> getUserRechargeOrdersLately(int userId, int limit) {
        return rechargeOrderMapper.getUserRechargeOrdersLately(userId, limit);
    }

    public void addPayedRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrderMapper.addPayedRechargeOrder(rechargeOrder);
    }

    public RechargeOrder getByOrderIdAndUserId(String orderId, int userId) {
        return rechargeOrderMapper.getByOrderIdAndUserId(orderId, userId);
    }

    public RechargeOrder getByUnionOrderIdAndUserId(String orderId, int userId) {
        return rechargeOrderMapper.getByUnionOrderIdAndUserId(orderId, userId);
    }

    public RechargeOrder getByOrderId(String orderId) {
        return rechargeOrderMapper.getByOrderId(orderId);
    }

    public int updateRechargeOrder(RechargeOrder rechargeOrder) {
        return rechargeOrderMapper.updateRechargeOrder(rechargeOrder);
    }

    public int updateRechargeOrderBackInfo(int bufferId, int ruleId, String orderId) {
        return rechargeOrderMapper.updateRechargeOrderBackInfo(bufferId, ruleId, orderId);
    }

    public int getRechargeOrderCountByOrderIdAndStatus(String orderId, int status) {
        return rechargeOrderMapper.getRechargeOrderCountByOrderIdAndStatus(orderId, status);
    }

    public RechargeOrder getByConsumeOrderIdAndUserId(String consumeOrderId, int userId) {
        return rechargeOrderMapper.getByConsumeOrderIdAndUserId(consumeOrderId, userId);
    }

    public RechargeOrder getByConsumeOrderId(String consumeOrderId) {
        return rechargeOrderMapper.getByConsumeOrderId(consumeOrderId);
    }
}
