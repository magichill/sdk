package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.Live;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 16-11-8.
 */
public interface ILiveMapper {

    void addLiveIndex(Live live);
    void updateAnnounceLive(Live live);
    void updateAnnounceLiveIndex(Live live);

    void addLive(@Param("liveId") int liveId, @Param("liveTitle") String liveTitle, @Param("mid") Integer mid,
                 @Param("liveType") int liveType,@Param("livePicture") String livePicture, @Param("pushStream") String pushStream,
                 @Param("pullStream") String pullStream,@Param("h5Url") String h5Url,@Param("streamName") String streamName);

    Live getLiveById(@Param("liveId") int liveId);

    List<Live> getLiveList(@Param("status") Integer status,@Param("userId") String userId,@Param("start") int start, @Param("limit") int limit);
    List<Live> getFocusLiveListByUserId(@Param("userId") Integer userId,@Param("start") int start, @Param("limit") int limit);
    List<Live> getOnlineLiveList(@Param("start") int start, @Param("limit") int limit);
    List<Live> getRecordLiveList(@Param("start") int start, @Param("limit") int limit);

    Integer getLiveByStreamName(@Param("streamName") String streamName);

    Live getLiveInfoByStreamName(@Param("streamName") String streamName);
    void updateLive(@Param("liveId") int liveId,@Param("status") int status);
    void updateLiveIndex(@Param("liveId") int liveId,@Param("status") Integer status,
                         @Param("replayUrl") String replayUrl,@Param("startTime") String startTime,
                         @Param("endTime") String endTime);
    void updateStartLive(@Param("liveId") int liveId,@Param("status") Integer status);
    void updateEndLive(@Param("liveId") int liveId,@Param("status") Integer status,@Param("replayUrl") String replayUrl);

    List<Live> getLiveInfoByMid(@Param("userId") Integer userId,@Param("start") int start, @Param("limit") int limit);
}
