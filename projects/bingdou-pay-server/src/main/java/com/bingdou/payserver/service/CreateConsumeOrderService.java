package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.ConsumeOrderService;
//import com.bingdou.core.service.pay.VoucherService;
import com.bingdou.core.service.pay.paytype.IPayTypeService;
import com.bingdou.core.service.pay.paytype.PayTypeResponse;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.core.utils.UserUtils;
import com.bingdou.payserver.request.CreateConsumeOrderRequest;
import com.bingdou.payserver.request.CreateRechargeOrderRequest;
import com.bingdou.payserver.response.CreateConsumeOrderResponse;
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
 * �������Ѷ���(ֱ��ֱ��)
 * Created by gaoshan on 16/01/03.
 */
@Service
public class CreateConsumeOrderService extends BaseService implements IMethodService {

    @Autowired
    private ConsumeOrderService consumeOrderService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        CreateConsumeOrderRequest createConsumeOrderRequest = new CreateConsumeOrderRequest();
        createConsumeOrderRequest.parseRequest(request);
        return createConsumeOrderRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        CreateConsumeOrderRequest request = (CreateConsumeOrderRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    @Override
    public String getMethodName() {
        return "create_consume_order";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateConsumeOrderRequest createConsumeOrderRequest = (CreateConsumeOrderRequest) baseRequest;
        if (createConsumeOrderRequest.getMoney() <= 0
                || StringUtils.isEmpty(createConsumeOrderRequest.getUserOrderId())) {
            return ServiceResultUtil.illegal("�����������");
        }
        Application application = appBaseService.getAppByAppId(createConsumeOrderRequest.getAppId());
        return deal(createConsumeOrderRequest, user, application,
                RequestUtil.getClientIp(request), false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateConsumeOrderRequest createConsumeOrderRequest = (CreateConsumeOrderRequest) baseRequest;
        if (createConsumeOrderRequest.getMoney() <= 0
                || StringUtils.isEmpty(createConsumeOrderRequest.getUserOrderId())) {
            return ServiceResultUtil.illegal("�����������");
        }
        Application application = getValidApplication4Client(createConsumeOrderRequest);
        return deal(createConsumeOrderRequest, user, application,
                RequestUtil.getClientIp(request), true);
    }

    private ServiceResult deal(CreateConsumeOrderRequest createConsumeOrderRequest, User user,
                               Application application, String clientIp,
                               boolean isFromClient) throws Exception {
        Os os;
        String sdkVersion = "";
        String channel;
        boolean isUseConsumeVoucher = false;
        boolean isContainUserMoney = createConsumeOrderRequest.getIsContainUserMoney() == 1;
        if (isFromClient) {
            channel = createConsumeOrderRequest.getOtherInfo().getChannel();
            os = getClientOsByRequest(createConsumeOrderRequest);
            sdkVersion = createConsumeOrderRequest.getOtherInfo().getSdkVersion();
            isUseConsumeVoucher = createConsumeOrderRequest.getVoucherId() > 0;
        } else {
            os = Os.SERVER;
            channel = createConsumeOrderRequest.getChannel();
        }
        PayType payType = PayType.getByIndex(createConsumeOrderRequest.getPayType());
        if (payType == null)
            return ServiceResultUtil.illegal("�Ƿ�֧������");
        if (!payTypeBaseService.valid(payType, os, sdkVersion, createConsumeOrderRequest.getMoney())) {
            LogContext.instance().warn(payType + "," + os + "֧���ر�");
            return ServiceResultUtil.illegal("��֧����ʽ��ʱ�رջ򳬹��޶�");
        }
//        if (isContainUserMoney && !switchRuleService.isSupportHaimaCoin(application.getAppId(),
//                application.getOs(), channel))
        if (isContainUserMoney){
            return ServiceResultUtil.illegal("����Ϸ�ݲ�֧�ֱ�����֧��");
        }
        if (consumeOrderService.existByUserOrderIdAndAppId(
                createConsumeOrderRequest.getUserOrderId(), application.getAppId()))
            return ServiceResultUtil.illegal("�����߶������ظ�,��������ʧ��");
        int virtualMoneyFen = getVirtualMoneyFen4Use(user.getId(), application.getOs(),
                application.getAppId(), channel);
        int userTotalMoneyFen = virtualMoneyFen + user.getMoney();
        int orderMoneyFen = NumberUtil.convertFenFromYuan(createConsumeOrderRequest.getMoney());
        if (isContainUserMoney && userTotalMoneyFen >= orderMoneyFen)
            return ServiceResultUtil.illegal("�û������ڵ���֧�����,��ʹ�ñ�����֧��");
        if (isContainUserMoney && userTotalMoneyFen == 0)
            return ServiceResultUtil.illegal("�û�û�����");
//        Voucher voucher = null;
        int voucherAmountFen = 0;
//        if (isUseConsumeVoucher) {
//            LogContext.instance().info("���Ѵ���ȯԤʹ���߼�");
//            voucher = voucherService.getValidVoucher4UseConsumeVoucher(createConsumeOrderRequest.getVoucherId(),
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
            consumeOrderId = PayUtils.generateConsumeOrderIdByPayType(payType);
            payedMoneyFen = orderMoneyFen - voucherAmountFen;
        }
        float payedMoney = NumberUtil.convertYuanFromFen(payedMoneyFen);
        IPayTypeService payTypeService = payTypeFactory.getPayTypeService(payType);
        PayTypeResponse payTypeResponse = CreateOrderUtils.dealPayTypeRequest(consumeOrderId,
                user.getId(), clientIp, payTypeService, createConsumeOrderRequest, payedMoney, false);
        if (payTypeResponse == null) {
            LogContext.instance().error("����֧����ʽ��ȡ���Ϊ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        if (!payTypeResponse.isSuccess()) {
            LogContext.instance().error("����֧����ʽ����:" + payTypeResponse.getResultMessage());
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        String rechargeOrderId = PayUtils.generateRechargeOrderId();
        RechargeOrder rechargeOrder = buildRechargeOrder(user.getId(), application,
                payType, rechargeOrderId, createConsumeOrderRequest, payTypeResponse, payedMoney,
                isFromClient);
        boolean createRechargeSuccess = createRechargeOrder(rechargeOrder);
        if (!createRechargeSuccess) {
            LogContext.instance().error("����ֱ���ֵ����ʧ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        ConsumeOrder consumeOrder = buildConsumeOrder(user.getId(), application, rechargeOrderId, consumeOrderId,
                payType, payTypeResponse, createConsumeOrderRequest, isFromClient);
        boolean createConsumeSuccess = createConsumeOrder(consumeOrder);
        if (!createConsumeSuccess) {
            LogContext.instance().error("����ֱ�����Ѷ���ʧ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        if (isContainUserMoney)
            insertUserMoneyAndOrder(user, virtualMoneyFen, consumeOrderId);
        int voucherId = -1;
//        if (voucher != null)
//            voucherId = voucher.getId();
//        if (isUseConsumeVoucher)
//            preUseVoucher(voucherId, application.getAppId(), channel, user.getId(), consumeOrderId);
        DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_RECHARGE_ORDER, createConsumeOrderRequest,
                user, clientIp, "", rechargeOrder, false, false);
        DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_CP_PAY_ORDER, createConsumeOrderRequest,
                user, clientIp, voucherId + "", consumeOrder, false, voucherId > 0);
        CreateConsumeOrderResponse response = createResponse(consumeOrderId, payType,
                payTypeResponse, payedMoney);
        return ServiceResultUtil.success(response);
    }

    @SuppressWarnings("Duplicates")
    private CreateConsumeOrderResponse createResponse(String consumeOrderId, PayType payType,
                                                      PayTypeResponse payTypeResponse, float payedMoney) {
        CreateConsumeOrderResponse response = new CreateConsumeOrderResponse();
        response.setNeedPayMoney(payedMoney);
        String orderId = consumeOrderId;
        if (payTypeResponse != null) {
            if (PayType.ALI_MOBILE.equals(payType)) {
                response.setPayRequestUrl(payTypeResponse.getRequestParam());
            } else if (PayType.WEIXIN.equals(payType)) {
                response.setWxResponse(payTypeResponse.getWxResponse());
            } else if (PayType.HEEPAY_WEIXIN_WAP.equals(payType)
                    || PayType.ALI_SCAN.equals(payType)) {
                response.setPayRequestUrl(payTypeResponse.getRequestUrl());
            }
        }
        response.setOrderId(orderId);
        return response;
    }

    private RechargeOrder buildRechargeOrder(int userId, Application application, PayType payType, String rechargeOrderId,
                                             CreateConsumeOrderRequest request, PayTypeResponse response,
                                             float payedMoney, boolean isFromClient) {
        CreateRechargeOrderRequest createRechargeOrderRequest = new CreateRechargeOrderRequest();
        createRechargeOrderRequest.setMoney(payedMoney);
        createRechargeOrderRequest.setPayType(request.getPayType());
        createRechargeOrderRequest.setOther(request.getOther());
        createRechargeOrderRequest.setOtherInfo(request.getOtherInfo());
        createRechargeOrderRequest.setDeviceInfo(request.getDeviceInfo());
        return CreateOrderUtils.buildRechargeOrder(rechargeOrderId, userId,
                application, createRechargeOrderRequest, PayUtils.getUnionPayOrderId(payType, response),
                request.getUserOrderId(), isFromClient);
    }

    private boolean createRechargeOrder(RechargeOrder rechargeOrder) throws Exception {
        LogContext.instance().info("������ֵ����");
        return rechargeOrderService.addRechargeOrder(rechargeOrder);
    }

    private boolean createConsumeOrder(ConsumeOrder consumeOrder) {
        LogContext.instance().info("�������Ѷ���");
        return consumeOrderService.addConsumeOrder(consumeOrder);
    }

    private ConsumeOrder buildConsumeOrder(int userId, Application application, String rechargeOrderId,
                                           String orderId, PayType payType, PayTypeResponse response,
                                           CreateConsumeOrderRequest request, boolean isFromClient) {
        long time = DateUtil.getCurrentTimeSeconds();
        ConsumeOrder consumeOrder = new ConsumeOrder();
        consumeOrder.setUserId(userId);
        consumeOrder.setAppId(application.getAppId());
        consumeOrder.setAppMemberId(application.getAppMemberId());
        consumeOrder.setConsumeMoney(NumberUtil.convertFenFromYuan(request.getMoney()));
        consumeOrder.setGoodsDescription(request.getGoodsDescription());
        consumeOrder.setGoodsName(request.getGoodsName());
        consumeOrder.setOrderId(orderId);
        consumeOrder.setUserOrderId(request.getUserOrderId());
        consumeOrder.setPayedMoney(0);
        consumeOrder.setOrderTime(time);
        consumeOrder.setPayedTime(0);
        consumeOrder.setPayType(payType.getIndex());
        consumeOrder.setUserParam(StringUtils.isEmpty(request.getUserOrderParam()) ? "" :
                request.getUserOrderParam());
        consumeOrder.setStatus(OrderStatus.NOT_PAY.getIndex());
        consumeOrder.setRemark(StringUtils.isEmpty(request.getOther()) ? "" :
                request.getOther());
        consumeOrder.setRechargeOrderId(rechargeOrderId);
        consumeOrder.setOsName(Os.getOsNameByIndex(application.getOs()));
        if (isFromClient) {
            consumeOrder.setSdkVersion(request.getOtherInfo().getSdkVersion());
            consumeOrder.setChannel(request.getOtherInfo().getChannel());
//            consumeOrder.setUa(UserUtils.getOldUa4Client(request));
//            consumeOrder.setUid(UserUtils.getOldUid4Client(request));
        }
        consumeOrder.setUnionPayOrderId(PayUtils.getUnionPayOrderId(payType, response));
        return consumeOrder;
    }

//    private void preUseVoucher(int voucherId, String appId, String channel, int userId, String consumeOrderId)
//            throws Exception {
//        boolean voucherUseSuccess = voucherService.useVoucher(voucherId, VoucherType.CONSUME.getIndex(),
//                appId, channel, consumeOrderId, userId, VoucherUseStatus.PRE_USE);
//        if (!voucherUseSuccess)
//            throw new Exception("���Ѵ���ȯԤʹ��ʧ��");
//    }

    private void insertUserMoneyAndOrder(User user, int virtualMoneyFen,
                                         String consumeOrderId) throws Exception {
        LogContext.instance().info("��Ҫ���ĵı�����(��):" + user.getMoney());
        LogContext.instance().info("��Ҫ���ĵ���Ϸ��(��):" + virtualMoneyFen);
        consumeOrderService.insertUserMoneyOrderCreate(user.getId(), consumeOrderId,
                user.getMoney(), virtualMoneyFen);
    }

}
