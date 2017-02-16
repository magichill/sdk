package com.bingdou.core.service.pay;

//import com.bingdou.core.model.ActivityType;
import com.bingdou.core.model.OrderStatus;
import com.bingdou.core.model.RechargeOrder;
import com.bingdou.core.repository.pay.RechargeOrderDao;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 充值订单服务类
 * Created by gaoshan on 17/02/01.
 */
@Service
public class RechargeOrderService {

    @Autowired
    private RechargeOrderDao rechargeOrderDao;

    public boolean addRechargeOrder(RechargeOrder rechargeOrder) throws Exception {
        if (!checkOrderExists(rechargeOrder)) {
            return false;
        }
        rechargeOrderDao.addRechargeOrder(rechargeOrder);
        return true;
    }

    public boolean addPayedRechargeOrder(RechargeOrder rechargeOrder) throws Exception {
        if (!checkOrderExists(rechargeOrder)) {
            return false;
        }
        rechargeOrderDao.addPayedRechargeOrder(rechargeOrder);
        return true;
    }

    public boolean existOrderId(String orderId) throws Exception {
        if (StringUtils.isEmpty(orderId))
            throw new Exception("充值ID为空");
        int count = rechargeOrderDao.getRechargeOrderCountByOrderId(orderId);
        return count > 0;
    }

    public List<RechargeOrder> getUserRechargeOrdersLately(int userId, int limit) {
        if (userId < 0 || limit <= 0) {
            return null;
        }
        return rechargeOrderDao.getUserRechargeOrdersLately(userId, limit);
    }

    public RechargeOrder getByOrderIdAndUserId(String orderId, int userId) {
        if (StringUtils.isEmpty(orderId) || userId <= 0)
            return null;
        return rechargeOrderDao.getByOrderIdAndUserId(orderId, userId);
    }

    public RechargeOrder getByOrderId(String orderId) {
        if (StringUtils.isEmpty(orderId))
            return null;
        return rechargeOrderDao.getByOrderId(orderId);
    }

    public boolean isNoPayOrder(String orderId) {
        if (StringUtils.isEmpty(orderId))
            return false;
        int count = rechargeOrderDao.getRechargeOrderCountByOrderIdAndStatus(orderId,
                OrderStatus.NOT_PAY.getIndex());
        return count > 0;
    }

    public void updateRechargeOrderBackInfo(int ruleId, String orderId, int bufferId)
            throws Exception {
        if (StringUtils.isEmpty(orderId))
            throw new Exception("更新订单返利信息失败");
        if (ruleId == 0 && bufferId == 0) {
            LogContext.instance().info("规则ID和BUFF ID都为0,不需要更新订单返利信息");
            return;
        }
        int count = rechargeOrderDao.updateRechargeOrderBackInfo(bufferId, ruleId, orderId);
        if (count < 1)
            throw new Exception("更新订单返利信息失败");
    }

    public void updateRechargeOrder(int moneyFen, int backMoneyFen, String orderId, int activityType) throws Exception {
        if (moneyFen < 0 || backMoneyFen < 0 || StringUtils.isEmpty(orderId))
            throw new Exception("更新订单状态失败");
        long now = DateUtil.getCurrentTimeSeconds();
        RechargeOrder rechargeOrder = new RechargeOrder();
        rechargeOrder.setOrderId(orderId);
        rechargeOrder.setStatus(OrderStatus.PAYED.getIndex());
        rechargeOrder.setPayedMoney(moneyFen);
        rechargeOrder.setPayedTime(now);
        rechargeOrder.setVerify(OrderStatus.PAYED.getIndex());
        rechargeOrder.setVerifyMoney(moneyFen);
        rechargeOrder.setVerifyTime(now);
        rechargeOrder.setVerifyMessage("充值成功");
        rechargeOrder.setBackMoney(backMoneyFen);
//        if (backMoneyFen > 0) {
//            int isAct = ActivityType.CARD.getIndex() == activityType ? 1 : 2;
//            rechargeOrder.setIsAct(isAct);
//        }
        int count = rechargeOrderDao.updateRechargeOrder(rechargeOrder);
        if (count < 1)
            throw new Exception("更新充值订单状态失败");
    }

    public RechargeOrder getByConsumeOrderIdAndUserId(String consumeOrderId, int userId) {
        if (StringUtils.isEmpty(consumeOrderId) || userId <= 0)
            return null;
        return rechargeOrderDao.getByConsumeOrderIdAndUserId(consumeOrderId, userId);
    }

    public RechargeOrder getByConsumeOrderId(String consumeOrderId) {
        if (StringUtils.isEmpty(consumeOrderId))
            return null;
        return rechargeOrderDao.getByConsumeOrderId(consumeOrderId);
    }

    public RechargeOrder getByUnionOrderIdAndUserId(String orderId, int userId) {
        if (StringUtils.isEmpty(orderId) || userId <= 0)
            return null;
        return rechargeOrderDao.getByUnionOrderIdAndUserId(orderId, userId);
    }

    private boolean checkOrderExists(RechargeOrder rechargeOrder) throws Exception {
        if (rechargeOrder == null)
            return false;
        boolean exist = existOrderId(rechargeOrder.getOrderId());
        if (exist) {
            LogContext.instance().warn("存在" + rechargeOrder.getOrderId() + "的订单,创建失败");
            return false;
        }
        return true;
    }

}
