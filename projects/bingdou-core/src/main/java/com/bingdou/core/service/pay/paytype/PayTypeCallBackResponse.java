package com.bingdou.core.service.pay.paytype;

import com.bingdou.core.model.PayType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * ֧����ʽ�ص������
 * Created by gaoshan on 16/12/27.
 */
public class PayTypeCallBackResponse {

    /**
     * ��������ID
     */
    private String bingdouOrderId;
    /**
     * ֧����ʽ����ID
     */
    private String orderId;
    /**
     * ֧�����(��)
     */
    private int amount;
    /**
     * �����û�ID
     */
    private int userId;
    /**
     * ֧����ʽ
     */
    private PayType payType;
    /**
     * �ص��ķ���MAP
     */
    private Map<String, String> paramMap;

    public String getBingdouOrderId() {
        return bingdouOrderId;
    }

    public void setBingdouOrderId(String bingdouOrderId) {
        this.bingdouOrderId = bingdouOrderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
