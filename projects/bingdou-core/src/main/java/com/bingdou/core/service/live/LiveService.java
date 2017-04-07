package com.bingdou.core.service.live;

import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 17/3/30.
 */
@Service
public class LiveService {

    @Autowired
    private LiveDao liveDao;

    public Integer getLiveCountByMid(Integer userId){
        LogContext.instance().info("获取用户发布的视频数量");
        return liveDao.getLiveCountByMid(userId);
    }
}
