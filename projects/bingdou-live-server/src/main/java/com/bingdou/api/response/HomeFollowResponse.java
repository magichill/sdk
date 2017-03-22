package com.bingdou.api.response;

import com.bingdou.core.model.User;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by gaoshan on 17/3/21.
 */
public class HomeFollowResponse extends HomePageResponse {

    @SerializedName("users")
    private List<UserResponse> users;


    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }

    public void parseFromUser(List<User> userList){
        if(userList == null || userList.isEmpty()){
            return ;
        }
        List<UserResponse> userResponseList = Lists.newArrayList();
        for(User user:userList){
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setAvatarUrl(user.getAvatar());
            userResponse.setCpdId(user.getCpId());
            userResponse.setGender(user.getGender());
            userResponse.setNickName(user.getNickName());
            if(!StringUtils.isEmpty(user.getSignature())) {
                userResponse.setSignature(user.getSignature());
            }
            userResponseList.add(userResponse);
        }
        setUsers(userResponseList);
    }


}
