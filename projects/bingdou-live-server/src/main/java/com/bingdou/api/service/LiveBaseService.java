package com.bingdou.api.service;

import com.bingdou.core.model.Live;
import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.core.service.BaseService;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gaoshan on 16-11-8.
 */
public abstract class LiveBaseService extends BaseService {

    @Autowired
    private LiveDao liveDao;

    public Live getLiveInfo(int liveId){
        LogContext.instance().info("插入获取直播详细数据");
        return liveDao.getLiveById(liveId);
    }

    public List<Live> getLiveList(int start,int limit){
        LogContext.instance().info("插入获取直播列表数据");
        return liveDao.getLiveList(start,limit);
    }

    public boolean createLive(Live live){
        LogContext.instance().info("插入新直播数据");
        liveDao.addLiveIndex(live);
        if (live.getId() <= 0) {
            LogContext.instance().error("插入直播索引记录失败");
            return false;
        }
        LogContext.instance().info("插入直播记录");
        liveDao.addLive(live);
        return true;
    }

}
