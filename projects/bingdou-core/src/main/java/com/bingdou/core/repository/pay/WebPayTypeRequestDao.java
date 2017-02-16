package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IWebPayTypeRequestMapper;
import com.bingdou.core.model.WebPayTypeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WebPayTypeRequestDao {

    @Autowired
    private IWebPayTypeRequestMapper webPayTypeRequestMapper;

    public void addRequest(WebPayTypeRequest webPayTypeRequest) {
        webPayTypeRequestMapper.addRequest(webPayTypeRequest);
    }

    public WebPayTypeRequest getRequestByOrderId(String orderId) {
        return webPayTypeRequestMapper.getRequestByOrderId(orderId);
    }

}
