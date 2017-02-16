package com.bingdou.payserver.service;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.PayConstants;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.model.PayType;
import com.bingdou.core.model.WebPayTypeRequest;
import com.bingdou.core.service.pay.ConsumeOrderService;
import com.bingdou.core.service.pay.RechargeOrderService;
import com.bingdou.core.service.pay.WebPayTypeRequestService;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.OrderType;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import com.bingdou.tools.constants.KeyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebPayTypeService {

    @Autowired
    private WebPayTypeRequestService webPayTypeRequestService;
    @Autowired
    private RechargeOrderService rechargeOrderService;
    @Autowired
    private ConsumeOrderService consumeOrderService;

    public String getReturnUrl(HttpServletRequest request, String outTradeNo, String tradeStatus, int payType) {
        Map<String, String> params = PayUtils.getResultMapFromRequestMap(request.getParameterMap(), false);
        if (params == null || params.isEmpty())
            return "";
        String returnUrl = "";
        WebPayTypeRequest webPayTypeRequest = webPayTypeRequestService.getRequest(outTradeNo);
        if (webPayTypeRequest != null) {
            boolean isVerify = false;
            boolean isSuccessTrade = false;
            if (payType == PayType.ALI_SCAN.getIndex()) {
                isVerify = PayUtils.verifyAliResponse(params);
                isSuccessTrade = PayConstants.ALI_CALL_RESULT_FINISHED_CODE.equals(tradeStatus)
                        || PayConstants.ALI_CALL_RESULT_SUCCESS_CODE.equals(tradeStatus);
            }
            if (isVerify) {
                returnUrl = isSuccessTrade
                        ? webPayTypeRequest.getReturnUrl() + "?order_id=" + outTradeNo + "&is_success=1"
                        : webPayTypeRequest.getReturnUrl() + "?order_id=" + outTradeNo + "&is_success=0";
            } else {
                LogContext.instance().error("验证签名失败");
                returnUrl = webPayTypeRequest.getReturnUrl() + "?order_id=" + outTradeNo + "&is_success=0";
            }
        } else {
            LogContext.instance().error("不存在" + outTradeNo + "的订单");
        }
        return returnUrl;
    }

    public String getHtml(int payType, WebPayTypeRequest webPayTypeRequest, boolean isMobile) {
        String html = "";
        try {
            if (PayType.ALI_SCAN.getIndex() == payType) {
                html = getAliScanHtml(webPayTypeRequest, isMobile);
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "获取HTML错误");
        }
        return html;
    }

    public String validWebTypeRequestRequest(String orderId, String paySign, int payType,
                                             WebPayTypeRequest webPayTypeRequest,
                                             KeyGroup keyGroup) {
        if (webPayTypeRequest == null) {
            return "未获取到订单信息";
        }
        if (webPayTypeRequest.getPayType() != payType) {
            return "支付方式错误";
        }
        boolean isNoPayOrder;
        if (webPayTypeRequest.getOrderType() == 1) {
            LogContext.instance().info("充值订单");
            isNoPayOrder = rechargeOrderService.isNoPayOrder(orderId);
        } else {
            LogContext.instance().info("直充订单");
            isNoPayOrder = consumeOrderService.isNoPayOrder(orderId);
        }
        if (!isNoPayOrder) {
            return "订单已经支付";
        }
        if (DateUtil.getCurrentTimeSeconds() - webPayTypeRequest.getCreateTime()
                > PayConstants.WEB_PAY_TYPE_TIME_OUT_SECONDS) {
            return "您的订单超过30分钟未处理,已经过期,请重新创建订单";
        }
        String myPaySign = webPayTypeRequestService.getPaySign(webPayTypeRequest.getOrderId(),
                webPayTypeRequest.getRequestSourceIndex(), keyGroup);
        if (!myPaySign.equals(paySign)) {
            return "签名错误";
        }
        return "";
    }

    private Map<String, String> buildChinaPayUnionParams(WebPayTypeRequest webPayTypeRequest) {
        Map<String, String> params = new HashMap<String, String>();
        Date now = new Date();
        params.put("Version", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_VERSION);
        params.put("MerId", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_MER_ID);
        params.put("MerOrderNo", webPayTypeRequest.getOrderId());
        params.put("TranDate", DateUtil.format(now, DateUtil.YYYYMMDD));
        params.put("TranTime", DateUtil.format(now, DateUtil.HHMMSS));
        params.put("OrderAmt", webPayTypeRequest.getOrderMoney() + "");
        params.put("BusiType", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_BUSI_TYPE);
        params.put("CurryNo", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_CURRY_NO);
        params.put("MerPageUrl", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_RETURN_URL);
        if (webPayTypeRequest.getOrderType() == OrderType.RECHARGE.getIndex()) {
            params.put("MerBgUrl", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_RECHARGE_NOTIFY_URL);
        } else {
            params.put("MerBgUrl", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_CONSUME_NOTIFY_URL);
        }
        params.put("CommodityMsg", webPayTypeRequest.getOrderDesc());
        params.put("MerResv", webPayTypeRequest.getUserId() + "");
        params.put("PayTimeOut", 1440 + "");
        return params;
    }

    private Map<String, String> buildAliScanParams(WebPayTypeRequest webPayTypeRequest, boolean isMobile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("service", PayTypeData.PAY_TYPE_ALI_SCAN_METHOD);
        params.put("partner", Constants.ALI_PID);
        params.put("seller_id", Constants.ALI_PID);
        params.put("_input_charset", "utf-8");
        params.put("sign_type", "MD5");
        params.put("payment_type", "1");
        if (webPayTypeRequest.getOrderType() == OrderType.RECHARGE.getIndex()) {
            params.put("notify_url", PayTypeData.PAY_TYPE_ALI_PAY_SCAN_RECHARGE_NOTIFY_URL);
        } else {
            params.put("notify_url", PayTypeData.PAY_TYPE_ALI_PAY_SCAN_CONSUME_NOTIFY_URL);
        }
        params.put("return_url", PayTypeData.PAY_TYPE_ALI_PAY_SCAN_RETURN_URL);
        params.put("out_trade_no", webPayTypeRequest.getOrderId());
        params.put("subject", webPayTypeRequest.getOrderDesc());
        params.put("total_fee", NumberUtil.convertYuanFromFen(webPayTypeRequest.getOrderMoney()) + "");
        params.put("body", webPayTypeRequest.getOrderDesc());
        params.put("extra_common_param", webPayTypeRequest.getUserId() + "");
        if (isMobile) {
            params.put("qr_pay_mode", "4");
            params.put("qrcode_width", "300");
        }
        return params;
    }

    private String getAliScanHtml(WebPayTypeRequest webPayTypeRequest, boolean isMobile) throws UnsupportedEncodingException {
        Map<String, String> paramMap = buildAliScanParams(webPayTypeRequest, isMobile);
        String aliSign = PayUtils.getSign4Ali(paramMap);
        paramMap.put("sign", aliSign);
        return buildHtmlContent(paramMap, "alipaysubmit", PayTypeData.PAY_TYPE_ALI_URL_WITH_ENCODING, "post");
    }

//    private String getChinaPayUnionHtml(WebPayTypeRequest webPayTypeRequest) throws UnsupportedEncodingException {
//        Map<String, String> paramMap = buildChinaPayUnionParams(webPayTypeRequest);
//        String sign = PayUtils.getSign4ChinaPayUnion(paramMap);
//        paramMap.put("Signature", sign);
//        return buildHtmlContent(paramMap, "chinapayunionsubmit", PayTypeData.PAY_TYPE_CHINA_PAY_UNION_URL, "post");
//    }

    private String buildHtmlContent(Map<String, String> paramMap, String formId,
                                    String actionUrl, String formMethod) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<form id=\"" + formId + "\" name=\"" + formId + "\" action=\""
                + actionUrl + "\" method=\"" + formMethod + "\">");
        for (String key : paramMap.keySet()) {
            String value = paramMap.get(key);
            htmlContent.append("<input type=\"hidden\" name=\"").append(key)
                    .append("\" value=\"").append(value).append("\"/>");
        }
        htmlContent.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
        htmlContent.append("<script>document.forms['" + formId + "'].submit();</script>");
        String html = htmlContent.toString();
        LogContext.instance().info("HTML:" + html);
        return html;
    }

}
