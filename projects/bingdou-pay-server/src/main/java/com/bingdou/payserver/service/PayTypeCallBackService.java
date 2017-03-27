package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.model.*;
import com.bingdou.core.service.pay.*;
import com.bingdou.core.service.pay.paytype.PayTypeCallBackResponse;
import com.bingdou.core.service.user.UserBaseService;
import com.bingdou.core.service.user.UserStatisticsService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.CreateConsumeOrderRequest;
import com.bingdou.payserver.request.CreateRechargeOrderRequest;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.HadoopLogAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付方式回调服务类
 * Created by gaoshan on 16/3/11.
 */
@Service
public class PayTypeCallBackService {

    @Autowired
    private RechargeOrderService rechargeOrderService;
    @Autowired
    private ConsumeOrderService consumeOrderService;
    @Autowired
    private PayTypeBaseService payTypeBaseService;
    @Autowired
    private VipGradeService vipGradeService;
//    @Autowired
//    private ChargeBackService chargeBackService;
    @Autowired
    private UserBaseService userBaseService;
//    @Autowired
//    private VoucherService voucherService;
//    @Autowired
//    private CallBackCpServerService callBackCpServerService;
    @Autowired
    private AppBaseService appBaseService;
    @Autowired
    private UserStatisticsService userStatisticsService;

