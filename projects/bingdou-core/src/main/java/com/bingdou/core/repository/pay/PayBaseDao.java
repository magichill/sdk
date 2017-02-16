package com.bingdou.core.repository.pay;

import com.bingdou.core.mapper.pay.IPayBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 16/11/2.
 */
@Repository
public class PayBaseDao {

    @Autowired
    private IPayBaseMapper payBaseMapper;

    public String getActivityPrice(int id) {
        return payBaseMapper.getActivityPrice(id);
    }
}
