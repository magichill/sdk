package com.bingdou.core.cache;

import com.bingdou.core.model.live.Live;

import java.util.List;

/**
 * Created by gaoshan on 16/12/6.
 */
public interface ILiveCacheManager {

    boolean setLiveList(String key,List<Live> liveList);

    List<Live> getLiveList();

    Live getLiveInfo(int liveId);

    boolean setLiveInfo(int liveId,Live live);
}
