package com.bingdou.core.mapper.pay;


import com.bingdou.core.model.AppleInnerPayReceipt;

/**
 * 苹果应用内支付凭证MAPPER
 * Created by gaoshan on 17/3/25.
 */
public interface IAppleInnerPayReceiptMapper {

    void insertReceipt(AppleInnerPayReceipt receipt);

    int getSuccessReceiptCountByTransactionId(String transactionId);

}
