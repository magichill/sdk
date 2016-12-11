package com.bingdou.core.service.live;


import com.bingdou.core.repository.live.LiveDao;
import com.bingdou.core.service.live.callback.NotifyLiveStatus;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 16-11-26.
 */
@Service
public class RecordCallbackService {

    @Autowired
    private LiveDao liveDao;

    private static final String UPDATE_STATUS_URL = "http://intra.bingdou.tv:8088/1/data/live/update.json";

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

    //TODO 通知直播聊天室直播流状态
    /**
     *
     * @param streamName  流名称
     * @param status 流状态 false（关闭）  true（开启）
     * @return
     */
    public boolean notifyAppServer(String streamName,boolean status,String playUrl){
        NotifyLiveStatus notifyLiveStatus = new NotifyLiveStatus();
        notifyLiveStatus.setId(Long.valueOf(streamName));
        NotifyLiveStatus.Data data = new NotifyLiveStatus().new Data();
        if(StringUtils.isNotBlank(playUrl)) {
            data.setH5_play_url(playUrl);
        }
        data.setLive_status(status?"1":"2");
        notifyLiveStatus.setData(data);
        String param = JsonUtil.bean2JsonStr(notifyLiveStatus);
        try {
            HttpClientUtil.doPostJsonOrXmlHttpClient("直播状态通知请求",UPDATE_STATUS_URL,param,false,3000,3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