    @Transactional(rollbackFor = Exception.class)
    public boolean commonRecharge(PayTypeCallBackResponse response) throws Exception {
        return dealCommon(response, true);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean commonConsume(PayTypeCallBackResponse response) throws Exception {
        return dealCommon(response, false);
    }

    private boolean dealCommon(PayTypeCallBackResponse response, boolean isRecharge) throws Exception {
        if (response == null) {
            LogContext.instance().error("支付回调对象为空");
            return false;
        }
        LogContext.instance().info("回调对象:" + response);
        RechargeOrder rechargeOrder = getRechargeOrder(isRecharge, response.getUserId(),
                response.getBingdouOrderId());
        boolean valid = validateRechargeOrder(response, rechargeOrder);
        if (!valid)
            return false;
        addDetailByPayType(response);
        User user = getUser(response, rechargeOrder);
        if (user == null) {
            LogContext.instance().error("用户不存在");
            return false;
        }
        LogContext.instance().info("获取用户ID(" + user.getId() + ")的VIP信息");
        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        int backMoney = 0;
        if (isRecharge) {
            LogContext.instance().info("充值回调逻辑");
            updateUserMoney(response, user.getMoney(), user.getId());
//            if (PayUtils.isSpecialActivity(rechargeOrder.getAppId(), rechargeOrder.getChannel())) {
//                LogContext.instance().info("特殊充返活动");
//                backMoney = rechargeOrder.getOrderMoney();
//            } else {
//                backMoney = chargeBackService.calculateBackMoneyFen4CallBack(rechargeOrder.getActivityType(),
//                        user.getId(), response.getAmount(), rechargeOrder.getPropId(), rechargeOrder.getAppId(),
//                        userVipGrade.isInBlackList(), rechargeOrder.getOrderId());
//            }
            addBackMoney(backMoney, response, rechargeOrder);
        } else {
            LogContext.instance().info("直充回调逻辑");
            LogContext.instance().info("处理组合消费逻辑");
            List<UserMoneyOrder> userMoneyOrderList = consumeOrderService.getUnDoneUserMoneyOrderList(user.getId(),
                    response.getBingdouOrderId());
            boolean isContainUserMoney = userMoneyOrderList != null && !userMoneyOrderList.isEmpty();
            boolean isDealSuccess = true;
            if (isContainUserMoney) {
                isDealSuccess = dealUserMoneyOrder(user, rechargeOrder, response.getBingdouOrderId(),
                        userMoneyOrderList);
            }
            if (isDealSuccess) {
                updateConsumeOrder2Success(response.getAmount(), response.getBingdouOrderId());
                int voucherId = dealVoucher(response.getBingdouOrderId());
                ConsumeOrder consumeOrder = consumeOrderService.getOrderByOrderId(response.getBingdouOrderId());
                Application application = appBaseService.getAppByAppId(consumeOrder.getAppId());
                userStatisticsService.recordUserActiveRecord4PayCallBackCp(user.getId(), application,
                        consumeOrder.getConsumeMoney());
                recordConsumeServerLog(user, voucherId, consumeOrder);
//                callBackCpServerService.callBack(consumeOrder.getOrderId(), application);
            }
        }
        vipGradeService.dealVip(response.getAmount(), user.getId(), userVipGrade);
        updateRechargeOrder2Success(response.getAmount(), backMoney, rechargeOrder.getOrderId(),
                rechargeOrder.getActivityType());
        rechargeOrder = rechargeOrderService.getByOrderId(rechargeOrder.getOrderId());
        recordRechargeServerLog(user, rechargeOrder.getOrderId());
        return true;
    }

    private boolean validateRechargeOrder(PayTypeCallBackResponse response, RechargeOrder rechargeOrder)
            throws Exception {
        if (rechargeOrder == null) {
            LogContext.instance().error("不存在此订单");
            return false;
        }
        if (rechargeOrder.getPayType() != response.getPayType().getIndex()) {
            LogContext.instance().error("支付方式错误");
            return false;
        }
        //TODO 微信汇源有可能会金额不一致
        if (rechargeOrder.getOrderMoney() != response.getAmount()) {
            LogContext.instance().error("此订单金额和回调金额不一致");
            return false;
        }
        if (rechargeOrder.getStatus() == OrderStatus.PAYED.getIndex()) {
            LogContext.instance().error("此订单已经完成,不需要回调");
            return true;
        }
        return true;
    }

    private void addDetailByPayType(PayTypeCallBackResponse response) throws Exception {
        LogContext.instance().info("记录支付方式订单详情");
        if (PayType.ALI_SCAN.equals(response.getPayType())
                || PayType.ALI_MOBILE.equals(response.getPayType())
                || PayType.ALI_NO_PWD.equals(response.getPayType())) {
            payTypeBaseService.addAliOrderDetail(response.getParamMap());
        } else if (PayType.WEIXIN.equals(response.getPayType()) || PayType.PUBLIC_WEIXIN.equals(response.getPayType())) {
            payTypeBaseService.addWeixinOrderDetail(response.getParamMap());
        } else {
            throw new Exception("未知支付方式");
        }
    }

    private void updateRechargeOrder2Success(int moneyFen, int backMoneyFen, String orderId, int activityType) throws Exception {
        LogContext.instance().info("更新充值订单");
        rechargeOrderService.updateRechargeOrder(moneyFen, backMoneyFen, orderId, activityType);
    }

    private void updateConsumeOrder2Success(int moneyFen, String orderId) throws Exception {
        LogContext.instance().info("更新消费订单");
        consumeOrderService.updateConsumeOrder(moneyFen, orderId);
    }

    private void updateUserMoney(PayTypeCallBackResponse response, int userMoneyFen, int userId)
            throws Exception {
        LogContext.instance().info("更新用户冰豆币账户及资金变动日志");
        MoneyLog rechargeMoneyLog = PayUtils.buildRechargeMoneyLog(response.getBingdouOrderId(),
                response.getPayType(), userId, response.getAmount(), userMoneyFen);
        userBaseService.updateMoneyById(userId, userMoneyFen, rechargeMoneyLog);
    }

    private void recordRechargeServerLog(User user, String orderId) {
        LogContext.instance().info("记录充值订单完成的服务器统计日志");
        RechargeOrder rechargeOrder = rechargeOrderService.getByOrderId(orderId);
        BaseRequest baseRequest = new CreateRechargeOrderRequest();
        baseRequest.setAppId(rechargeOrder.getAppId());
        baseRequest.setChannel(rechargeOrder.getChannel());
        DataLogUtils.recordHadoopLog(HadoopLogAction.RECHARGE_ORDER_SUCCESS, baseRequest,
                user, "", "", rechargeOrder, true, false);
    }

    private void recordConsumeServerLog(User user, int voucherId, ConsumeOrder consumeOrder) {
        LogContext.instance().info("记录消费订单完成的服务器统计日志");
        BaseRequest baseRequest = new CreateConsumeOrderRequest();
        baseRequest.setAppId(consumeOrder.getAppId());
        baseRequest.setChannel(consumeOrder.getChannel());
        DataLogUtils.recordHadoopLog(HadoopLogAction.CONSUME_ORDER_SUCCESS, baseRequest,
                user, "", voucherId + "", consumeOrder, false, voucherId > 0);
    }

    private RechargeOrder getRechargeOrder(boolean isRecharge, int userId, String haimaOrderId) {
        RechargeOrder rechargeOrder;
        if (isRecharge) {
            if (userId <= 0)
                rechargeOrder = rechargeOrderService.getByOrderId(haimaOrderId);
            else
                rechargeOrder = rechargeOrderService.getByOrderIdAndUserId(haimaOrderId, userId);
        } else {
            if (userId <= 0)
                rechargeOrder = rechargeOrderService.getByConsumeOrderId(haimaOrderId);
            else
                rechargeOrder = rechargeOrderService.getByConsumeOrderIdAndUserId(haimaOrderId, userId);
        }
        return rechargeOrder;
    }

    private User getUser(PayTypeCallBackResponse response, RechargeOrder rechargeOrder) {
        User user;
        if (response.getUserId() <= 0)
            user = userBaseService.getDetailById(rechargeOrder.getUserId());
        else
            user = userBaseService.getDetailById(response.getUserId());
        return user;
    }

    private void addBackMoney(int backMoneyFen, PayTypeCallBackResponse response,
                              RechargeOrder rechargeOrder) throws Exception {
        LogContext.instance().info("添加游戏币返利金额");
        int osIndex = Os.getIndexByOsName(rechargeOrder.getOsName());
        int userVirtualMoneyFen = getVirtualMoneyFen(rechargeOrder, osIndex);
        MoneyLog rechargeMoneyLog = PayUtils.buildRechargeBackMoneyLog(response.getBingdouOrderId(),
                response.getPayType(), rechargeOrder.getUserId(), userVirtualMoneyFen, backMoneyFen);
        userBaseService.insertOrUpdateVirtualMoney(rechargeOrder.getUserId(), userVirtualMoneyFen,
                osIndex, rechargeMoneyLog);
    }

    private int getVirtualMoneyFen(Order order, int osIndex) {
        LogContext.instance().info("获取游戏币金额");
        return userBaseService.getVirtualMoney(order.getUserId(), osIndex, false);
    }

    private int dealVoucher(String consumeOrderId) {
//        Voucher voucher = voucherService.getVoucherUserOrderByOrderId(consumeOrderId);
//        if (voucher == null) {
//            LogContext.instance().error(consumeOrderId + "订单的消费代金券为空");
//        } else {
//            boolean convertPre2UsedResult = voucherService.convertPre2Used(voucher.getId());
//            if (convertPre2UsedResult) {
//                LogContext.instance().info("消费代金券(" + voucher.getId() + ")改变状态成功");
//            } else {
//                LogContext.instance().error("消费代金券(" + voucher.getId() + ")改变状态失败");
//            }
//        }
        int voucherId = -1;
//        if (voucher != null)
//            voucherId = voucher.getId();
        return voucherId;
    }

    private boolean dealUserMoneyOrder(User user, RechargeOrder rechargeOrder, String consumeOrderId,
                                       List<UserMoneyOrder> userMoneyOrderList) throws Exception {
        int osIndex = Os.getIndexByOsName(rechargeOrder.getOsName());
        int virtualMoneyFen = getVirtualMoneyFen(rechargeOrder, osIndex);
        boolean isNotEnough = false;
        int needReduceUserMoneyFen = 0;
        int needReduceVirtualMoneyFen = 0;
        for (UserMoneyOrder userMoneyOrder : userMoneyOrderList) {
            if (MoneyType.BINGDOU_MONEY.getIndex() == userMoneyOrder.getMoneyType()) {
                if (userMoneyOrder.getMoneyFen() > user.getMoney()) {
                    LogContext.instance().info("用户余额不足");
                    isNotEnough = true;
                    break;
                }
                needReduceUserMoneyFen = userMoneyOrder.getMoneyFen();
            }
            if (MoneyType.VIRTUAL_MONEY.getIndex() == userMoneyOrder.getMoneyType()) {
                if (userMoneyOrder.getMoneyFen() > virtualMoneyFen) {
                    LogContext.instance().info("用户游戏币不足");
                    isNotEnough = true;
                    break;
                }
                needReduceVirtualMoneyFen = userMoneyOrder.getMoneyFen();
            }
        }
        if (isNotEnough) {
            LogContext.instance().error("用户余额不足,不下发CP道具,将把现金部分加入到海马币余额中");
            userBaseService.updateMoneyById(user.getId(), user.getMoney(),
                    PayUtils.buildRechargeMoneyLog(rechargeOrder.getOrderId(),
                            PayType.getByIndex(rechargeOrder.getPayType()), user.getId(),
                            rechargeOrder.getOrderMoney(), user.getMoney()));
            return false;
        } else {
            LogContext.instance().info("用户余额充足,将扣除相应币种金额");
            userBaseService.updateMoneyById(user.getId(), user.getMoney(),
                    PayUtils.buildConsumeMoneyLog(user.getId(), consumeOrderId,
                            needReduceUserMoneyFen, user.getMoney() - needReduceUserMoneyFen, true));
            userBaseService.insertOrUpdateVirtualMoney(user.getId(), virtualMoneyFen, osIndex,
                    PayUtils.buildConsumeMoneyLog(user.getId(), consumeOrderId,
                            needReduceVirtualMoneyFen, virtualMoneyFen - needReduceVirtualMoneyFen, false));
            boolean result = consumeOrderService.setUserMoneyOrder2Done(consumeOrderId);
            if (!result)
                throw new Exception("更新用户余额订单失败");
            return true;
        }
    }

}
