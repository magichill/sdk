package com.bingdou.core.service.live.callback;

import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

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
        private String online_status;
        private String start_time;
        private String end_time;

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

        public String getOnline_status() {
            return online_status;
        }

        public void setOnline_status(String online_status) {
            this.online_status = online_status;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }

//    public static void main(String[] args){
//        String streamId = "1034938674643381";
//        boolean status = false;
//        String playUrl = "http://cncplayback.bingdou.tv/live-1034938674643381--20161215004024.flv";
//        String url = "http://intra.bingdou.tv:8088/1/data/live/update.json";
//        NotifyLiveStatus notifyLiveStatus = new NotifyLiveStatus();
//        notifyLiveStatus.setId(Long.valueOf(streamId));
//        NotifyLiveStatus.Data data = new NotifyLiveStatus().new Data();
//        if(status) {
//            data.setLive_status("1");
//            data.setStart_time(new Date().getTime());
//            data.setOnline_status("0");
//        }else{
//            data.setLive_status("2");
//            data.setEnd_time(new Date().getTime());
//            data.setOnline_status("1");
//        }
//        if(StringUtils.isNotBlank(playUrl)) {
//            data.setH5_play_url(playUrl);
//            data.setEnd_time(null);
//        }
//        notifyLiveStatus.setData(data);
//        String param = JsonUtil.bean2JsonStr(notifyLiveStatus);
//        System.out.println("请求参数："+param);
//        try {
//            String result = HttpClientUtil.doPostJsonOrXmlHttpClient("直播状态通知请求",url,param,false,3000,3000);
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
