package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.AliPayNoPwdAuthService;
//import com.bingdou.core.service.pay.ChargeBackService;
import com.bingdou.core.service.pay.ConsumeOrderService;
//import com.bingdou.core.service.pay.VoucherService;
import com.bingdou.core.service.pay.paytype.IPayTypeService;
import com.bingdou.core.service.pay.paytype.PayTypeRequest;
import com.bingdou.core.service.pay.paytype.PayTypeResponse;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.AliNoPwdPayRequest;
import com.bingdou.payserver.request.CreateRechargeOrderRequest;
import com.bingdou.payserver.response.AliNoPwdPayResponse;
import com.bingdou.payserver.utils.CreateOrderUtils;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * ֧��������֧��
 */
@Service
public class AliNoPwdPayService extends BaseService implements IMethodService {

//    @Autowired
//    private ChargeBackService chargeBackService;
    @Autowired
    private AliPayNoPwdAuthService aliPayNoPwdAuthService;
    @Autowired
    private ConsumeOrderService consumeOrderService;
//    @Autowired
//    private VoucherService voucherService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        AliNoPwdPayRequest aliNoPwdPayRequest = new AliNoPwdPayRequest();
        aliNoPwdPayRequest.parseRequest(request);
        return aliNoPwdPayRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        AliNoPwdPayRequest request = (AliNoPwdPayRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    @Override
    public String getMethodName() {
        return "ali_no_pwd_pay";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AliNoPwdPayRequest aliNoPwdPayRequest = (AliNoPwdPayRequest) baseRequest;
        if (aliNoPwdPayRequest.getMoney() <= 0) {
            return ServiceResultUtil.illegal("�����������");
        }
        Os os = getClientOsByRequest(aliNoPwdPayRequest);
        String sdkVersion = aliNoPwdPayRequest.getOtherInfo().getSdkVersion();
        if (!payTypeBaseService.valid(PayType.ALI_NO_PWD, os, sdkVersion, aliNoPwdPayRequest.getMoney())) {
            LogContext.instance().warn(PayType.ALI_NO_PWD + "," + os + "֧���ر�");
            return ServiceResultUtil.illegal("��֧����ʽ��ʱ�رջ򳬹��޶�");
        }
        boolean isSign = aliPayNoPwdAuthService.isSignFromAliServer(user.getId());
        if (!isSign) {
            return ServiceResultUtil.aliPayNoPwdUnSign();
        }
        Application application = getValidApplication4Client(aliNoPwdPayRequest);
        String clientIp = RequestUtil.getClientIp(request);
        if (aliNoPwdPayRequest.getIsRecharge() == 1) {
            if (aliNoPwdPayRequest.getAliNoPwdPayRecharge() == null)
                return ServiceResultUtil.illegal("�����������");
            return recharge(aliNoPwdPayRequest, user, application, clientIp);
        } else {
            if (aliNoPwdPayRequest.getAliNoPwdPayConsume() == null)
                return ServiceResultUtil.illegal("�����������");
            return consume(aliNoPwdPayRequest, user, application, clientIp);
        }
    }

    private ServiceResult recharge(AliNoPwdPayRequest aliNoPwdPayRequest, User user, Application application,
                                   String clientIp)
            throws Exception {
        if (aliNoPwdPayRequest.getMoney() < 1) {
            return ServiceResultUtil.illegal("��ֵ���������1Ԫ");
        }
//        boolean isSupportHaimaCoin = switchRuleService.isSupportHaimaCoin(aliNoPwdPayRequest.getOtherInfo().getAppId(),
//                aliNoPwdPayRequest.getDeviceInfo().getOs(), aliNoPwdPayRequest.getOtherInfo().getChannel());
//        if (!isSupportHaimaCoin) {
//            return ServiceResultUtil.illegal("��Ӧ���ݲ�֧�ֳ�ֵ������");
//        }
        LogContext.instance().info("������ֵ����");
        RechargeOrder rechargeOrder = createRechargeOrder4Recharge(aliNoPwdPayRequest, user.getId(), application);
//        if (aliNoPwdPayRequest.getAliNoPwdPayRecharge().getActivityType() == ActivityType.CARD.getIndex()
//                && aliNoPwdPayRequest.getAliNoPwdPayRecharge().getRechargeCardId() > 0) {
//            LogContext.instance().info("ѡ���˳䷵���,���䷵���Ƿ����");
//            boolean unUsedPropId = chargeBackService.validPropId(user.getId(),
//                    aliNoPwdPayRequest.getAliNoPwdPayRecharge().getRechargeCardId());
//            if (unUsedPropId) {
//                LogContext.instance().info("�䷵������");
//                rechargeOrder.setPropId(aliNoPwdPayRequest.getAliNoPwdPayRecharge().getRechargeCardId());
//            } else {
//                LogContext.instance().info("�䷵��������");
//            }
//        }
        boolean createSuccess = rechargeOrderService.addRechargeOrder(rechargeOrder);
        LogContext.instance().info("������ֵ�������:" + createSuccess);
        if (createSuccess) {
            AliPayNoPwdAuth auth = aliPayNoPwdAuthService.getAuthInfo(user.getId());
            if (auth == null)
                return ServiceResultUtil.aliPayNoPwdUnSign();
            IPayTypeService payTypeService = payTypeFactory.getPayTypeService(PayType.ALI_NO_PWD);
            if (payTypeService == null) {
                LogContext.instance().error("����֧����ʽ��ȡ���Ϊ��");
                return ServiceResultUtil.illegal("��������ʧ��");
            }
            PayTypeResponse payTypeResponse = payTypeService.callPayType(buildPayTypeRequest(aliNoPwdPayRequest,
                    rechargeOrder.getOrderId(), user.getId(), clientIp, auth.getAgreementNo()));
            if (payTypeResponse == null) {
                LogContext.instance().error("����֧����ʽ��ȡ���Ϊ��");
                return ServiceResultUtil.illegal("��������ʧ��");
            }
            DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_RECHARGE_ORDER, aliNoPwdPayRequest,
                    user, clientIp, rechargeOrder.getPropId() + "", rechargeOrder, true, false);
            return buildServiceResult(rechargeOrder.getOrderId(), payTypeResponse.getAliNoPwdPayReturnCode());
        } else {
            return ServiceResultUtil.illegal("������ֵ����ʧ��");
        }
    }

