package com.bingdou.core.model;

public enum AliNoPwdPayReturnCode {

    /**
     * ֧��ʧ��
     */
    PAY_FAIL,
    /**
     * ��������ʧ��
     */
    REQUEST_FAIL,
    /**
     * �µ�ʧ��
     */
    ORDER_FAIL,
    /**
     * �µ��ɹ�����֧���ɹ�
     */
    ORDER_SUCCESS_PAY_SUCCESS,
    /**
     * �µ��ɹ�֧��ʧ��
     */
    ORDER_SUCCESS_PAY_FAIL,
    /**
     * �µ��ɹ�֧��������
     */
    ORDER_SUCCESS_PAY_INPROCESS,
    /**
     * ������δ֪
     */
    UNKNOWN

}
