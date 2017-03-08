package com.bingdou.api.service;

import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.api.response.CreateLiveResponse;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.LiveType;
import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.core.service.BaseService;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by gaoshan on 16-11-8.
 */
public abstract class LiveBaseService extends BaseService {

    @Autowired
    private LiveDao liveDao;

    public Live getLiveInfo(int liveId){
        LogContext.instance().info("获取直播详细数据");
        return liveDao.getLiveById(liveId);
    }

    public Live getLiveInfoByStreamName(String streamName){
        LogContext.instance().info("获取直播详细数据");
        return liveDao.getLiveInfoByStreamName(streamName);
    }

    public List<Live> getLiveList(int status,String userId,int start,int limit){
        LogContext.instance().info("获取直播列表数据");
        return liveDao.getLiveList(status,userId,start,limit);
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

    public boolean createLive(User user, CreateLiveRequest request, CreateLiveResponse response, String streamName){
        Live live = buildLive(user,request,response,streamName);
        return createLive(live);
    }

    protected Live buildLive(User user,CreateLiveRequest createLiveRequest, CreateLiveResponse response,String streamName){
        Live live = new Live();
        live.setLiveTitle(createLiveRequest.getLiveTitle());
        live.setStreamName(streamName);
        live.setMid(user.getId());
        live.setLiveType(LiveType.LIVE.getIndex());
        live.setPushStream(response.getPushUrl());
        live.setPullStream(response.getPlayUrl());
        live.setH5Url(response.getH5Url());
        live.setLivePicture(createLiveRequest.getLivePic());
        live.setLiveType(createLiveRequest.getVideoType());
        live.setStatus(createLiveRequest.getStatus());
        live.setOrientation(createLiveRequest.getOrientation());
        live.setTags(createLiveRequest.getTags());
        live.setPassword(createLiveRequest.getPassword());
        live.setPrice(NumberUtil.convertFenFromYuan(createLiveRequest.getPrice()));
        live.setRewardPercent(createLiveRequest.getPercent());

        return live;
    }

}
