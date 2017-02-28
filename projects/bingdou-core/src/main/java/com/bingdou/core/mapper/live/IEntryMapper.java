package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.EntryRoom;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/2/27.
 */
public interface IEntryMapper {

    void addEntry(@Param("userId") int userId,@Param("liveId") int liveId);

    EntryRoom getEntryByUserIdAndLiveId(@Param("userId") int userId, @Param("liveId") int liveId);

    void updateEntry(@Param("id") int id,@Param("enterCount") int enterCount);
}
