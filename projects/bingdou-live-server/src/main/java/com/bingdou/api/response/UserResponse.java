package com.bingdou.api.response;

import com.bingdou.core.model.User;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.StringUtils;

/**
 * Created by gaoshan on 17/2/18.
 */
public class UserResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("nick_name")
    private String nickName;
    @SerializedName("gender")
    private int gender;
    @SerializedName("level")
    private int level;
    @SerializedName("avatar_url")
    private String avatarUrl = "http://o8ov5bkvs.bkt.clouddn.com/1159184461660189";
    @SerializedName("short_id")
    private String cpdId;
    @SerializedName("signature")
    private String signature = "这个人的签名被吃了";
    @SerializedName("certification_status")
    private int certificationStatus;
    @SerializedName("following_status")
    private int followingStatus;

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = String.valueOf(100000+id);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(int certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public int getFollowingStatus() {
        return followingStatus;
    }

    public void setFollowingStatus(int followingStatus) {
        this.followingStatus = followingStatus;
    }

    public void parseFromUser(User user){
        setId(user.getId());
        setAvatarUrl(user.getAvatar());
        setCpdId(user.getCpId());
        setGender(user.getGender());
        setNickName(user.getNickName());
        if(!StringUtils.isEmpty(user.getSignature())) {
            setSignature(user.getSignature());
        }
    }
}
