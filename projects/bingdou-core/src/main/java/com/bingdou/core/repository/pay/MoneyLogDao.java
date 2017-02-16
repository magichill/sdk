package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IMoneyLogMapper;
import com.bingdou.core.model.MoneyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 16/12/28.
 */
@Repository
public class MoneyLogDao {

    @Autowired
    private IMoneyLogMapper moneyLogMapper;

    public void add(MoneyLog moneyLog) {
        moneyLogMapper.add(moneyLog);
    }
}
