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
}
