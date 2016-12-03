package com.bingdou.cdn.service;

import com.bingdou.cdn.constant.LiveType;
import com.bingdou.cdn.request.CreateLiveRequest;
import com.bingdou.cdn.response.CreateLiveResponse;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gaoshan on 16-11-18.
 */
@Service
public class CdnBaseService {

    @Autowired
    private LiveDao liveDao;

    @Transactional
    public void createLive(CreateLiveRequest createLiveRequest, CreateLiveResponse response){
        Live live = new Live();

        live.setLiveTitle(createLiveRequest.getLiveTitle());
        live.setMid(Integer.valueOf(createLiveRequest.getUserId()));
        live.setLiveType(LiveType.LIVE.getIndex());
        live.setPushStream(response.getPushUrl());
        live.setPullStream(response.getPlayUrl());

        liveDao.addLiveIndex(live);
        if (live.getId() <= 0) {
            LogContext.instance().error("插入直播索引记录失败");
            throw new RuntimeException("插入直播索引记录失败");
        }
        liveDao.addLive(live);
    }
}
