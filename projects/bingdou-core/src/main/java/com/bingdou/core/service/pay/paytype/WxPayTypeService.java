package com.bingdou.core.service.pay.paytype;

import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.model.PayType;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.XmlUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付请求服务类
 * Created by gaoshan on 16/12/27.
 */
@Service
public class WxPayTypeService extends BasePayTypeService implements IPayTypeService {

    @Override
    public PayTypeResponse callPayType(PayTypeRequest payTypeRequest) throws Exception {
        return requestOrder(payTypeRequest);
    }

    @Override
    public PayTypeCallBackResponse getPayTypeCallBackResult(HttpServletRequest request, boolean isRecharge, PayType payType) {
        return callBack(request, isRecharge);
    }

    @Override
    protected String successCode() {
        return "SUCCESS";
    }

    @Override
    protected PayType getPayType() {
        return PayType.WEIXIN;
    }

    @Override
    protected Object getRequestOrderParams(PayTypeRequest payTypeRequest) {
        String randStr = DigestUtils.md5Hex(System.currentTimeMillis() + "");
        Map<String, String> paramMap = buildParamMap(payTypeRequest, randStr);
        String sign = getSign(paramMap);
        paramMap.put("sign", sign);
        return XmlUtil.buildXmlFromMap4Wx(paramMap);
    }

    @Override
    protected String getRequestOrderResult(Object param) throws Exception {
        return HttpClientUtil.doPostJsonOrXmlHttpClient("call-wx-mobile-pay", PayTypeData.PAY_TYPE_WX_URL,
                param.toString(), true, PayTypeData.PAY_TYPE_WX_TIMEOUT, PayTypeData.PAY_TYPE_WX_TIMEOUT);
    }

    @Override
    protected PayTypeResponse getRequestOrderResponse(String payTypeResult) {
        Map<String, String> map = XmlUtil.getMapFromXmlStr4OneLevel(payTypeResult);
        if (map == null || map.isEmpty()) {
            LogContext.instance().error("微信订单预处理结果为空");
            return null;
        }
        String wxSign = map.get("sign");
        map.remove("sign");
        String mySign = getSign(map);
        LogContext.instance().info("MY SIGN:" + mySign);
        if (!mySign.equals(wxSign)) {
            LogContext.instance().error("签名验证失败");
            return null;
        }
        String appId = PayTypeData.PAY_TYPE_WX_APP_ID;
        String partnerId = PayTypeData.PAY_TYPE_WX_MCH_ID;
        long timestamp = DateUtil.getTimeSecondsByTimeZone(8);
        String preOrderId = map.get("prepay_id");
        String nonceStr = DigestUtils.md5Hex(System.currentTimeMillis() + "");
        String wxPackage = PayTypeData.PAY_TYPE_WX_PACKAGE;
        Map<String, String> responseMap = new HashMap<String, String>();
        responseMap.put("appid", appId);
        responseMap.put("partnerid", partnerId);
        responseMap.put("prepayid", preOrderId);
        responseMap.put("noncestr", nonceStr);
        responseMap.put("timestamp", timestamp + "");
        responseMap.put("package", wxPackage);
        String responseSign = getSign(responseMap);
        PayTypeResponse response = new PayTypeResponse();
        response.setResultCode(map.get("return_code"));
        response.setResultMessage(map.get("return_msg"));
        WxResponse wxResponse = new WxResponse();
        wxResponse.setNonceStr(nonceStr);
        wxResponse.setPartnerId(partnerId);
        wxResponse.setTimestamp(timestamp);
        wxResponse.setWxAppId(appId);
        wxResponse.setWxPackage(wxPackage);
        wxResponse.setSign(responseSign);
        wxResponse.setPreOrderId(preOrderId);
        response.setWxResponse(wxResponse);
        return response;
    }

    @Override
    protected void setRequestOrderResponse(PayTypeResponse response) {
    }

    @Override
    protected Map<String, String> getCallBackResponseMap(HttpServletRequest request) {
        return XmlUtil.getMapByRequestStream(request);
    }

    @Override
    protected boolean isValidCallBackResponse(Map<String, String> map) {
        String sign = map.get("sign");
        if (StringUtils.isEmpty(sign)) {
            return false;
        }
        String mySign = getSign(map);
        if (!mySign.equals(sign)) {
            LogContext.instance().error("回调签名验证失败");
            return false;
        }
        String resultCode = map.get("result_code");
        if (!successCode().equals(resultCode)) {
            LogContext.instance().error("回调结果失败");
            return false;
        }
        return true;
    }

    @Override
    protected PayTypeCallBackResponse getCallBackResponse(Map<String, String> map) {
        PayTypeCallBackResponse callBackResponse = new PayTypeCallBackResponse();
        callBackResponse.setOrderId(map.get("transaction_id"));
        callBackResponse.setBingdouOrderId(map.get("out_trade_no"));
        callBackResponse.setAmount(Integer.parseInt(map.get("total_fee")));
        callBackResponse.setPayType(getPayType());
        callBackResponse.setUserId(Integer.parseInt(map.get("attach")));
        callBackResponse.setParamMap(map);
        return callBackResponse;
    }

    private String getSign(Map<String, String> postParamsMap) {
        Map<String, String> signMap = new HashMap<String, String>(postParamsMap);
        signMap.remove("sign");
        String signStr = PayUtils.getSignSeedByMap(signMap);
        return DigestUtils.md5Hex(signStr + "&key=" + PayTypeData.PAY_TYPE_WX_KEY).toUpperCase();
    }

    private Map<String, String> buildParamMap(PayTypeRequest payTypeRequest, String nonceStr) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", PayTypeData.PAY_TYPE_WX_APP_ID);
        map.put("mch_id", PayTypeData.PAY_TYPE_WX_MCH_ID);
        map.put("nonce_str", nonceStr);
        map.put("body", payTypeRequest.getOrderDesc());
        map.put("out_trade_no", payTypeRequest.getOrderId());
        map.put("fee_type", PayTypeData.PAY_TYPE_WX_FEE_TYPE);
        map.put("total_fee", payTypeRequest.getMoneyFen() + "");
        map.put("spbill_create_ip", payTypeRequest.getClientIP());
        if (payTypeRequest.isRecharge()) {
            map.put("notify_url", PayTypeData.PAY_TYPE_WX_RECHARGE_NOTIFY_URL);
        } else {
            map.put("notify_url", PayTypeData.PAY_TYPE_WX_CONSUME_NOTIFY_URL);
        }
        map.put("trade_type", PayTypeData.PAY_TYPE_WX_TRADE_TYPE);
        map.put("attach", payTypeRequest.getUserId() + "");
        return map;
    }

}
