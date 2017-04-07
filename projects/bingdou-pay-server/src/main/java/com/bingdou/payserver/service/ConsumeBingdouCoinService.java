package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.ConsumeOrderService;
import com.bingdou.core.service.user.UserStatisticsService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.ConsumeBingdouCoinRequest;
import com.bingdou.payserver.response.ConsumeBingdouCoinResponse;
import com.bingdou.tools.*;
import com.bingdou.tools.constants.HadoopLogAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class ConsumeBingdouCoinService extends BaseService implements IMethodService {

    @Autowired
    private ConsumeOrderService consumeOrderService;
//    @Autowired
//    private VoucherService voucherService;
//    @Autowired
//    private CallBackCpServerService callBackCpServerService;
    @Autowired
    private UserStatisticsService userStatisticsService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ConsumeBingdouCoinRequest consumeBingdouCoinRequest = new ConsumeBingdouCoinRequest();
        consumeBingdouCoinRequest.parseRequest(request);
        return consumeBingdouCoinRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }


    @Override
    public User getUser(BaseRequest baseRequest) {
        ConsumeBingdouCoinRequest consumeBingdouCoinRequest = (ConsumeBingdouCoinRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(consumeBingdouCoinRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "consume_bingdou_coin";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        Application application = appBaseService.getAppByAppId(baseRequest.getAppId());
        return deal(baseRequest, application, user, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        Application application = getValidApplication4Client(baseRequest);
        return deal(baseRequest, application, user, request);
    }

    private ServiceResult deal(BaseRequest baseRequest, Application application, User user,
                               HttpServletRequest request) throws Exception {
        ConsumeBingdouCoinRequest consumeBingdouCoinRequest = (ConsumeBingdouCoinRequest) baseRequest;
        if (StringUtils.isEmpty(consumeBingdouCoinRequest.getGoodsName())
                || StringUtils.isEmpty(consumeBingdouCoinRequest.getUserOrderId())) {
            return ServiceResultUtil.illegal("请求参数有误");
        }
        String channel;
        boolean isUseConsumeVoucher = false;
        boolean isFromClient = isClientRequest(request);
        if (isFromClient) {
            channel = consumeBingdouCoinRequest.getOtherInfo().getChannel();
        } else {
            channel = consumeBingdouCoinRequest.getChannel();
        }
        if (consumeOrderService.existByUserOrderIdAndAppId(consumeBingdouCoinRequest.getUserOrderId(),
                application.getAppId()))
            return ServiceResultUtil.illegal("开发者订单号重复,消费失败");
        int orderMoneyFen = consumeBingdouCoinRequest.getOrderMoneyFen();
        if (!isUseConsumeVoucher && orderMoneyFen == 0)
            return ServiceResultUtil.illegal("非法消费金额");
        int virtualMoneyFen = getVirtualMoneyFen4Use(user.getId(), application.getOs(),
                application.getAppId(), channel);
        int userTotalMoneyFen = user.getMoney() + virtualMoneyFen;
        int voucherAmountFen = 0;

        if (!isUseConsumeVoucher && orderMoneyFen > userTotalMoneyFen)
            return ServiceResultUtil.illegal("用户余额不足");
        int payedMoneyFen = getPayedMoneyFen(isUseConsumeVoucher, voucherAmountFen, orderMoneyFen);

        String orderId = PayUtils.generateConsumeOrderIdByPayType(PayType.BINGDOU_COIN);
        ConsumeOrder consumeOrder = buildConsumeOrder(user.getId(),
                application, orderMoneyFen, orderId, payedMoneyFen,
                PayType.BINGDOU_COIN, OrderStatus.PAYED, consumeBingdouCoinRequest, channel);
        boolean createSuccess = consumeOrderService.addConsumeOrder(consumeOrder);

        if (!createSuccess)
            return ServiceResultUtil.illegal("消费失败");

        int totalRemainBalanceFen = updateUserMoneyAndOrder(user, virtualMoneyFen, payedMoneyFen,
                orderMoneyFen, orderId, application.getOs());
        String clientIp = RequestUtil.getClientIp(request);
        recordCreateLog(user, clientIp, consumeBingdouCoinRequest, consumeOrder, -1);
        recordCompleteLog(user, clientIp, consumeBingdouCoinRequest, consumeOrder, -1);
        ConsumeBingdouCoinResponse response = new ConsumeBingdouCoinResponse();
        response.setMoney(totalRemainBalanceFen);
        response.setStatus(OrderStatus.PAYED.getIndex());
//        userStatisticsService.recordUserActiveRecord4PayCallBackCp(user.getId(), application, orderMoneyFen);
//        callBackCpServerService.callBack(orderId, application);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
    }

    private void updateUserAccountMoney(User user, int remainBalanceFen,
                                        int virtualRemainBalanceFen, int orderMoneyFen,
                                        int virtualMoneyOld, String orderId,
                                        int osIndex) throws Exception {
        LogContext.instance().info("更新用户账户金额");
        userBaseService.updateMoneyById(user.getId(), user.getMoney(),
                PayUtils.buildConsumeMoneyLog(user.getId(), orderId, orderMoneyFen,
                        remainBalanceFen, true));
        userBaseService.insertOrUpdateVirtualMoney(user.getId(), virtualMoneyOld, osIndex,
                PayUtils.buildConsumeMoneyLog(user.getId(), orderId, orderMoneyFen, virtualRemainBalanceFen,
                        false));
    }

    private ConsumeOrder buildConsumeOrder(int userId, Application application, int consumeMoney,
                                           String orderId, int payedMoney, PayType payType,
                                           OrderStatus orderStatus,
                                           ConsumeBingdouCoinRequest request, String channel) {
        long time = DateUtil.getCurrentTimeSeconds();
        ConsumeOrder consumeOrder = new ConsumeOrder();
        consumeOrder.setUserId(userId);
        consumeOrder.setAppId(application.getAppId());
        consumeOrder.setAppMemberId(application.getAppMemberId());
        consumeOrder.setConsumeMoney(consumeMoney);
        consumeOrder.setGoodsDescription(request.getGoodsDescription());
        consumeOrder.setGoodsName(request.getGoodsName());
        consumeOrder.setOrderId(orderId);
        consumeOrder.setUserOrderId(request.getUserOrderId());
        consumeOrder.setPayedMoney(payedMoney);
        consumeOrder.setOrderTime(time);
        consumeOrder.setPayedTime(time);
        consumeOrder.setPayType(payType.getIndex());
        consumeOrder.setUserParam("");
        consumeOrder.setStatus(orderStatus.getIndex());
        consumeOrder.setRemark("");
        consumeOrder.setRechargeOrderId("");
        consumeOrder.setChannel(channel);
        consumeOrder.setUnionPayOrderId("");
        consumeOrder.setSdkVersion("");
        consumeOrder.setOsName(Os.getOsNameByIndex(application.getOs()));
        consumeOrder.setUa("");
        consumeOrder.setUid("");
        return consumeOrder;
    }

    private void recordCreateLog(User user, String clientIp, ConsumeBingdouCoinRequest request,
                                 ConsumeOrder consumeOrder, int voucherId) {
        consumeOrder.setPayedTime(0);
        consumeOrder.setStatus(OrderStatus.NOT_PAY.getIndex());
        consumeOrder.setPayedMoney(0);
        DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_CONSUME_ORDER, request,
                user, clientIp, voucherId + "", consumeOrder, false, voucherId > 0);
    }

    private void recordCompleteLog(User user, String clientIp, ConsumeBingdouCoinRequest request,
                                   ConsumeOrder consumeOrder, int voucherId) {
        DataLogUtils.recordHadoopLog(HadoopLogAction.CONSUME_ORDER_SUCCESS, request,
                user, clientIp, voucherId + "", consumeOrder, false, voucherId > 0);
    }

    private int getPayedMoneyFen(boolean isUseConsumeVoucher, int voucherAmountFen, int orderMoneyFen) {
        int payedMoneyFen;
        if (isUseConsumeVoucher) {
            payedMoneyFen = voucherAmountFen >= orderMoneyFen ? 0 : orderMoneyFen - voucherAmountFen;
        } else {
            payedMoneyFen = orderMoneyFen;
        }
        return payedMoneyFen;
    }


    private int updateUserMoneyAndOrder(User user, int virtualMoneyFen, int payedMoneyFen,
                                        int orderMoneyFen, String orderId, int osIndex) throws Exception {
        if (payedMoneyFen == 0) {
            LogContext.instance().info("不需要支付余额,代金券金额足够");
            return virtualMoneyFen + user.getMoney();
        }
        int remainBalanceFen;
        int virtualRemainBalanceFen;
        int needReduceVirtualMoneyFen;
        int needReduceMoneyFen;
        if (virtualMoneyFen >= payedMoneyFen) {
            LogContext.instance().info("游戏币大于等于需要支付金额");
            virtualRemainBalanceFen = virtualMoneyFen - payedMoneyFen;
            remainBalanceFen = user.getMoney();
            needReduceVirtualMoneyFen = payedMoneyFen;
            needReduceMoneyFen = 0;
        } else {
            LogContext.instance().info("游戏币小于需要支付金额");
            virtualRemainBalanceFen = 0;
            remainBalanceFen = user.getMoney() - payedMoneyFen + virtualMoneyFen;
            needReduceVirtualMoneyFen = virtualMoneyFen;
            needReduceMoneyFen = payedMoneyFen - virtualMoneyFen;
        }
        LogContext.instance().info("需要消耗的冰豆币(分):" + needReduceMoneyFen);
        LogContext.instance().info("需要消耗的游戏币(分):" + needReduceVirtualMoneyFen);
        updateUserAccountMoney(user, remainBalanceFen, virtualRemainBalanceFen, orderMoneyFen,
                virtualMoneyFen, orderId, osIndex);
        consumeOrderService.insertUserMoneyOrderDone(user.getId(), orderId,
                needReduceMoneyFen, needReduceVirtualMoneyFen);
        LogContext.instance().info("消费后用户冰豆币余额(分):" + remainBalanceFen);
        LogContext.instance().info("消费后用户游戏币余额(分):" + virtualRemainBalanceFen);
        return remainBalanceFen + virtualRemainBalanceFen;
    }

}
