package com.bingdou.api.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public class HomePageResponse {

    @SerializedName("lives")
    private List<ComposedLiveResponse> lives;

    @SerializedName("timestamp")
    private String timestamp;

    public List<ComposedLiveResponse> getLives() {
        return lives;
    }

    public void setLives(List<ComposedLiveResponse> lives) {
        this.lives = lives;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void parseFromLive(List<Live> liveList){
        if(liveList == null || liveList.size() == 0){
            return ;
        }
        List<ComposedLiveResponse> liveResponses = Lists.newArrayList();
        for(Live live :liveList){
            ComposedLiveResponse composedLiveResponse = new ComposedLiveResponse();
            composedLiveResponse.parseFromLive(live);

            if(live.getUser()!= null) {
                User user = live.getUser();
                UserResponse userResponse = new UserResponse();
                userResponse.parseFromUser(user);
                composedLiveResponse.setUserResponse(userResponse);
            }
            liveResponses.add(composedLiveResponse);
        }
        setLives(liveResponses);
    }
}
