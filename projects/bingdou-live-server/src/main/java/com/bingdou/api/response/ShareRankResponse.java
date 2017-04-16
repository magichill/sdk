package com.bingdou.api.response;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.ContributeUserRank;
import com.bingdou.core.model.live.ShareUserRank;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshan on 17/4/14.
 */
public class ShareRankResponse {

    @SerializedName("users")
    private List<UserProfileResponse> users;

    public List<UserProfileResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserProfileResponse> users) {
        this.users = users;
    }

    public void parseFromUserList(List<ShareUserRank> users){
        if(users == null || users.isEmpty()){
            setUsers(new ArrayList<UserProfileResponse>());
            return ;
        }

        List<UserProfileResponse> responses = Lists.newArrayList();
        buildUserProfileResponse(users,responses);
        setUsers(responses);
    }

    public void buildUserProfileResponse(List<ShareUserRank> users,List<UserProfileResponse> responses) {
        for (ShareUserRank user : users) {
            UserProfileResponse userProfileResponse = new UserProfileResponse();
            userProfileResponse.setUserPrimaryId(user.getId());
            userProfileResponse.setNickName(user.getNickName());
            userProfileResponse.setAvatar(user.getAvatar());
            userProfileResponse.setCpIdOrId(user.getCpId());
            if(StringUtils.isNotEmpty(user.getSignature())) {
                userProfileResponse.setSignature(user.getSignature());
            }
            userProfileResponse.setGender(user.getGender());
            userProfileResponse.setShareCount(user.getShareCount());
            responses.add(userProfileResponse);
        }
    }
    class UserProfileResponse{

        @SerializedName("id")
        private Integer userPrimaryId = 0;
        @SerializedName("short_id")
        private String cpIdOrId = "";
        @SerializedName("gender")
        private int gender = -1;
        @SerializedName("level")
        private int level;
        @SerializedName("phone")
        private String mobile = "";
        @SerializedName("nick_name")
        private String nickName = "";
        @SerializedName("avatar_url")
        private String avatar = "http://o8ov5bkvs.bkt.clouddn.com/1159184461660189";
        @SerializedName("signature")
        private String signature = "这个人的签名被吃了";
        @SerializedName("share_count")
        private Integer shareCount;

        public Integer getUserPrimaryId() {
            return userPrimaryId;
        }

        public void setUserPrimaryId(Integer userPrimaryId) {
            this.userPrimaryId = userPrimaryId;
        }

        public String getCpIdOrId() {
            return cpIdOrId;
        }

        public void setCpIdOrId(String cpIdOrId) {
            this.cpIdOrId = cpIdOrId;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public Integer getShareCount() {
            return shareCount;
        }

        public void setShareCount(Integer shareCount) {
            this.shareCount = shareCount;
        }
    }
}
