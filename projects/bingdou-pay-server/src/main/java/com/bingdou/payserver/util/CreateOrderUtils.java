//package com.bingdou.payserver.util;
//
//import com.bingdou.core.model.Application;
//import com.bingdou.core.model.Os;
//import com.bingdou.payserver.request.CreateOrderRequest;
//import com.bingdou.tools.DateUtil;
//import com.bingdou.tools.LogContext;
//import com.bingdou.tools.NumberUtil;
//import org.apache.commons.lang3.StringUtils;
//
///**
// * Created by gaoshan on 16/12/9.
// */
//public class CreateOrderUtils {
//
//    private CreateOrderUtils() {
//    }
//
//    public static PayTypeResponse dealPayTypeRequest(String orderId, int userId, String clientIp,
//                                                     IPayTypeService payTypeService,
//                                                     CreateOrderRequest createOrderRequest, float payedMoney,
//                                                     boolean isRechargeOrder) throws Exception {
//        if (payTypeService == null) {
//            LogContext.instance().info("payTypeService为空");
//            return null;
//        }
//        if (PayType.PAY_19.getIndex() == createOrderRequest.getPayType() && !isRechargeOrder) {
//            LogContext.instance().warn("19PAY只支持充值海马币");
//            return null;
//        }
//        PayTypeRequest request = new PayTypeRequest();
//        request.setBaseRequest(createOrderRequest);
//        request.setPayType(createOrderRequest.getPayType());
//        request.setRecharge(isRechargeOrder);
//        request.setOrderId(orderId);
//        request.setMoney(payedMoney);
//        request.setReturnUrl(createOrderRequest.getReturnUrl());
//        if (isRechargeOrder) {
//            request.setOrderDesc("冰豆币充值");
//        } else {
//            request.setOrderDesc(createOrderRequest.getGoodsName());
//        }
//        request.setUserId(userId);
//        request.setClientIP(clientIp);
//        if (PayType.PAY_19.getIndex() == createOrderRequest.getPayType()) {
//            CreateRechargeOrderRequest createRechargeOrderRequest = (CreateRechargeOrderRequest) createOrderRequest;
//            if (createRechargeOrderRequest.getCardPay() == null)
//                return null;
//            request.setPay19Type(createRechargeOrderRequest.getCardPay().getType());
//            request.setPay19CardNo(createRechargeOrderRequest.getCardPay().getCardNo());
//            request.setPay19CardPassword(createRechargeOrderRequest.getCardPay().getCardPassword());
//        }
//        return payTypeService.callPayType(request);
//    }
//
//    public static RechargeOrder buildRechargeOrder(String orderId, int userId, Application application,
//                                                   CreateRechargeOrderRequest request,
//                                                   String unionPayOrderId, String userOrderId,
//                                                   boolean isFromClient) {
//        long now = DateUtil.getCurrentTimeSeconds();
//        RechargeOrder rechargeOrder = new RechargeOrder();
//        rechargeOrder.setOrderId(orderId);
//        rechargeOrder.setUserId(userId);
//        rechargeOrder.setAppMemberId(application.getAppMemberId());
//        rechargeOrder.setGoodsName("海马币");
//        rechargeOrder.setQuantity(request.getMoney());
//        rechargeOrder.setOrderMoney(NumberUtil.convertFenFromYuan(request.getMoney()));
//        rechargeOrder.setOrderTime(now);
//        rechargeOrder.setPayedMoney(0);
//        rechargeOrder.setPayedTime(0);
//        rechargeOrder.setPayType(request.getPayType());
//        rechargeOrder.setStatus(OrderStatus.NOT_PAY.getIndex());
//        rechargeOrder.setVerify(OrderStatus.NOT_PAY.getIndex());
//        rechargeOrder.setVerifyMoney(0);
//        rechargeOrder.setVerifyTime(0);
//        rechargeOrder.setRemark(StringUtils.isEmpty(request.getOther()) ? "" : request.getOther());
//        String channel = "";
//        String sdkVersion = "";
//        String appId = application.getAppId();
//        String osName = Os.getOsNameByIndex(application.getOs());
//        if (isFromClient) {
//            channel = request.getOtherInfo().getChannel();
//            sdkVersion = request.getOtherInfo().getSdkVersion();
//        }
//        rechargeOrder.setChannel(channel);
//        rechargeOrder.setSdkVersion(sdkVersion);
//        rechargeOrder.setUnionPayOrderId(unionPayOrderId);
//        rechargeOrder.setUserOrderId(StringUtils.isEmpty(userOrderId) ? "" : userOrderId);
//        rechargeOrder.setAppId(appId);
//        rechargeOrder.setOsName(osName);
//        rechargeOrder.setActivityType(request.getActivityType());
//        rechargeOrder.setClientScene(request.getClientScene());
//        return rechargeOrder;
//    }
//
//}
