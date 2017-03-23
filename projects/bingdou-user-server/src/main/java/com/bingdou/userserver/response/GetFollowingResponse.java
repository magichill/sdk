package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public class GetFollowingResponse extends BaseResponse{

    @SerializedName("user_likes")
    private List<UserProfileResponse> userLikes;

    public List<UserProfileResponse> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<UserProfileResponse> userLikes) {
        this.userLikes = userLikes;
    }

    public void parseFromUserList(List<User> users){
        if(users == null || users.isEmpty()){
            setUserLikes(new ArrayList<UserProfileResponse>());
            return ;
        }

        List<UserProfileResponse> responses = Lists.newArrayList();
        buildUserProfileResponse(users,responses);
        setUserLikes(responses);
    }
}
