package com.bingdou.core.service.live;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.ConsumeDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 17/3/20.
 */
@Service
public class ConsumeService {

    @Autowired
    private ConsumeDao consumeDao;

    public void addConsumeRecord(Live live,User user){
        LogContext.instance().info("插入用户消费记录");
        consumeDao.addConsumeRecord(live,user);
    }

    public boolean exisRecord(Live live,User user){
        LogContext.instance().info("查询用户是否购买过直播");
        Integer existRecord = consumeDao.existRecord(live,user);
        if(existRecord > 0){
            LogContext.instance().info("用户已购买过该视频");
            return true;
        }else{
            LogContext.instance().info("用户未购买过该视频");
            return false;
        }
    }

    public Integer getConsumePrice(User user){
        LogContext.instance().info("获取用户消费冰豆");
        Integer consumePrice = consumeDao.consumePrice(user);
        if(consumePrice == null){
            return 0;
        }
        return consumePrice;
    }

    public Integer getIncomePrice(User user){
        LogContext.instance().info("获取用户挣取豆币");
        Integer incomePrice = consumeDao.incomePrice(user);
        if(incomePrice == null)
            return 0;
        return incomePrice;
    }
}
