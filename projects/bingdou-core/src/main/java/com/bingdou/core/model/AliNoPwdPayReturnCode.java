package com.bingdou.core.model;

public enum AliNoPwdPayReturnCode {

    /**
     * 支付失败
     */
    PAY_FAIL,
    /**
     * 请求免密失败
     */
    REQUEST_FAIL,
    /**
     * 下单失败
     */
    ORDER_FAIL,
    /**
     * 下单成功并且支付成功
     */
    ORDER_SUCCESS_PAY_SUCCESS,
    /**
     * 下单成功支付失败
     */
    ORDER_SUCCESS_PAY_FAIL,
    /**
     * 下单成功支付处理中
     */
    ORDER_SUCCESS_PAY_INPROCESS,
    /**
     * 处理结果未知
     */
    UNKNOWN

}
