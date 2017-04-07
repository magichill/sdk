package com.bingdou.api.response;

import com.bingdou.core.model.live.VideoTag;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 17/4/4.
 */
public class GetLiveTagResponse {

    @SerializedName("tags")
    public List<LiveTagResponse> tagResponseLive;

    public List<LiveTagResponse> getTagResponseLive() {
        return tagResponseLive;
    }

    public void setTagResponseLive(List<LiveTagResponse> tagResponseLive) {
        this.tagResponseLive = tagResponseLive;
    }

    public void parseLiveTags(List<VideoTag> tags){
        if(tags == null || tags.isEmpty()){
            return ;
        }
        List<LiveTagResponse> liveTagResponses = Lists.newArrayList();
        for(VideoTag tag : tags){
            LiveTagResponse tagResponse = new LiveTagResponse();
            tagResponse.parseFromTag(tag);
            liveTagResponses.add(tagResponse);
        }
        setTagResponseLive(liveTagResponses);
    }


}
