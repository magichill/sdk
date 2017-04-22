package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.ILiveMapper;
import com.bingdou.core.model.live.Live;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 16-11-8.
 */
@Repository
public class LiveDao {

    @Autowired
    private ILiveMapper liveMapper;

    public void addLiveIndex(Live live){
        liveMapper.addLiveIndex(live);
    }

    public void addLive(Live live){
        liveMapper.addLive(live.getId(),live.getLiveTitle(),live.getMid(),live.getLiveType(),
                live.getLivePicture(),live.getPushStream(),live.getPullStream(),
                live.getH5Url(),live.getStreamName(),live.getStartTime());
    }

    public Live getLiveById(int liveId){
        return liveMapper.getLiveById(liveId);
    }

    public Integer getLiveIdByMid(Integer liveId,Integer mid){
        return liveMapper.getLiveIdByMid(liveId,mid);
    }

    public List<Live> getLiveList(Integer status,String userId,int start,int limit){
        return liveMapper.getLiveList(status,userId,start,limit);
    }

    public List<Live> getFocusLiveList(Integer userId,int start,int limit){
        return liveMapper.getFocusLiveListByUserId(userId,start,limit);
    }

    public List<Live> getRecordLiveList(int start,int limit){
        return liveMapper.getRecordLiveList(start,limit);
    }
    public List<Live> getOnlineLiveList(int start,int limit){
        return liveMapper.getOnlineLiveList(start,limit);
    }

    public Integer getLiveByStreamName(String streamName){
        return liveMapper.getLiveByStreamName(streamName);
    }

    public Live getLiveInfoByStreamName(String streamName){
        return liveMapper.getLiveInfoByStreamName(streamName);
    }

    public void updateLiveStatus(int liveId,int status){
        liveMapper.updateLive(liveId,status);
    }

    public void updateStartLive(int liveId,int status){
        liveMapper.updateStartLive(liveId,status);
    }

    public void updateEndLive(int liveId,int status,String replayUrl){
        liveMapper.updateEndLive(liveId, status, replayUrl);
    }

    public void updateDescription(Live live){
        liveMapper.updateDescription(live);
    }

    public void updateLiveIndex(int liveId,int status,String replayUrl,String startTime,String endTime){
        liveMapper.updateLiveIndex(liveId, status, replayUrl,startTime,endTime);
    }

    public void updateAnnounceLive(Live live){
        liveMapper.updateAnnounceLive(live);
    }

    public void updateLiveVideoType(Integer liveId){
        liveMapper.updateVideoType(liveId);
    }

    public void updateIndexLiveVideoType(Integer liveId){
        liveMapper.updateIndexVideoType(liveId);
    }

    public void updateAnnounceLiveIndex(Live live){
        liveMapper.updateAnnounceLiveIndex(live);
    }

    public List<Live> getLiveInfoByMid(Integer userId,int start,int limit){
        return liveMapper.getLiveInfoByMid(userId,start,limit);
    }

    public List<Live> getLiveInfoByTagId(Integer tagId,int start,int limit){
        return liveMapper.getLiveInfoByTagId(tagId,start,limit);
    }

    public Integer getLiveCountByMid(Integer userId){
        return liveMapper.getLiveCountByMid(userId);
    }
}
