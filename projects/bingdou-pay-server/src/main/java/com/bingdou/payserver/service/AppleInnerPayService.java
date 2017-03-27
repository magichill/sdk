package com.bingdou.payserver.service;

import com.bingdou.core.constants.PayConstants;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.AppleInnerPayReceiptService;
import com.bingdou.core.service.pay.RechargeOrderService;
import com.bingdou.core.service.pay.VipGradeService;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.AppleInnerPayProduct;
import com.bingdou.payserver.request.AppleInnerPayRequest;
import com.bingdou.payserver.response.AppleInnerPayReceiptResponse;
import com.bingdou.payserver.response.AppleInnerPayResponse;
import com.bingdou.payserver.utils.PayServerProperties;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 苹果应用内支付服务类
 * Created by gaoshan on 17/3/25.
 */
@Service
public class AppleInnerPayService extends BaseService implements IMethodService {

    @Autowired
    private RechargeOrderService rechargeOrderService;
    @Autowired
    private VipGradeService vipGradeService;
    @Autowired
    private AppleInnerPayReceiptService appleInnerPayReceiptService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        AppleInnerPayRequest appleInnerPayRequest = new AppleInnerPayRequest();
        appleInnerPayRequest.parseRequest(request);
        return appleInnerPayRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        AppleInnerPayRequest request = (AppleInnerPayRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    @Override
    public String getMethodName() {
        return "apple_inner_pay";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AppleInnerPayRequest appleInnerPayRequest = (AppleInnerPayRequest) baseRequest;
        Application application = appBaseService.getAppByAppId(appleInnerPayRequest.getOtherInfo().getAppId());
        return dealApplePay(appleInnerPayRequest,user,application);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AppleInnerPayRequest appleInnerPayRequest = (AppleInnerPayRequest) baseRequest;
        Application application = appBaseService.getAppByAppId(appleInnerPayRequest.getAppId());
        return dealApplePay(appleInnerPayRequest,user,application);
    }

    private ServiceResult dealApplePay(AppleInnerPayRequest appleInnerPayRequest,User user,Application application) throws Exception {

        if (appleInnerPayRequest.getProductList() == null
                || appleInnerPayRequest.getProductList().isEmpty()
                || StringUtils.isEmpty(appleInnerPayRequest.getReceiptData())
                || appleInnerPayRequest.getOrderMoney() <= 0) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        String receiptVerifyResult = getAppleVerifyResult(appleInnerPayRequest.getReceiptData());
        AppleInnerPayResponse response = new AppleInnerPayResponse();
        if (StringUtils.isEmpty(receiptVerifyResult)) {
            LogContext.instance().error("苹果返回的验证结果为空");
            response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_APPLE_STATUS_ERROR);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
        }
        AppleInnerPayReceiptResponse receiptResponse = JsonUtil.jsonStr2Bean(receiptVerifyResult,
                AppleInnerPayReceiptResponse.class);
        if (receiptResponse.getStatus() != PayConstants.APPLE_PAY_VERIFY_SUCCESS_RETURN_CODE) {
            LogContext.instance().error("验证状态失败");
            response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_APPLE_STATUS_ERROR);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
        }
        if (receiptResponse.getReceipt() == null ||
                receiptResponse.getReceipt().getInAppList() == null) {
            LogContext.instance().error("凭证数量等于0");
            response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_APPLE_STATUS_ERROR);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
        }
        if (receiptResponse.getReceipt().getInAppList().size() > 1) {
            LogContext.instance().error("凭证数量大于1");
            response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_MULTI_ERROR);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
        }
        boolean isValidateOrderMoney = isValidateOrderMoney(appleInnerPayRequest, receiptResponse);
        if (!isValidateOrderMoney) {
            LogContext.instance().error("验证订单金额错误");
            response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_APPLE_MONEY_ERROR);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
        }
        AppleInnerPayReceipt receipt = convertBy(receiptResponse);
        String transactionId = receipt.getTransactionId();
        boolean isExist = appleInnerPayReceiptService.isExistReceipt(transactionId);
        if (isExist) {
            LogContext.instance().error("已经存在此凭证的成功验证记录");
            response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_DEAL_ERROR);
            return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
        }
        String orderId = PayUtils.generateAppleInnerPayOrderId();
        receipt.setRechargeOrderId(orderId);
        int userMoney = deal(appleInnerPayRequest, receipt, user,application);
        response.setVerifyResult(PayConstants.APPLE_PAY_VERIFY_SUCCESS);
        response.setOrderId(orderId);
        response.setCpIdOrId(user.getReturnUserId());
        response.setMoney(NumberUtil.convertYuanFromFen(userMoney));
        UserVipGrade newUserVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        response.setVipLevel(newUserVipGrade.getUserLevelId());
        float nextLevelRechargeAmount = 0f;
        float nextLevelNeedRechargeAmount = 0f;
        if (newUserVipGrade.getNextLevelRechargeAmount() > 0) {
            nextLevelRechargeAmount = newUserVipGrade.getNextLevelRechargeAmount();
            nextLevelNeedRechargeAmount = newUserVipGrade.getNextLevelNeedRechargeAmount();
        }
        response.setVipUpNeedMoney(nextLevelRechargeAmount);
        response.setNextVipLevelMoney(nextLevelNeedRechargeAmount);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
    }
    private int deal(AppleInnerPayRequest request, AppleInnerPayReceipt receipt,
                     User user,Application application) throws Exception {
        LogContext.instance().info("记录苹果应用内支付凭证记录");
        appleInnerPayReceiptService.insertReceipt(receipt);
        int moneyFen = NumberUtil.convertFenFromYuan(request.getOrderMoney());
        LogContext.instance().info("处理充值订单");
        dealRechargeOrder(request, user.getId(), moneyFen, receipt.getRechargeOrderId(),application);
        LogContext.instance().info("获取用户ID(" + user.getId() + ")的VIP信息");
        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        LogContext.instance().info("处理用户VIP信息");
        vipGradeService.dealVip(moneyFen, user.getId(), userVipGrade);
        LogContext.instance().info("更新用户信息及资金变动日志(订单金额)");
        MoneyLog moneyLog = PayUtils.buildRechargeMoneyLog(receipt.getRechargeOrderId(),
                PayType.APPLE_INNER, user.getId(), moneyFen, user.getMoney());
        userBaseService.updateMoneyById(user.getId(), user.getMoney(), moneyLog);
        return moneyLog.getMoneyBalance();
    }

    private boolean isValidateOrderMoney(AppleInnerPayRequest request,
                                         AppleInnerPayReceiptResponse receiptResponse) {
        AppleInnerPayReceiptResponse.Receipt.InApp inApp = receiptResponse.getReceipt().getInAppList().get(0);
        Map<String, Float> productMap = new HashMap<String, Float>(request.getProductList().size());
        for (AppleInnerPayProduct product : request.getProductList()) {
            productMap.put(product.getId(), product.getAmount());
        }
        return productMap.containsKey(inApp.getProductId())
                && request.getOrderMoney() == (productMap.get(inApp.getProductId()) * inApp.getQuantity());
    }

    private String getAppleVerifyResult(String receiptData) throws Exception {
        String json = "{\"receipt-data\":\"" + receiptData + "\"}";
        String receiptVerifyResult = HttpClientUtil.doPostJsonOrXmlHttpClient("apple-inner-pay",
                PayServerProperties.APPLE_INNER_PAY_URL, json, false,
                PayConstants.APPLE_PAY_VERIFY_TIME_OUT, PayConstants.APPLE_PAY_VERIFY_TIME_OUT);
        LogContext.instance().info("苹果返回的验证结果:" + receiptVerifyResult);
        return receiptVerifyResult;
    }

    private void dealRechargeOrder(AppleInnerPayRequest request, int userId, int moneyFen, String orderId,Application application) throws Exception {
//        Application application = appBaseService.getAppByAppId(request.getAppId());
        request.getOtherInfo().getAppId();
        RechargeOrder rechargeOrder = new RechargeOrder();
        rechargeOrder.setUserId(userId);
        rechargeOrder.setAppMemberId(application.getAppMemberId());
        rechargeOrder.setOrderId(orderId);
        rechargeOrder.setOrderMoney(moneyFen);
        rechargeOrder.setPayType(PayType.APPLE_INNER.getIndex());
        rechargeOrder.setGoodsName("苹果应用内支付充值");
        rechargeOrder.setQuantity(request.getOrderMoney());
        rechargeOrder.setAppId(application.getAppId());
        rechargeOrder.setChannel(request.getChannel());
        rechargeOrder.setSdkVersion("");
        rechargeOrder.setOsName(Os.IOS.getName());
        rechargeOrderService.addPayedRechargeOrder(rechargeOrder);
    }

    private AppleInnerPayReceipt convertBy(AppleInnerPayReceiptResponse response) {
        AppleInnerPayReceiptResponse.Receipt.InApp inApp = response.getReceipt().getInAppList().get(0);
        AppleInnerPayReceipt receipt = new AppleInnerPayReceipt();
        receipt.setStatus(response.getStatus());
        receipt.setEnvironment(response.getEnvironment());
        receipt.setReceiptType(response.getReceipt().getReceiptType());
        receipt.setAdamId(response.getReceipt().getAdamId());
        receipt.setAppItemId(response.getReceipt().getAppItemId());
        receipt.setBundleId(response.getReceipt().getBundleId());
        receipt.setApplicationVersion(response.getReceipt().getApplicationVersion());
        receipt.setReceiptCreationDateMs(response.getReceipt().getReceiptCreationDateMs());
        receipt.setRequestDateMs(response.getReceipt().getRequestDateMs());
        receipt.setOriginalApplicationVersion(response.getReceipt().getOriginalApplicationVersion());
        receipt.setQuantity(inApp.getQuantity());
        receipt.setProductId(inApp.getProductId());
        receipt.setTransactionId(inApp.getTransactionId());
        receipt.setOriginalTransactionId(inApp.getOriginalTransactionId());
        receipt.setPurchaseDateMs(inApp.getPurchaseDateMs());
        receipt.setOriginalPurchaseDateMs(inApp.getOriginalPurchaseDateMs());
        receipt.setTrialPeriod(inApp.isTrialPeriod());
        return receipt;
    }

}
