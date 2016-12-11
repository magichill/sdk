package com.bingdou.core.cache;

import com.bingdou.core.model.live.Live;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gaoshan on 16/12/6.
 */
@Component
public class LiveCacheManager extends JedisManager implements ILiveCacheManager {


    @Override
    public boolean setLiveList(String key, List<Live> liveList) {
        return false;
    }

    @Override
    public List<Live> getLiveList() {
        return null;
    }

    @Override
    public Live getLiveInfo(int liveId) {
        return null;
    }

    @Override
    public boolean setLiveInfo(int liveId, Live live) {
        return false;
    }
}
