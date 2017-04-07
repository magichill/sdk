package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IConsumeMapper;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/3/20.
 */
@Repository
public class ConsumeDao {

    @Autowired
    private IConsumeMapper consumeMapper;

    public void addConsumeRecord(Live live,User user){
        consumeMapper.addConsumeRecord(live.getId(),user.getId(),live.getPrice(),live.getMid());
    }

    public Integer existRecord(Live live,User user){
        return consumeMapper.existRecord(live.getId(),user.getId());
    }

    public Integer consumePrice(User user){
        return consumeMapper.getConsumeByMid(user.getId());
    }

    public Integer incomePrice(User user){
        return consumeMapper.getIncomeByHostId(user.getId());
    }
}