    private ServiceResult consume(AliNoPwdPayRequest aliNoPwdPayRequest, User user, Application application,
                                  String clientIp) throws Exception {
        if (StringUtils.isEmpty(aliNoPwdPayRequest.getAliNoPwdPayConsume().getUserOrderId())) {
            return ServiceResultUtil.illegal("�����߶����Ų���Ϊ��");
        }
        boolean isContainUserMoney = aliNoPwdPayRequest.getAliNoPwdPayConsume().getIsContainUserMoney() == 1;
        String channel = aliNoPwdPayRequest.getOtherInfo().getChannel();
        boolean isUseConsumeVoucher = aliNoPwdPayRequest.getAliNoPwdPayConsume().getVoucherId() > 0;
//        if (isContainUserMoney && !switchRuleService.isSupportHaimaCoin(application.getAppId(),
//                application.getOs(), channel))
        if (isContainUserMoney) {
            return ServiceResultUtil.illegal("��Ӧ���ݲ�֧�ֱ�����֧��");
        }
        if (consumeOrderService.existByUserOrderIdAndAppId(
                aliNoPwdPayRequest.getAliNoPwdPayConsume().getUserOrderId(), application.getAppId()))
            return ServiceResultUtil.illegal("�����߶������ظ�,��������ʧ��");
        int virtualMoneyFen = getVirtualMoneyFen4Use(user.getId(), application.getOs(),
                application.getAppId(), channel);
        int userTotalMoneyFen = virtualMoneyFen + user.getMoney();
        int orderMoneyFen = NumberUtil.convertFenFromYuan(aliNoPwdPayRequest.getMoney());
        if (isContainUserMoney && userTotalMoneyFen >= orderMoneyFen)
            return ServiceResultUtil.illegal("�û������ڵ���֧�����,��ʹ�ñ�����֧��");
        if (isContainUserMoney && userTotalMoneyFen == 0)
            return ServiceResultUtil.illegal("�û�û�����");
//        Voucher voucher = null;
        int voucherAmountFen = 0;
//        if (isUseConsumeVoucher) {
//            LogContext.instance().info("���Ѵ���ȯԤʹ���߼�");
//            voucher = voucherService.getValidVoucher4UseConsumeVoucher(aliNoPwdPayRequest.getAliNoPwdPayConsume()
//                            .getVoucherId(),
//                    application.getAppId(), user.getId());
//            if (voucher == null)
//                return ServiceResultUtil.illegal("�Ƿ�����ȯ");
//            voucherAmountFen = NumberUtil.convertFenFromYuan(voucher.getAmount());
//            if (isContainUserMoney && userTotalMoneyFen + voucherAmountFen >= orderMoneyFen)
//                return ServiceResultUtil.illegal("�û����+����ȯ�����ڵ���֧�����,��ʹ�ñ�����֧��");
//        }
        String consumeOrderId;
        int payedMoneyFen;
        if (isContainUserMoney) {
            LogContext.instance().info("�ֽ�+�����϶���");
            consumeOrderId = PayUtils.generateMixConsumeOrderId();
            payedMoneyFen = orderMoneyFen - userTotalMoneyFen - voucherAmountFen;
        } else {
            LogContext.instance().info("�ֽ𶩵�");
            consumeOrderId = PayUtils.generateConsumeOrderIdByPayType(PayType.ALI_NO_PWD);
            payedMoneyFen = orderMoneyFen - voucherAmountFen;
        }
        float payedMoney = NumberUtil.convertYuanFromFen(payedMoneyFen);
        RechargeOrder rechargeOrder = createRechargeOrder4Consume(aliNoPwdPayRequest, payedMoney, user.getId(),
                application);
        boolean createRechargeSuccess = rechargeOrderService.addRechargeOrder(rechargeOrder);
        if (!createRechargeSuccess) {
            LogContext.instance().error("����ֱ���ֵ����ʧ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        ConsumeOrder consumeOrder = buildConsumeOrder(user.getId(), application, rechargeOrder.getOrderId(),
                consumeOrderId, aliNoPwdPayRequest);
        boolean createConsumeSuccess = consumeOrderService.addConsumeOrder(consumeOrder);
        if (!createConsumeSuccess) {
            LogContext.instance().error("����ֱ�����Ѷ���ʧ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        IPayTypeService payTypeService = payTypeFactory.getPayTypeService(PayType.ALI_NO_PWD);
        if (payTypeService == null) {
            LogContext.instance().error("����֧����ʽ��ȡ���Ϊ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        AliPayNoPwdAuth auth = aliPayNoPwdAuthService.getAuthInfo(user.getId());
        if (auth == null)
            return ServiceResultUtil.aliPayNoPwdUnSign();
        PayTypeResponse payTypeResponse = payTypeService.callPayType(buildPayTypeRequest(aliNoPwdPayRequest,
                consumeOrderId, user.getId(), clientIp, auth.getAgreementNo()));
        if (payTypeResponse == null) {
            LogContext.instance().error("����֧����ʽ��ȡ���Ϊ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        if (isContainUserMoney) {
            LogContext.instance().info("��Ҫ���ĵı�����(��):" + user.getMoney());
            LogContext.instance().info("��Ҫ���ĵ���Ϸ��(��):" + virtualMoneyFen);
            consumeOrderService.insertUserMoneyOrderCreate(user.getId(), consumeOrderId,
                    user.getMoney(), virtualMoneyFen);
        }
        int voucherId = -1;
//        if (voucher != null)
//            voucherId = voucher.getId();
//        if (isUseConsumeVoucher)
//            preUseVoucher(voucherId, application.getAppId(), channel, user.getId(), consumeOrderId);
        DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_RECHARGE_ORDER, aliNoPwdPayRequest,
                user, clientIp, "", rechargeOrder, false, false);
        DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_CP_PAY_ORDER, aliNoPwdPayRequest,
                user, clientIp, voucherId + "", consumeOrder, false, voucherId > 0);
        return buildServiceResult(consumeOrderId, payTypeResponse.getAliNoPwdPayReturnCode());
    }

    private RechargeOrder createRechargeOrder4Consume(AliNoPwdPayRequest aliNoPwdPayRequest, float payedMoneyYuan,
                                                      int userId, Application application) {
        String rechargeOrderId = PayUtils.generateRechargeOrderId();
        CreateRechargeOrderRequest createRechargeOrderRequest = new CreateRechargeOrderRequest();
        createRechargeOrderRequest.setMoney(payedMoneyYuan);
        createRechargeOrderRequest.setPayType(PayType.ALI_NO_PWD.getIndex());
        createRechargeOrderRequest.setOther("");
        createRechargeOrderRequest.setOtherInfo(aliNoPwdPayRequest.getOtherInfo());
        createRechargeOrderRequest.setDeviceInfo(aliNoPwdPayRequest.getDeviceInfo());
        return CreateOrderUtils.buildRechargeOrder(rechargeOrderId, userId,
                application, createRechargeOrderRequest, "",
                aliNoPwdPayRequest.getAliNoPwdPayConsume().getUserOrderId(), true);
    }

    private RechargeOrder createRechargeOrder4Recharge(AliNoPwdPayRequest aliNoPwdPayRequest,
                                                       int userId, Application application) {
        String rechargeOrderId = PayUtils.generateRechargeOrderId();
        CreateRechargeOrderRequest createRechargeOrderRequest = new CreateRechargeOrderRequest();
        createRechargeOrderRequest.setMoney(aliNoPwdPayRequest.getMoney());
        createRechargeOrderRequest.setPayType(PayType.ALI_NO_PWD.getIndex());
        createRechargeOrderRequest.setOther("");
        createRechargeOrderRequest.setOtherInfo(aliNoPwdPayRequest.getOtherInfo());
        createRechargeOrderRequest.setDeviceInfo(aliNoPwdPayRequest.getDeviceInfo());
        createRechargeOrderRequest.setActivityType(aliNoPwdPayRequest.getAliNoPwdPayRecharge().getActivityType());
        createRechargeOrderRequest.setClientScene(aliNoPwdPayRequest.getAliNoPwdPayRecharge().getClientScene());
        return CreateOrderUtils.buildRechargeOrder(rechargeOrderId, userId,
                application, createRechargeOrderRequest, "", "", true);
    }

    private PayTypeRequest buildPayTypeRequest(AliNoPwdPayRequest aliNoPwdPayRequest, String orderId,
                                               int userId, String clientIp, String aliAgreementNo) {
        PayTypeRequest payTypeRequest = new PayTypeRequest();
        payTypeRequest.setBaseRequest(aliNoPwdPayRequest);
        payTypeRequest.setPayType(PayType.ALI_NO_PWD.getIndex());
        payTypeRequest.setRecharge(aliNoPwdPayRequest.getIsRecharge() == 1);
        payTypeRequest.setOrderId(orderId);
        payTypeRequest.setMoney(aliNoPwdPayRequest.getMoney());
        if (aliNoPwdPayRequest.getIsRecharge() == 1) {
            payTypeRequest.setOrderDesc("�����ҳ�ֵ");
        } else {
            payTypeRequest.setOrderDesc(aliNoPwdPayRequest.getAliNoPwdPayConsume().getGoodsName());
        }
        payTypeRequest.setUserId(userId);
        payTypeRequest.setClientIP(clientIp);
        payTypeRequest.setAliAgreementNo(aliAgreementNo);
        return payTypeRequest;
    }

    private ConsumeOrder buildConsumeOrder(int userId, Application application, String rechargeOrderId,
                                           String orderId, AliNoPwdPayRequest request) {
        long time = DateUtil.getCurrentTimeSeconds();
        ConsumeOrder consumeOrder = new ConsumeOrder();
        consumeOrder.setUserId(userId);
        consumeOrder.setAppId(application.getAppId());
        consumeOrder.setAppMemberId(application.getAppMemberId());
        consumeOrder.setConsumeMoney(NumberUtil.convertFenFromYuan(request.getMoney()));
        consumeOrder.setGoodsDescription(request.getAliNoPwdPayConsume().getGoodsDescription());
        consumeOrder.setGoodsName(request.getAliNoPwdPayConsume().getGoodsName());
        consumeOrder.setOrderId(orderId);
        consumeOrder.setUserOrderId(request.getAliNoPwdPayConsume().getUserOrderId());
        consumeOrder.setPayedMoney(0);
        consumeOrder.setOrderTime(time);
        consumeOrder.setPayedTime(0);
        consumeOrder.setPayType(PayType.ALI_NO_PWD.getIndex());
        consumeOrder.setUserParam(StringUtils.isEmpty(request.getAliNoPwdPayConsume().getUserOrderParam()) ? "" :
                request.getAliNoPwdPayConsume().getUserOrderParam());
        consumeOrder.setStatus(OrderStatus.NOT_PAY.getIndex());
        consumeOrder.setRemark("");
        consumeOrder.setRechargeOrderId(rechargeOrderId);
        consumeOrder.setOsName(Os.getOsNameByIndex(application.getOs()));
        consumeOrder.setSdkVersion(request.getOtherInfo().getSdkVersion());
        consumeOrder.setChannel(request.getOtherInfo().getChannel());
//        consumeOrder.setUa(UserUtils.getOldUa4Client(request));
//        consumeOrder.setUid(UserUtils.getOldUid4Client(request));
        consumeOrder.setUnionPayOrderId("");
        return consumeOrder;
    }

//    private void preUseVoucher(int voucherId, String appId, String channel, int userId, String consumeOrderId)
//            throws Exception {
//        boolean voucherUseSuccess = voucherService.useVoucher(voucherId, VoucherType.CONSUME.getIndex(),
//                appId, channel, consumeOrderId, userId, VoucherUseStatus.PRE_USE);
//        if (!voucherUseSuccess)
//            throw new Exception("���Ѵ���ȯԤʹ��ʧ��");
//    }

    private ServiceResult buildServiceResult(String orderId, AliNoPwdPayReturnCode aliNoPwdPayReturnCode) {
        AliNoPwdPayResponse response = new AliNoPwdPayResponse();
        response.setOrderId(orderId);
        if (aliNoPwdPayReturnCode.equals(AliNoPwdPayReturnCode.ORDER_SUCCESS_PAY_SUCCESS)) {
            return ServiceResultUtil.success();
        } else if (aliNoPwdPayReturnCode.equals(AliNoPwdPayReturnCode.ORDER_SUCCESS_PAY_INPROCESS)) {
            return ServiceResultUtil.aliPayNoPwdInProcess();
        } else {
            return ServiceResultUtil.illegal("֧��������֧��ʧ��");
        }
    }

}
