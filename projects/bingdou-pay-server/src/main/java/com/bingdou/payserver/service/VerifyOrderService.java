package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.*;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.ConsumeOrderService;
import com.bingdou.core.service.pay.RechargeOrderService;
import com.bingdou.core.service.pay.VipGradeService;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.OrderType;
import com.bingdou.payserver.request.VerifyOrderRequest;
import com.bingdou.payserver.response.VerifyOrderResponse;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证订单服务类
 * Created by gaoshan on 16/12/25.
 */
@Service
public class VerifyOrderService extends BaseService implements IMethodService {

    @Autowired
    private RechargeOrderService rechargeOrderService;
    @Autowired
    private VipGradeService vipGradeService;
    @Autowired
    private ConsumeOrderService consumeOrderService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        VerifyOrderRequest verifyOrderRequest = new VerifyOrderRequest();
        verifyOrderRequest.parseRequest(request);
        return verifyOrderRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        VerifyOrderRequest request = (VerifyOrderRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    @Override
    public String getMethodName() {
        return "verify_order";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        Application application = appBaseService.getAppByAppId(baseRequest.getAppId());
        return dealVerify(baseRequest, user, request, application);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        Application application = getValidApplication4Client(baseRequest);
        return dealVerify(baseRequest, user, request, application);
    }

    private ServiceResult dealVerify(BaseRequest baseRequest, User user,
                                     HttpServletRequest request, Application application) throws Exception {
        VerifyOrderRequest verifyOrderRequest = (VerifyOrderRequest) baseRequest;
        if (StringUtils.isEmpty(verifyOrderRequest.getOrderId())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        if (verifyOrderRequest.getOrderType() == OrderType.RECHARGE.getIndex()) {
            return deal(request, verifyOrderRequest, user, application, true);
        } else if (verifyOrderRequest.getOrderType() == OrderType.CONSUME.getIndex()) {
            return deal(request, verifyOrderRequest, user, application, false);
        } else {
            return ServiceResultUtil.illegal("错误的验证订单类型");
        }
    }

    private ServiceResult deal(HttpServletRequest request, VerifyOrderRequest verifyOrderRequest,
                               User user, Application application, boolean isRecharge) throws Exception {
        String channel;
        String sdkVersion = "";
        if (isClientRequest(request)) {
            sdkVersion = verifyOrderRequest.getOtherInfo().getSdkVersion();
            channel = verifyOrderRequest.getOtherInfo().getChannel();
        } else {
            channel = verifyOrderRequest.getChannel();
        }
        VerifyOrderResponse response = new VerifyOrderResponse();
        int verifyResult;
        if (isRecharge) {
            RechargeOrder rechargeOrder;
            if (PayType.UPMP.getIndex() == verifyOrderRequest.getPayType()) {
                rechargeOrder = rechargeOrderService.getByUnionOrderIdAndUserId(verifyOrderRequest.
                        getOrderId(), user.getId());
            } else {
                rechargeOrder = rechargeOrderService.getByOrderIdAndUserId(verifyOrderRequest.
                        getOrderId(), user.getId());
            }
            verifyResult = PayUtils.validateOrderStatus(rechargeOrder);
        } else {
            int status = consumeOrderService.getStatusByUserOrderIdAndUserId(user.getId(),
                    verifyOrderRequest.getOrderId());
            verifyResult = PayUtils.validateOrderStatus(status);
        }
        response.setVerifyResult(verifyResult);
        if (verifyResult == OrderStatus.PAYED.getIndex()) {
            int virtualMoney = getVirtualMoneyFen4Show(user.getId(), application.getOs(), sdkVersion,
                    application.getAppId(), channel, false);
            LogContext.instance().info("订单已经支付成功,查询用户信息并返回");
            UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
            response.setCpIdOrId(user.getReturnUserId());
            if (user.getMoney() != null)
                response.setMoney(NumberUtil.convertYuanFromFen(user.getMoney() + virtualMoney));
            response.setVipLevel(userVipGrade.getUserLevelId());
            float nextLevelRechargeAmount = 0f;
            float nextLevelNeedRechargeAmount = 0f;
            if (userVipGrade.getNextLevelRechargeAmount() > 0) {
                nextLevelRechargeAmount = userVipGrade.getNextLevelRechargeAmount();
                nextLevelNeedRechargeAmount = userVipGrade.getNextLevelNeedRechargeAmount();
            }
            response.setVipUpNeedMoney(nextLevelRechargeAmount);
            response.setNextVipLevelMoney(nextLevelNeedRechargeAmount);
        }
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
    }
}
