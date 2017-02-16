package com.bingdou.core.service.pay;

import com.bingdou.core.model.ConsumeOrder;
import com.bingdou.core.model.MoneyType;
import com.bingdou.core.model.OrderStatus;
import com.bingdou.core.model.UserMoneyOrder;
import com.bingdou.core.repository.pay.ConsumeOrderDao;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class ConsumeOrderService {

    @Autowired
    private ConsumeOrderDao consumeOrderDao;

    public boolean existByUserOrderIdAndAppId(String userOrderId, String appId) {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(userOrderId))
            return false;
        int count = consumeOrderDao.getOrderCountByUserOrderIdAndAppId(userOrderId, appId);
        return count > 0;
    }

    public boolean addConsumeOrder(ConsumeOrder consumeOrder) {
        if (consumeOrder == null)
            return false;
        int count = consumeOrderDao.getCountByOrderId(consumeOrder.getOrderId());
        if (count > 0) {
            LogContext.instance().warn("存在" + consumeOrder.getOrderId() + "的订单");
            return false;
        }
        consumeOrderDao.addConsumeOrder(consumeOrder);
        return true;
    }

    public ConsumeOrder getOrderByUserOrderIdAndAppId(String userOrderId, String appId) {
        if (StringUtils.isEmpty(userOrderId) || StringUtils.isEmpty(appId))
            return null;
        return consumeOrderDao.getOrderByUserOrderIdAndAppId(userOrderId, appId);
    }

    public int getCountByUserIdAndDate(int userId, long beginTime, long endTime) {
        if (userId <= 0 || beginTime <= 0 || endTime <= 0)
            return 0;
        return consumeOrderDao.getCountByUserIdAndDate(userId, beginTime, endTime);
    }

    public boolean isConsume(int userId, String beginDateStr, String endDateStr) throws ParseException {
        Date beginDate = DateUtils.parseDate(beginDateStr, DateUtil.YYYY_MM_DD);
        Date endDate = DateUtils.parseDate(endDateStr + " 23:59:59",
                DateUtil.YYYY_MM_DD_HH_MM_SS);
        int count = getCountByUserIdAndDate(userId,
                beginDate.getTime() / 1000, endDate.getTime() / 1000);
        return count > 0;
    }

    public List<ConsumeOrder> getUserConsumeOrdersLately(int userId, int limit) {
        if (userId < 0 || limit <= 0) {
            return null;
        }
        return consumeOrderDao.getUserConsumeOrdersLately(userId, limit);
    }

    public void updateConsumeOrder(int moneyFen, String orderId) throws Exception {
        ConsumeOrder consumeOrder = new ConsumeOrder();
        consumeOrder.setOrderId(orderId);
        consumeOrder.setPayedMoney(moneyFen);
        consumeOrder.setStatus(OrderStatus.PAYED.getIndex());
        consumeOrder.setPayedTime(DateUtil.getCurrentTimeSeconds());
        int count = consumeOrderDao.updateConsumeOrder(consumeOrder);
        if (count < 1)
            throw new Exception("更新消费订单状态失败");
    }

    public ConsumeOrder getOrderByOrderId(String orderId) {
        if (StringUtils.isEmpty(orderId))
            return null;
        return consumeOrderDao.getOrderByOrderId(orderId);
    }

    public void insertUserMoneyOrderCreate(int userId, String orderId, int moneyFen, int virtualMoneyFen) throws Exception {
        insertUserMoneyOrder(userId, orderId, moneyFen, virtualMoneyFen, 0);
    }

    public void insertUserMoneyOrderDone(int userId, String orderId, int moneyFen, int virtualMoneyFen) throws Exception {
        insertUserMoneyOrder(userId, orderId, moneyFen, virtualMoneyFen, 1);
    }

    public List<UserMoneyOrder> getUnDoneUserMoneyOrderList(int userId, String orderId) {
        if (userId <= 0 || StringUtils.isEmpty(orderId))
            return null;
        return consumeOrderDao.getUnDoneUserMoneyOrderList(userId, orderId);
    }

    public boolean setUserMoneyOrder2Done(String orderId) {
        if (StringUtils.isEmpty(orderId))
            return false;
        int count = consumeOrderDao.updateUserMoneyOrderByOrderId(orderId);
        return count > 0;
    }

    public int getStatusByUserOrderIdAndUserId(int userId, String userOrderId) {
        if (userId <= 0 || StringUtils.isEmpty(userOrderId))
            return OrderStatus.NOT_EXISTS.getIndex();
        Integer status = consumeOrderDao.getStatusByUserOrderIdAndUserId(userId, userOrderId);
        return status == null ? OrderStatus.NOT_EXISTS.getIndex() : status;
    }

    public boolean isNoPayOrder(String orderId) {
        if (StringUtils.isEmpty(orderId))
            return false;
        int count = consumeOrderDao.getCountByOrderIdAndStatus(orderId,
                OrderStatus.NOT_PAY.getIndex());
        return count > 0;
    }

    private void insertUserMoneyOrder(int userId, String orderId, int moneyFen, int virtualMoneyFen,
                                      int status) throws Exception {
        LogContext.instance().info("插入账户金额订单消费记录");
        if (userId <= 0 || StringUtils.isEmpty(orderId))
            throw new Exception("插入账户金额订单消费记录失败");
        if (virtualMoneyFen > 0) {
            consumeOrderDao.insertUserMoneyOrder(userId, orderId, MoneyType.VIRTUAL_MONEY.getIndex(),
                    virtualMoneyFen, status);
            LogContext.instance().info("插入游戏币消费记录成功");
        }
        if (moneyFen > 0) {
            consumeOrderDao.insertUserMoneyOrder(userId, orderId, MoneyType.BINGDOU_MONEY.getIndex(),
                    moneyFen, status);
            LogContext.instance().info("插入冰豆币消费记录成功");
        }
        LogContext.instance().info("插入账户金额订单消费记录成功");
    }

}
