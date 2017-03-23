package com.bingdou.userserver.response;

import com.bingdou.core.model.User;
import com.bingdou.userserver.constant.ResponseConstant;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by gaoshan on 17/3/22.
 */
public abstract class BaseResponse {

    public void buildUserProfileResponse(List<User> users,List<UserProfileResponse> responses) {
        for (User user : users) {
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setUserPrimaryId(user.getId());
            userProfileResponse.setNickName(user.getNickName());
            userProfileResponse.setLevel(user.getVipLevel());
            userProfileResponse.setAvatar(user.getAvatar());
            userProfileResponse.setCpIdOrId(user.getCpId());
            userProfileResponse.setSignature(StringUtils.isEmpty(user.getSignature())? ResponseConstant.DEFAULT_SIGNATURE:user.getSignature());
            userProfileResponse.setGender(user.getGender());
            responses.add(userProfileResponse);
        }
    }
}
