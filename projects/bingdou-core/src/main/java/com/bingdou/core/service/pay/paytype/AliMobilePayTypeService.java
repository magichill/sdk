package com.bingdou.core.service.pay.paytype;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.constants.UserConstants;
import com.bingdou.core.model.PayType;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.tools.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * Ö§¸¶±¦¿ì½ÝÖ§¸¶
 * Created by gaoshan on 16/11/12.
 */
@Service
public class AliMobilePayTypeService implements IPayTypeService {

    @Override
    public PayTypeResponse callPayType(PayTypeRequest payTypeRequest) throws Exception {
        String result = "partner=\"" + Constants.ALI_PID + "\"";
        result += "&seller_id=" + "\"" + Constants.ALI_SELLER_ID + "\"";
        result += "&out_trade_no=\"" + payTypeRequest.getOrderId() + "\"";
        result += "&subject=\"" + payTypeRequest.getOrderDesc() + "\"";
        result += "&body=\"" + payTypeRequest.getOrderDesc() + "\"";
        result += "&total_fee=" + "\"" + payTypeRequest.getMoney() + "\"";
        if (payTypeRequest.isRecharge())
            result += "&notify_url=\"" + PayTypeData.PAY_TYPE_ALI_MOBILE_RECHARGE_NOTIFY_URL + "\"";
        else
            result += "&notify_url=\"" + PayTypeData.PAY_TYPE_ALI_MOBILE_CONSUME_NOTIFY_URL + "\"";
        result += "&service=\"" + PayTypeData.PAY_TYPE_ALI_MOBILE_PAY_METHOD + "\"";
        result += "&_input_charset=" + "\"utf-8\"";
        result += "&payment_type=\"1\"";
        result += "&it_b_pay=\"30m\"";
        result += "&show_url=\"m.alipay.com\"";
        String sign = CodecUtils.rsaSign(result, UserConstants.ALI_PKCS8_RSA_PRIVATE_KEY);
        if (StringUtils.isEmpty(sign))
            return null;
        sign = URLEncoder.encode(sign, "UTF-8");
        result += "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
        PayTypeResponse response = new PayTypeResponse();
        response.setSuccess(true);
        response.setRequestParam(result);
        return response;
    }

    @Override
    public PayTypeCallBackResponse getPayTypeCallBackResult(HttpServletRequest request, boolean isRecharge, PayType payType) {
        return PayUtils.getAliPayTypeCallBackResult(request, payType);
    }

}
