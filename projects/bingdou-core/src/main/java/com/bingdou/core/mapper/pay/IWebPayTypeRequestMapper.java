package com.bingdou.core.mapper.pay;

import com.bingdou.core.model.WebPayTypeRequest;

public interface IWebPayTypeRequestMapper {

    void addRequest(WebPayTypeRequest webPayTypeRequest);

    WebPayTypeRequest getRequestByOrderId(String orderId);

}
