package com.bingdou.core.service.pay;

import com.bingdou.core.model.WebPayTypeRequest;
import com.bingdou.core.repository.pay.WebPayTypeRequestDao;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.KeyGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebPayTypeRequestService {

    @Autowired
    private WebPayTypeRequestDao webPayTypeRequestDao;

    public void addRequest(WebPayTypeRequest webPayTypeRequest, KeyGroup keyGroup) throws Exception {
        LogContext.instance().info("������ҳ֧����ʽ�����¼");
        if (webPayTypeRequest == null || keyGroup == null) {
            throw new Exception("������ҳ֧����ʽ�����¼ʧ��");
        }
        String paySign = getPaySign(webPayTypeRequest.getOrderId(),
                webPayTypeRequest.getRequestSourceIndex(), keyGroup);
        if (StringUtils.isEmpty(paySign)) {
            throw new Exception("������ҳ֧����ʽ�����¼ʧ��");
        }
        webPayTypeRequest.setPaySign(paySign);
        webPayTypeRequestDao.addRequest(webPayTypeRequest);
    }

    public WebPayTypeRequest getRequest(String orderId) {
        if (StringUtils.isEmpty(orderId))
            return null;
        WebPayTypeRequest webPayTypeRequest = webPayTypeRequestDao.getRequestByOrderId(orderId);
        if (webPayTypeRequest == null)
            return null;
        return webPayTypeRequest;
    }

    public String getPaySign(String orderId, String requestSourceIndex, KeyGroup keyGroup) {
        String sign = "";
        try {
            String data = orderId + requestSourceIndex;
            sign = CodecUtils.getMySign(data, keyGroup);
        } catch (Exception e) {
            LogContext.instance().error(e, "����֧��ǩ��ʧ��");
        }
        return sign;
    }

}
