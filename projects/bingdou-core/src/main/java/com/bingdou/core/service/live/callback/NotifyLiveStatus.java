package com.bingdou.core.service.live.callback;

import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by gaoshan on 16/12/10.
 */
public class NotifyLiveStatus {

    private Long id;

    private Data data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String h5_play_url;
        private String live_status;

        public String getH5_play_url() {
            return h5_play_url;
        }

        public void setH5_play_url(String h5_play_url) {
            this.h5_play_url = h5_play_url;
        }

        public String getLive_status() {
            return live_status;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }
    }

    public static void main(String[] args){
        String streamId = "1029028816420935";
        boolean status = false;
        String playUrl = "";
        String url = "http://intra.bingdou.tv:8088/1/data/live/update.json";
        NotifyLiveStatus notifyLiveStatus = new NotifyLiveStatus();
        notifyLiveStatus.setId(Long.valueOf(streamId));
        NotifyLiveStatus.Data data = new NotifyLiveStatus().new Data();
        if(StringUtils.isNotBlank(playUrl)) {
            data.setH5_play_url(playUrl);
        }
        data.setLive_status(status?"1":"2");
        notifyLiveStatus.setData(data);
        String param = JsonUtil.bean2JsonStr(notifyLiveStatus);
        System.out.println("请求参数："+param);
        try {
            String result = HttpClientUtil.doPostJsonOrXmlHttpClient("直播状态通知请求",url,param,false,3000,3000);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
