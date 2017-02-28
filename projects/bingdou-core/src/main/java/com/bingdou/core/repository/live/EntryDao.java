package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IEntryMapper;
import com.bingdou.core.model.live.EntryRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/2/28.
 */
@Repository
public class EntryDao {

    @Autowired
    private IEntryMapper iEntryMapper;

    public void addEntry(EntryRoom entryRoom){
        iEntryMapper.addEntry(entryRoom.getUserId(), entryRoom.getLiveId());
    }

    public EntryRoom getEntryByUserIdAndLiveId(Integer userId, Integer liveId){
        return iEntryMapper.getEntryByUserIdAndLiveId(userId,liveId);
    }

    public void updateEntry(EntryRoom entryRoom){
        iEntryMapper.updateEntry(entryRoom.getId(), entryRoom.getEnterCount());
    }
}
