package com.bingdou.core.service.live;


import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.core.service.live.callback.NotifyLiveStatus;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by gaoshan on 16-11-26.
 */
@Service
public class RecordCallbackService {

    @Autowired
    private LiveDao liveDao;

    @Value("${update.status.url:http://intra.bingdou.tv:8088/1/data/live/update.json}")
    private String UPDATE_STATUS_URL;

    public boolean checkLiveExist(String streamName){
        boolean isExist = false;
        if(StringUtils.isNotBlank(streamName)) {
            int count = liveDao.getLiveByStreamName(streamName);
            if(count == 1){
                isExist = true;
            }
        }
        LogContext.instance().info("检查直播流是否存在："+isExist);
        return isExist;
    }

    public Live getLiveInfo(String streamName){
        LogContext.instance().info("检查直播流是否存在");
        return liveDao.getLiveInfoByStreamName(streamName);
    }

    public boolean updateLiveStatus(int liveId,boolean isOpen){
        LogContext.instance().info("更新直播流状态");
        int status = 1;
        if(!isOpen) {
            status = 2;
        }
        liveDao.updateLiveStatus(liveId,status);
        return true;
    }
    //TODO 通知直播聊天室直播流状态
    /**
     *
     * @param streamName  流名称
     * @param status 流状态 false（关闭）  true（开启）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean notifyAppServer(String streamName,boolean status,String playUrl){
        if(StringUtils.isEmpty(streamName)){
            LogContext.instance().info("通知流状态参数不正确");
            return false;
        }
        Live live = liveDao.getLiveInfoByStreamName(streamName);
        if (status) {
            liveDao.updateLiveIndex(live.getId(),1,null,"startTime",null);
            liveDao.updateStartLive(live.getId(), 1);
        } else {
            if(StringUtils.isBlank(playUrl)){
                liveDao.updateLiveIndex(live.getId(),2,null,null,"endTime");
                liveDao.updateEndLive(live.getId(), 2, null);
            }else {
                liveDao.updateLiveIndex(live.getId(), 2, playUrl,null,null);
                liveDao.updateEndLive(live.getId(), 2, playUrl);
            }
        }
        return true;
    }
}
