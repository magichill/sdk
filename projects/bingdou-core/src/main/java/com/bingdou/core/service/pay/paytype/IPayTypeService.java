package com.bingdou.core.service.pay.paytype;


import com.bingdou.core.model.PayType;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16/12/27.
 */
public interface IPayTypeService {

    PayTypeResponse callPayType(PayTypeRequest payTypeRequest) throws Exception;

    PayTypeCallBackResponse getPayTypeCallBackResult(HttpServletRequest request, boolean isRecharge, PayType payType);

}
