package com.bingdou.api.service;

import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.api.request.UpdateAnnouceRequest;
import com.bingdou.api.response.CreateLiveResponse;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.LiveType;
import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.core.service.BaseService;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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

    public List<Live> getLiveList(Integer status,String userId,int start,int limit){
        LogContext.instance().info("获取直播列表数据");
        return liveDao.getLiveList(status,userId,start,limit);
    }

    public List<Live> getFocusLiveList(Integer userId,int start,int limit){
        LogContext.instance().info("获取用户关注的主播直播数据");
        return liveDao.getFocusLiveList(userId,start,limit);
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
        LogContext.instance().info("创建直播数据");
        Live live = buildLive(user,request,response,streamName);
        return createLive(live);
    }

    public List<Live> getLiveInfoByMid(User user,int start,int limit){
        LogContext.instance().info("获取用户直播数据列表");
        return liveDao.getLiveInfoByMid(user.getId(),start,limit);
    }

    public List<Live> getLiveInfoByTagId(Integer tagId,int start,int limit){
        LogContext.instance().info("获取标签直播数据列表");
        return liveDao.getLiveInfoByTagId(tagId,start,limit);
    }

    public boolean updateAnnounceLive(User user, UpdateAnnouceRequest request){
        LogContext.instance().info("更新预告内容");
        Live live = getLiveInfo(request.getLiveId());
        if(live == null){
            LogContext.instance().info("直播不存在");
            return false;
        }
        if(live.getMid() != user.getId()){
            LogContext.instance().info("无权限修改直播预告");
            return false;
        }
        if(request.getOrientation() != null){
            live.setOrientation(request.getOrientation());
        }
        if(StringUtils.isNotEmpty(request.getCoverUrl())) {
            live.setLivePicture(request.getCoverUrl());
        }
        if(request.getPrice() != null) {
            live.setPrice(request.getPrice());
        }
        if(StringUtils.isNotEmpty(request.getPassword())) {
            live.setPassword(request.getPassword());
        }
        if(request.getPercent() != null) {
            live.setRewardPercent(request.getPercent());
        }
        if(StringUtils.isNotEmpty(request.getTitle())){
            live.setLiveTitle(request.getTitle());
        }
        if(request.getStartAt()!=null){
            Date date = new Date(request.getStartAt());
            live.setStartTime(date);
        }
        if(request.getVideoType()!= null){
            live.setLiveType(request.getVideoType());
        }
        liveDao.updateAnnounceLiveIndex(live);
        liveDao.updateAnnounceLive(live);
        return true;
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
        live.setPrice(createLiveRequest.getPrice());
        live.setRewardPercent(createLiveRequest.getPercent());
        live.setStartTime(new Date(createLiveRequest.getStartAt()));

        return live;
    }

}
