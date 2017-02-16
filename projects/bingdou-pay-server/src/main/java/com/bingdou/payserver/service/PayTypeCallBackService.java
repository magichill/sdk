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
 * ֧����ʽ�ص�������
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
            LogContext.instance().error("֧���ص�����Ϊ��");
            return false;
        }
        LogContext.instance().info("�ص�����:" + response);
        RechargeOrder rechargeOrder = getRechargeOrder(isRecharge, response.getUserId(),
                response.getBingdouOrderId());
        boolean valid = validateRechargeOrder(response, rechargeOrder);
        if (!valid)
            return false;
        addDetailByPayType(response);
        User user = getUser(response, rechargeOrder);
        if (user == null) {
            LogContext.instance().error("�û�������");
            return false;
        }
        LogContext.instance().info("��ȡ�û�ID(" + user.getId() + ")��VIP��Ϣ");
        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        int backMoney = 0;
        if (isRecharge) {
            LogContext.instance().info("��ֵ�ص��߼�");
            updateUserMoney(response, user.getMoney(), user.getId());
            if (PayUtils.isSpecialActivity(rechargeOrder.getAppId(), rechargeOrder.getChannel())) {
                LogContext.instance().info("����䷵�");
                backMoney = rechargeOrder.getOrderMoney();
            } else {
//                backMoney = chargeBackService.calculateBackMoneyFen4CallBack(rechargeOrder.getActivityType(),
//                        user.getId(), response.getAmount(), rechargeOrder.getPropId(), rechargeOrder.getAppId(),
//                        userVipGrade.isInBlackList(), rechargeOrder.getOrderId());
            }
            addBackMoney(backMoney, response, rechargeOrder);
        } else {
            LogContext.instance().info("ֱ��ص��߼�");
            LogContext.instance().info("������������߼�");
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
            LogContext.instance().error("�����ڴ˶���");
            return false;
        }
        if (rechargeOrder.getPayType() != response.getPayType().getIndex()) {
            LogContext.instance().error("֧����ʽ����");
            return false;
        }
        //TODO ΢�Ż�Դ�п��ܻ��һ��
        if (rechargeOrder.getOrderMoney() != response.getAmount()) {
            LogContext.instance().error("�˶������ͻص���һ��");
            return false;
        }
        if (rechargeOrder.getStatus() == OrderStatus.PAYED.getIndex()) {
            LogContext.instance().error("�˶����Ѿ����,����Ҫ�ص�");
            return true;
        }
        return true;
    }

    private void addDetailByPayType(PayTypeCallBackResponse response) throws Exception {
        LogContext.instance().info("��¼֧����ʽ��������");
        if (PayType.ALI_SCAN.equals(response.getPayType())
                || PayType.ALI_MOBILE.equals(response.getPayType())
                || PayType.ALI_NO_PWD.equals(response.getPayType())) {
            payTypeBaseService.addAliOrderDetail(response.getParamMap());
        } else if (PayType.WEIXIN.equals(response.getPayType())) {
            payTypeBaseService.addWeixinOrderDetail(response.getParamMap());
        } else {
            throw new Exception("δ֪֧����ʽ");
        }
    }

    private void updateRechargeOrder2Success(int moneyFen, int backMoneyFen, String orderId, int activityType) throws Exception {
        LogContext.instance().info("���³�ֵ����");
        rechargeOrderService.updateRechargeOrder(moneyFen, backMoneyFen, orderId, activityType);
    }

    private void updateConsumeOrder2Success(int moneyFen, String orderId) throws Exception {
        LogContext.instance().info("�������Ѷ���");
        consumeOrderService.updateConsumeOrder(moneyFen, orderId);
    }

    private void updateUserMoney(PayTypeCallBackResponse response, int userMoneyFen, int userId)
            throws Exception {
        LogContext.instance().info("�����û�������˻����ʽ�䶯��־");
        MoneyLog rechargeMoneyLog = PayUtils.buildRechargeMoneyLog(response.getBingdouOrderId(),
                response.getPayType(), userId, response.getAmount(), userMoneyFen);
        userBaseService.updateMoneyById(userId, userMoneyFen, rechargeMoneyLog);
    }

    private void recordRechargeServerLog(User user, String orderId) {
        LogContext.instance().info("��¼��ֵ������ɵķ�����ͳ����־");
        RechargeOrder rechargeOrder = rechargeOrderService.getByOrderId(orderId);
        BaseRequest baseRequest = new CreateRechargeOrderRequest();
        baseRequest.setAppId(rechargeOrder.getAppId());
        baseRequest.setChannel(rechargeOrder.getChannel());
        DataLogUtils.recordHadoopLog(HadoopLogAction.RECHARGE_ORDER_SUCCESS, baseRequest,
                user, "", "", rechargeOrder, true, false);
    }

    private void recordConsumeServerLog(User user, int voucherId, ConsumeOrder consumeOrder) {
        LogContext.instance().info("��¼���Ѷ�����ɵķ�����ͳ����־");
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
        LogContext.instance().info("�����Ϸ�ҷ������");
        int osIndex = Os.getIndexByOsName(rechargeOrder.getOsName());
        int userVirtualMoneyFen = getVirtualMoneyFen(rechargeOrder, osIndex);
        MoneyLog rechargeMoneyLog = PayUtils.buildRechargeBackMoneyLog(response.getBingdouOrderId(),
                response.getPayType(), rechargeOrder.getUserId(), userVirtualMoneyFen, backMoneyFen);
        userBaseService.insertOrUpdateVirtualMoney(rechargeOrder.getUserId(), userVirtualMoneyFen,
                osIndex, rechargeMoneyLog);
    }

    private int getVirtualMoneyFen(Order order, int osIndex) {
        LogContext.instance().info("��ȡ��Ϸ�ҽ��");
        return userBaseService.getVirtualMoney(order.getUserId(), osIndex, false);
    }

    private int dealVoucher(String consumeOrderId) {
//        Voucher voucher = voucherService.getVoucherUserOrderByOrderId(consumeOrderId);
//        if (voucher == null) {
//            LogContext.instance().error(consumeOrderId + "���������Ѵ���ȯΪ��");
//        } else {
//            boolean convertPre2UsedResult = voucherService.convertPre2Used(voucher.getId());
//            if (convertPre2UsedResult) {
//                LogContext.instance().info("���Ѵ���ȯ(" + voucher.getId() + ")�ı�״̬�ɹ�");
//            } else {
//                LogContext.instance().error("���Ѵ���ȯ(" + voucher.getId() + ")�ı�״̬ʧ��");
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
                    LogContext.instance().info("�û�����");
                    isNotEnough = true;
                    break;
                }
                needReduceUserMoneyFen = userMoneyOrder.getMoneyFen();
            }
            if (MoneyType.VIRTUAL_MONEY.getIndex() == userMoneyOrder.getMoneyType()) {
                if (userMoneyOrder.getMoneyFen() > virtualMoneyFen) {
                    LogContext.instance().info("�û���Ϸ�Ҳ���");
                    isNotEnough = true;
                    break;
                }
                needReduceVirtualMoneyFen = userMoneyOrder.getMoneyFen();
            }
        }
        if (isNotEnough) {
            LogContext.instance().error("�û�����,���·�CP����,�����ֽ𲿷ּ��뵽����������");
            userBaseService.updateMoneyById(user.getId(), user.getMoney(),
                    PayUtils.buildRechargeMoneyLog(rechargeOrder.getOrderId(),
                            PayType.getByIndex(rechargeOrder.getPayType()), user.getId(),
                            rechargeOrder.getOrderMoney(), user.getMoney()));
            return false;
        } else {
            LogContext.instance().info("�û�������,���۳���Ӧ���ֽ��");
            userBaseService.updateMoneyById(user.getId(), user.getMoney(),
                    PayUtils.buildConsumeMoneyLog(user.getId(), consumeOrderId,
                            needReduceUserMoneyFen, user.getMoney() - needReduceUserMoneyFen, true));
            userBaseService.insertOrUpdateVirtualMoney(user.getId(), virtualMoneyFen, osIndex,
                    PayUtils.buildConsumeMoneyLog(user.getId(), consumeOrderId,
                            needReduceVirtualMoneyFen, virtualMoneyFen - needReduceVirtualMoneyFen, false));
            boolean result = consumeOrderService.setUserMoneyOrder2Done(consumeOrderId);
            if (!result)
                throw new Exception("�����û�����ʧ��");
            return true;
        }
    }

}
