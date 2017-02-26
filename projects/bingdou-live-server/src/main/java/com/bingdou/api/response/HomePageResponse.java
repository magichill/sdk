package com.bingdou.api.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public class HomePageResponse {

    @SerializedName("lives")
    private List<ComposedLiveResponse> lives;

    public List<ComposedLiveResponse> getLives() {
        return lives;
    }

    public void setLives(List<ComposedLiveResponse> lives) {
        this.lives = lives;
    }

    public void parseFromLive(List<Live> liveList){
        if(liveList == null || liveList.size() == 0){
            return ;
        }
        List<ComposedLiveResponse> liveResponses = Lists.newArrayList();
        for(Live live :liveList){
            ComposedLiveResponse composedLiveResponse = new ComposedLiveResponse();
            composedLiveResponse.setId(live.getId());
            composedLiveResponse.setPlayUrl(live.getPullStream());
            composedLiveResponse.setCoverUrl(live.getLivePicture());
            composedLiveResponse.setTitle(live.getLiveTitle());
            composedLiveResponse.setPublishUrl(live.getPushStream());
            composedLiveResponse.setPlaybackUrl(live.getReplayUrl());

            if(live.getUser()!= null) {
                User user = live.getUser();
                UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setAvatarUrl(user.getAvatar());
                userResponse.setCpdId(user.getCpId());
                userResponse.setGender(user.getGender());
                userResponse.setNickName(user.getNickName());
                userResponse.setSignature(user.getSignature());
                composedLiveResponse.setUserResponse(userResponse);
            }
            liveResponses.add(composedLiveResponse);
        }
        setLives(liveResponses);
    }
}