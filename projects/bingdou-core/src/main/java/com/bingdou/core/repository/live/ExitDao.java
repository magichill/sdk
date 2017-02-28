package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IExitMapper;
import com.bingdou.core.model.live.ExitRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/2/28.
 */
@Repository
public class ExitDao {

    @Autowired
    private IExitMapper iExitMapper;

    public void addExit(ExitRoom exitRoom){
        iExitMapper.addExit(exitRoom.getUserId(), exitRoom.getLiveId(),exitRoom.getMessageCount(),exitRoom.getPresentCount());
    }

    public ExitRoom getExitByUserIdAndLiveId(Integer userId, Integer liveId){
        return iExitMapper.getExitByUserIdAndLiveId(userId,liveId);
    }

    public void updateExit(ExitRoom exitRoom){
        iExitMapper.updateExit(exitRoom.getId(), exitRoom.getMessageCount(), exitRoom.getPresentCount());
    }
}
