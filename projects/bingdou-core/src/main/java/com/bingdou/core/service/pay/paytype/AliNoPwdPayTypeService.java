package com.bingdou.core.service.pay.paytype;


import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.model.AliNoPwdPayReturnCode;
import com.bingdou.core.model.PayType;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AliNoPwdPayTypeService implements IPayTypeService {

    @Override
    public PayTypeResponse callPayType(PayTypeRequest payTypeRequest) throws Exception {
        AliNoPwdPayReturnCode aliNoPwdPayReturnCode = requestAliNoPwdPay(payTypeRequest);
        LogContext.instance().info("支付宝免密支付结果(同步):" + aliNoPwdPayReturnCode);
        PayTypeResponse response = new PayTypeResponse();
        response.setAliNoPwdPayReturnCode(aliNoPwdPayReturnCode);
        return response;
    }

    @Override
    public PayTypeCallBackResponse getPayTypeCallBackResult(HttpServletRequest request, boolean isRecharge, PayType payType) {
        return PayUtils.getAliPayTypeCallBackResult(request, payType);
    }

    private AliNoPwdPayReturnCode requestAliNoPwdPay(PayTypeRequest payTypeRequest) throws Exception {
        Map<String, String> paramMap = buildRequestParam(payTypeRequest.getOrderId(),
                payTypeRequest.getOrderDesc(), payTypeRequest.getMoney(), payTypeRequest.getAliAgreementNo(),
                payTypeRequest.isRecharge());
        paramMap.put("sign", PayUtils.getSign4Ali(paramMap));
        String result = HttpClientUtil.doGetHttpClient("ali-no-pwd-pay", PayTypeData.PAY_TYPE_ALI_GATE_WAY,
                paramMap, null, PayTypeData.PAY_TYPE_ALI_TIMEOUT, PayTypeData.PAY_TYPE_ALI_TIMEOUT);
        LogContext.instance().info("支付宝免密授权结果:" + result);
        if (StringUtils.isEmpty(result)) {
            return AliNoPwdPayReturnCode.REQUEST_FAIL;
        }
        Document document = XmlUtil.getDocumentByXmlStr(result);
        String isSuccessStr = XmlUtil.getNodeTextByXPath(document, "/alipay/is_success");
        if ("T".equals(isSuccessStr)) {
            String resultCode = XmlUtil.getNodeTextByXPath(document, "/alipay/response/alipay/" + "result_code");
            if (resultCode.equals("ORDER_FAIL")) {
                return AliNoPwdPayReturnCode.ORDER_FAIL;
            } else if (resultCode.equals("ORDER_SUCCESS_PAY_SUCCESS")) {
                return AliNoPwdPayReturnCode.ORDER_SUCCESS_PAY_SUCCESS;
            } else if (resultCode.equals("ORDER_SUCCESS_PAY_FAIL")) {
                return AliNoPwdPayReturnCode.ORDER_SUCCESS_PAY_FAIL;
            } else if (resultCode.equals("ORDER_SUCCESS_PAY_INPROCESS")) {
                return AliNoPwdPayReturnCode.ORDER_SUCCESS_PAY_INPROCESS;
            } else {
                return AliNoPwdPayReturnCode.UNKNOWN;
            }
        }
        return AliNoPwdPayReturnCode.PAY_FAIL;
    }

    private Map<String, String> buildRequestParam(String orderId, String orderDesc, float moneyYuan,
                                                  String aliAgreementNo, boolean isRecharge) {
        Map<String, String> params = new HashMap<String, String>(11);
        params.put("service", PayTypeData.PAY_TYPE_ALI_NO_PWD_PAY_METHOD);
        params.put("partner", Constants.ALI_PID);
        params.put("_input_charset", "utf-8");
        params.put("sign_type", "MD5");
        if (isRecharge) {
            params.put("notify_url", PayTypeData.PAY_TYPE_ALI_NO_PWD_PAY_RECHARGE_NOTIFY_URL);
        } else {
            params.put("notify_url", PayTypeData.PAY_TYPE_ALI_NO_PWD_PAY_CONSUME_NOTIFY_URL);
        }
        params.put("out_trade_no", orderId);
        params.put("subject", orderDesc);
        params.put("product_code", PayTypeData.PAY_TYPE_ALI_NO_PWD_PAY_PRODUCT_CODE);
        params.put("total_fee", moneyYuan + "");
        params.put("body", orderDesc);
        params.put("agreement_info", "{\"agreement_no\":\"" + aliAgreementNo + "\"}");
        return params;
    }
}
