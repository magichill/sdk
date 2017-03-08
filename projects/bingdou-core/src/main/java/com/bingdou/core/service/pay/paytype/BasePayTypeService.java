package com.bingdou.core.service.pay.paytype;

import com.bingdou.core.model.PayType;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaoshan on 16/12/27.
 */
public abstract class BasePayTypeService {

    /**
     * 成功code
     */
    protected abstract String successCode();

    /**
     * 支付方式
     */
    protected abstract PayType getPayType();

    /**
     * 请求支付方式创建订单参数
     */
    protected abstract Object getRequestOrderParams(PayTypeRequest payTypeRequest);

    /**
     * 请求支付方式创建订单响应
     */
    protected abstract String getRequestOrderResult(Object param) throws Exception;

    /**
     * 请求支付方式创建订单结果解析
     */
    protected abstract PayTypeResponse getRequestOrderResponse(String payTypeResult);

    /**
     * 设置请求支付方式创建订单最终返回结果
     */
    protected abstract void setRequestOrderResponse(PayTypeResponse response);

    /**
     * 获取回调结果MAP
     */
    protected abstract Map<String, String> getCallBackResponseMap(HttpServletRequest request);

    /**
     * 验证回调结果是否合法
     */
    protected abstract boolean isValidCallBackResponse(Map<String, String> map);

    /**
     * 获取回调最终结果
     */
    protected abstract PayTypeCallBackResponse getCallBackResponse(Map<String, String> map);

    /**
     * 请求支付方式创建订单
     */
    protected PayTypeResponse requestOrder(PayTypeRequest payTypeRequest) throws Exception {
        LogContext.instance().info("请求支付方式创建订单(" + getPayType() + ")");
        Object param = getRequestOrderParams(payTypeRequest);
        LogContext.instance().info("请求参数:" + param);
        String payTypeResult = getRequestOrderResult(param);
        LogContext.instance().info("支付方式返回结果:" + payTypeResult);
        if (StringUtils.isEmpty(payTypeResult)) {
            LogContext.instance().error("请求支付方式结果为空");
            return null;
        }
        PayTypeResponse response = getRequestOrderResponse(payTypeResult);
        if (response == null) {
            LogContext.instance().error("请求支付方式结果解析为空");
            return null;
        }
        if (successCode().equals(response.getResultCode())) {
            setRequestOrderResponse(response);
            response.setSuccess(true);
        } else {
            LogContext.instance().error("请求创建订单失败");
            response.setSuccess(false);
        }
        return response;
    }

    protected PayTypeCallBackResponse callBack(HttpServletRequest request, boolean isRecharge) {
        LogContext.instance().info(getPayType() + (isRecharge ? "充值" : "直充") + "回调");
        Map<String, String> responseMap = getCallBackResponseMap(request);
        if (responseMap == null || responseMap.isEmpty()) {
            LogContext.instance().error("解析响应结果为空");
            return null;
        }
        LogContext.instance().info("响应:" + responseMap);
        LogContext.instance().info("验证回调结果是否合法");
        if (!isValidCallBackResponse(responseMap)) {
            LogContext.instance().error("回调验证失败");
            return null;
        }
        return getCallBackResponse(responseMap);
    }

}
