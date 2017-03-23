package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public class GetContributorsResponse extends  BaseResponse{

    @SerializedName("users")
    private List<UserProfileResponse> users;

    public List<UserProfileResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserProfileResponse> users) {
        this.users = users;
    }

    public void parseFromUserList(List<User> users){
        if(users == null || users.isEmpty()){
            setUsers(new ArrayList<UserProfileResponse>());
            return ;
        }

        List<UserProfileResponse> responses = Lists.newArrayList();
        buildUserProfileResponse(users,responses);
        setUsers(responses);
    }
}
