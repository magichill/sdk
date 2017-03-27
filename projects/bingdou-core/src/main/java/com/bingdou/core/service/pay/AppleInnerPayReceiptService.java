package com.bingdou.core.service.pay;

import com.bingdou.core.model.AppleInnerPayReceipt;
import com.bingdou.core.repository.pay.AppleInnerPayReceiptDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppleInnerPayReceiptService {

    @Autowired
    private AppleInnerPayReceiptDao appleInnerPayReceiptDao;

    public void insertReceipt(AppleInnerPayReceipt receipt) {
        if (receipt == null)
            return;
        appleInnerPayReceiptDao.insertReceipt(receipt);
    }

    public boolean isExistReceipt(String transactionId) throws Exception {
        if (StringUtils.isEmpty(transactionId))
            throw new Exception("苹果应用内购买流水ID为空");
        int count = appleInnerPayReceiptDao.getSuccessReceiptCountByTransactionId(transactionId);
        return count > 0;
    }
}
