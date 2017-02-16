package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.ConsumeOrder;
import com.bingdou.core.model.OrderStatus;
import com.bingdou.core.model.User;
import com.bingdou.core.model.UserVipGrade;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.ConsumeOrderService;
import com.bingdou.core.service.pay.VipGradeService;
import com.bingdou.payserver.request.GetConsumeOrderRequest;
import com.bingdou.payserver.response.GetConsumeOrderResponse;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取消费订单服务类
 * Created by gaoshan on 17/1/4.
 */
@Service
public class GetConsumeOrderService extends BaseService implements IMethodService {

    @Autowired
    private ConsumeOrderService consumeOrderService;
    @Autowired
    private VipGradeService vipGradeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetConsumeOrderRequest baseRequest = new GetConsumeOrderRequest();
        baseRequest.parseRequest(request);
        return baseRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetConsumeOrderRequest getConsumeOrderRequest = (GetConsumeOrderRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(getConsumeOrderRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "get_consume_order";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return deal(baseRequest, user);
    }

    private ServiceResult deal(BaseRequest baseRequest, User user) throws Exception {
        GetConsumeOrderRequest getConsumeOrderRequest = (GetConsumeOrderRequest) baseRequest;
        if (StringUtils.isEmpty(getConsumeOrderRequest.getAccount())
                || StringUtils.isEmpty(getConsumeOrderRequest.getUserOrderId())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        GetConsumeOrderResponse response = new GetConsumeOrderResponse();
        ConsumeOrder order = consumeOrderService.getOrderByUserOrderIdAndAppId(getConsumeOrderRequest.getUserOrderId(),
                getConsumeOrderRequest.getOtherInfo().getAppId());
        if (order == null) {
            LogContext.instance().warn("订单不存在 : " + getConsumeOrderRequest.getUserOrderId());
            response.setStatus(OrderStatus.NOT_EXISTS.getIndex());
            return ServiceResultUtil.success(response);
        }
        response.setMoney(NumberUtil.convertYuanFromFen(user.getMoney()));
        response.setUserOrderId(order.getUserOrderId());
        response.setOrderMoney(NumberUtil.convertYuanFromFen(order.getConsumeMoney()));
        response.setStatus(order.getStatus());
        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        response.setVipLevel(userVipGrade.getUserLevelId());
        response.setVipUpNeedMoney(userVipGrade.getNextLevelNeedRechargeAmount());
        response.setNextVipLevelMoney(userVipGrade.getNextLevelRechargeAmount());
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setResult(JsonUtil.bean2JsonTree(response));
        return serviceResult;
    }
}
