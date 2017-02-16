package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
//import com.bingdou.core.service.pay.ChargeBackService;
import com.bingdou.core.service.pay.paytype.IPayTypeService;
import com.bingdou.core.service.pay.paytype.PayTypeResponse;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.CreateRechargeOrderRequest;
import com.bingdou.payserver.response.CreateRechargeOrderResponse;
import com.bingdou.payserver.utils.CreateOrderUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * ������ֵ����
 * Created by gaoshan on 17/1/10.
 */
@Service
public class CreateRechargeOrderService extends BaseService implements IMethodService {

//    @Autowired
//    private ChargeBackService chargeBackService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        CreateRechargeOrderRequest createRechargeOrderRequest = new CreateRechargeOrderRequest();
        createRechargeOrderRequest.parseRequest(request);
        return createRechargeOrderRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        CreateRechargeOrderRequest request = (CreateRechargeOrderRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    @Override
    public String getMethodName() {
        return "create_recharge_order";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateRechargeOrderRequest createRechargeOrderRequest = (CreateRechargeOrderRequest) baseRequest;
        if (StringUtils.isEmpty(createRechargeOrderRequest.getAccount())) {
            return ServiceResultUtil.illegal("�˻���ϢΪ��");
        }
        if (createRechargeOrderRequest.getMoney() < 1) {
            return ServiceResultUtil.illegal("��ֵ���������1Ԫ");
        }
        PayType payType = PayType.getByIndex(createRechargeOrderRequest.getPayType());
        if (payType == null) {
            return ServiceResultUtil.illegal("�Ƿ�֧������");
        }
        Application application = appBaseService.getAppByAppId(createRechargeOrderRequest.getAppId());
        return deal(createRechargeOrderRequest, user, application, payType,
                RequestUtil.getClientIp(request), false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateRechargeOrderRequest createRechargeOrderRequest = (CreateRechargeOrderRequest) baseRequest;
        if (StringUtils.isEmpty(createRechargeOrderRequest.getAccount())) {
            return ServiceResultUtil.illegal("�˻���ϢΪ��");
        }
        if (createRechargeOrderRequest.getMoney() < 1) {
            return ServiceResultUtil.illegal("��ֵ���������1Ԫ");
        }
        PayType payType = PayType.getByIndex(createRechargeOrderRequest.getPayType());
        if (payType == null) {
            return ServiceResultUtil.illegal("�Ƿ�֧������");
        }
//        boolean isSupportHaimaCoin = switchRuleService.isSupportHaimaCoin(baseRequest.getOtherInfo().getAppId(),
//                baseRequest.getDeviceInfo().getOs(), baseRequest.getOtherInfo().getChannel());
//        if (!isSupportHaimaCoin) {
//            return ServiceResultUtil.illegal("��Ӧ���ݲ�֧�ֳ�ֵ������");
//        }
        Application application = getValidApplication4Client(createRechargeOrderRequest);
        return deal(createRechargeOrderRequest, user, application, payType,
                RequestUtil.getClientIp(request), true);
    }

    private ServiceResult deal(CreateRechargeOrderRequest createRechargeOrderRequest, User user,
                               Application application, PayType payType, String clientIp,
                               boolean isFromClient)
            throws Exception {
        Os os;
        String sdkVersion = "";
        if (isFromClient) {
            os = getClientOsByRequest(createRechargeOrderRequest);
            sdkVersion = createRechargeOrderRequest.getOtherInfo().getSdkVersion();
        } else {
            os = Os.SERVER;
        }
        if (!payTypeBaseService.valid(payType, os, sdkVersion, createRechargeOrderRequest.getMoney())) {
            LogContext.instance().warn(payType + "," + os + "֧���ر�");
            return ServiceResultUtil.illegal("��֧����ʽ��ʱ�رջ򳬹��޶�");
        }
        String rechargeOrderId = PayUtils.generateRechargeOrderId();
        IPayTypeService payTypeService = payTypeFactory.getPayTypeService(payType);
        PayTypeResponse payTypeResponse = CreateOrderUtils.dealPayTypeRequest(rechargeOrderId,
                user.getId(), clientIp, payTypeService, createRechargeOrderRequest,
                createRechargeOrderRequest.getMoney(), true);
        if (payTypeResponse == null) {
            LogContext.instance().error("����֧����ʽ��ȡ���Ϊ��");
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        if (!payTypeResponse.isSuccess()) {
            LogContext.instance().error("����֧����ʽ����:" + payTypeResponse.getResultMessage());
            return ServiceResultUtil.illegal("��������ʧ��");
        }
        LogContext.instance().info("������ֵ����");
        RechargeOrder rechargeOrder = CreateOrderUtils.buildRechargeOrder(rechargeOrderId, user.getId(),
                application, createRechargeOrderRequest,
                PayUtils.getUnionPayOrderId(payType, payTypeResponse), "", isFromClient);
        boolean unUsedPropId = false;
//        if (createRechargeOrderRequest.getActivityType() == ActivityType.CARD.getIndex()
//                && createRechargeOrderRequest.getRechargeCardId() > 0) {
//            LogContext.instance().info("ѡ���˳䷵���,���䷵���Ƿ����");
//            unUsedPropId = chargeBackService.validPropId(user.getId(),
//                    createRechargeOrderRequest.getRechargeCardId());
//            if (unUsedPropId) {
//                LogContext.instance().info("�䷵������");
//                rechargeOrder.setPropId(createRechargeOrderRequest.getRechargeCardId());
//            } else {
//                LogContext.instance().info("�䷵��������");
//            }
//        }
        boolean createSuccess = rechargeOrderService.addRechargeOrder(rechargeOrder);
        LogContext.instance().info("������ֵ�������:" + createSuccess);
        if (createSuccess) {
            DataLogUtils.recordHadoopLog(HadoopLogAction.CREATE_RECHARGE_ORDER, createRechargeOrderRequest,
                    user, clientIp, rechargeOrder.getPropId() + "", rechargeOrder, true, false);
            CreateRechargeOrderResponse response = createResponse(unUsedPropId, rechargeOrder, payType,
                    payTypeResponse);
            return ServiceResultUtil.success(response);
        } else {
            return ServiceResultUtil.illegal("������ֵ����ʧ��");
        }
    }

    @SuppressWarnings("Duplicates")
    private CreateRechargeOrderResponse createResponse(boolean unUsedPropId, RechargeOrder rechargeOrder,
                                                       PayType payType, PayTypeResponse payTypeResponse) {
        CreateRechargeOrderResponse response = new CreateRechargeOrderResponse();
        response.setCanUseRechargeCard(unUsedPropId ? 1 : 0);
        response.setMoney(rechargeOrder.getQuantity());
        String orderId = rechargeOrder.getOrderId();
        if (payTypeResponse != null) {
            if (PayType.ALI_MOBILE.equals(payType)) {
                response.setPayRequestUrl(payTypeResponse.getRequestParam());
            } else if (PayType.WEIXIN.equals(payType)) {
                response.setWxResponse(payTypeResponse.getWxResponse());
            } else if (PayType.HEEPAY_WEIXIN_SCAN_CODE.equals(payType)
                    || PayType.ALI_SCAN.equals(payType)) {
                response.setPayRequestUrl(payTypeResponse.getRequestUrl());
            }
//            } else if (PayType.HEEPAY_WEIXIN_SDK.equals(payType)) {
//                response.setHeepaySdkWxResponse(payTypeResponse.getHeepaySdkWxResponse());
//            } else if (PayType.PP.equals(payType)) {
//                response.setPayRequestUrl(payTypeResponse.getPpResponse().getRequestUrl());
//            }
        }
        response.setOrderId(orderId);
        return response;
    }

}
