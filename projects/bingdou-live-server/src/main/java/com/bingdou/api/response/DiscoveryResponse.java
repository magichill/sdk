package com.bingdou.api.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 17/3/15.
 */
public class DiscoveryResponse extends HomePageResponse {

    @SerializedName("broadcasters")
    private List<UserResponse> broadcasters;


    public void parseFromLiveAndUser(List<Live> liveList, List<User> userList){
        parseFromLive(liveList);
        parseFromUser(userList);
    }

    private void parseFromUser(List<User> userList){
        if(userList == null || userList.size() == 0){
            return ;
        }
        List<UserResponse> userResponseList = Lists.newArrayList();
        for(User user : userList) {
            if (user != null) {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setAvatarUrl(user.getAvatar());
                userResponse.setCpdId(user.getCpId());
                userResponse.setGender(user.getGender());
                userResponse.setNickName(user.getNickName());
                userResponse.setSignature(user.getSignature());
                userResponseList.add(userResponse);
            }
        }
        this.setBroadcasters(userResponseList);
    }
    public List<UserResponse> getBroadcasters() {
        return broadcasters;
    }

    public void setBroadcasters(List<UserResponse> broadcasters) {
        this.broadcasters = broadcasters;
    }
}
