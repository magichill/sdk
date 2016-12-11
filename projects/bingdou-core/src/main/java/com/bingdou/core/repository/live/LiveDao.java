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
        liveMapper.addLive(live.getId(),live.getLiveTitle(),live.getMid(),live.getLiveType(),live.getLivePicture(),live.getPushStream(),live.getPullStream(),live.getStreamName());
    }

    public Live getLiveById(int liveId){
        return liveMapper.getLiveById(liveId);
    }

    public List<Live> getLiveList(int start,int limit){
        return liveMapper.getLiveList(start,limit);
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
}
