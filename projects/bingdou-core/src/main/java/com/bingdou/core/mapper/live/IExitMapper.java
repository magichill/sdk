package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.ExitRoom;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/2/28.
 */
public interface IExitMapper {

    void addExit(@Param("userId") int userId, @Param("liveId") int liveId,@Param("messageCount") int messageCount,@Param("presentCount") int presentCount);

    ExitRoom getExitByUserIdAndLiveId(@Param("userId") int userId, @Param("liveId") int liveId);

    void updateExit(@Param("id") int id,@Param("messageCount") int messageCount,@Param("presentCount") int presentCount);
}
