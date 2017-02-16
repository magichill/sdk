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
     * �ɹ�code
     */
    protected abstract String successCode();

    /**
     * ֧����ʽ
     */
    protected abstract PayType getPayType();

    /**
     * ����֧����ʽ������������
     */
    protected abstract Object getRequestOrderParams(PayTypeRequest payTypeRequest);

    /**
     * ����֧����ʽ����������Ӧ
     */
    protected abstract String getRequestOrderResult(Object param) throws Exception;

    /**
     * ����֧����ʽ���������������
     */
    protected abstract PayTypeResponse getRequestOrderResponse(String payTypeResult);

    /**
     * ��������֧����ʽ�����������շ��ؽ��
     */
    protected abstract void setRequestOrderResponse(PayTypeResponse response);

    /**
     * ��ȡ�ص����MAP
     */
    protected abstract Map<String, String> getCallBackResponseMap(HttpServletRequest request);

    /**
     * ��֤�ص�����Ƿ�Ϸ�
     */
    protected abstract boolean isValidCallBackResponse(Map<String, String> map);

    /**
     * ��ȡ�ص����ս��
     */
    protected abstract PayTypeCallBackResponse getCallBackResponse(Map<String, String> map);

    /**
     * ����֧����ʽ��������
     */
    protected PayTypeResponse requestOrder(PayTypeRequest payTypeRequest) throws Exception {
        LogContext.instance().info("����֧����ʽ��������(" + getPayType() + ")");
        Object param = getRequestOrderParams(payTypeRequest);
        LogContext.instance().info("�������:" + param);
        String payTypeResult = getRequestOrderResult(param);
        LogContext.instance().info("֧����ʽ���ؽ��:" + payTypeResult);
        if (StringUtils.isEmpty(payTypeResult)) {
            LogContext.instance().error("����֧����ʽ���Ϊ��");
            return null;
        }
        PayTypeResponse response = getRequestOrderResponse(payTypeResult);
        if (response == null) {
            LogContext.instance().error("����֧����ʽ�������Ϊ��");
            return null;
        }
        if (successCode().equals(response.getResultCode())) {
            setRequestOrderResponse(response);
            response.setSuccess(true);
        } else {
            LogContext.instance().error("���󴴽�����ʧ��");
            response.setSuccess(false);
        }
        return response;
    }

    protected PayTypeCallBackResponse callBack(HttpServletRequest request, boolean isRecharge) {
        LogContext.instance().info(getPayType() + (isRecharge ? "��ֵ" : "ֱ��") + "�ص�");
        Map<String, String> responseMap = getCallBackResponseMap(request);
        if (responseMap == null || responseMap.isEmpty()) {
            LogContext.instance().error("������Ӧ���Ϊ��");
            return null;
        }
        LogContext.instance().info("��Ӧ:" + responseMap);
        LogContext.instance().info("��֤�ص�����Ƿ�Ϸ�");
        if (!isValidCallBackResponse(responseMap)) {
            LogContext.instance().error("�ص���֤ʧ��");
            return null;
        }
        return getCallBackResponse(responseMap);
    }

}
