package com.bingdou.api.response;

import com.bingdou.core.model.live.Live;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 16-11-4.
 */
public class FindLiveResponse {

    @SerializedName("live_list")
    private List<Live> liveList;

    public List<Live> getLiveList() {
        return liveList;
    }

    public void setLiveList(List<Live> liveList) {
        this.liveList = liveList;
    }


}
