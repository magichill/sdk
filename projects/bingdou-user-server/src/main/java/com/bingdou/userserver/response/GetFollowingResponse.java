package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public class GetFollowingResponse {

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
        for(User user : users){
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setUserPrimaryId(user.getId());
            userProfileResponse.setNickName(user.getNickName());
            userProfileResponse.setLevel(user.getVipLevel());
            userProfileResponse.setAvatar(user.getAvatar());
            userProfileResponse.setCpIdOrId(user.getCpId());
            userProfileResponse.setSignature(user.getSignature());
            userProfileResponse.setGender(user.getGender());
            responses.add(userProfileResponse);
        }
        setUserLikes(responses);
    }
}
