package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.Live;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 16-11-8.
 */
public interface ILiveMapper {

    void addLiveIndex(Live live);

    void addLive(@Param("liveId") int liveId, @Param("liveTitle") String liveTitle, @Param("mid") int mid,
                 @Param("livePicture") String livePicture, @Param("pushStream") String pushStream,
                 @Param("pullStream") String pullStream);

    Live getLiveById(@Param("liveId") int liveId);

    List<Live> getLiveList(@Param("start") int start, @Param("limit") int limit);
}
