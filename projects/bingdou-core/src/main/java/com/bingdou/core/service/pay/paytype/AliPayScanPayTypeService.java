package com.bingdou.core.service.pay.paytype;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.model.PayType;
import com.bingdou.core.model.WebPayTypeRequest;
import com.bingdou.core.service.pay.WebPayTypeRequestService;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.tools.constants.KeyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付宝扫码支付服务类
 * Created by gaoshan on 16/11/28.
 */
@Service
public class AliPayScanPayTypeService implements IPayTypeService {

    @Autowired
    private WebPayTypeRequestService webPayTypeRequestService;

    @Override
    public PayTypeResponse callPayType(PayTypeRequest payTypeRequest) throws Exception {
        KeyGroup keyGroup = (KeyGroup) payTypeRequest.getBaseRequest().getRequest()
                .getAttribute(Constants.REQUEST_KEY_GROUP_NAME);
        WebPayTypeRequest webPayTypeRequest = PayUtils.buildWebPayTypeRequest(payTypeRequest);
        webPayTypeRequestService.addRequest(webPayTypeRequest, keyGroup);
        PayTypeResponse payTypeResponse = new PayTypeResponse();
        payTypeResponse.setSuccess(true);
        payTypeResponse.setRequestUrl(PayTypeData.WEB_PAY_TYPE_REQUEST_URL_PREFIX
                + PayType.ALI_SCAN.getIndex());
        return payTypeResponse;
    }

    @Override
    public PayTypeCallBackResponse getPayTypeCallBackResult(HttpServletRequest request, boolean isRecharge, PayType payType) {
        return PayUtils.getAliPayTypeCallBackResult(request, payType);
    }

}

