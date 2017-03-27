package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IAppleInnerPayReceiptMapper;
import com.bingdou.core.model.AppleInnerPayReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AppleInnerPayReceiptDao {

    @Autowired
    private IAppleInnerPayReceiptMapper appleInnerPayReceiptMapper;

    public void insertReceipt(AppleInnerPayReceipt receipt) {
        appleInnerPayReceiptMapper.insertReceipt(receipt);
    }

    public int getSuccessReceiptCountByTransactionId(String transactionId) {
        return appleInnerPayReceiptMapper.getSuccessReceiptCountByTransactionId(transactionId);
    }
}
