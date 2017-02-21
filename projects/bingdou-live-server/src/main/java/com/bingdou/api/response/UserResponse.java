package com.bingdou.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/2/18.
 */
public class UserResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("nick_name")
    private String nickName;
    @SerializedName("gender")
    private int gender;
    @SerializedName("level")
    private int level;
    @SerializedName("avatarUrl")
    private String avatarUrl;
    @SerializedName("short_id")
    private String cpdId;
    @SerializedName("signature")
    private String signature;
    @SerializedName("certification_status")
    private int certificationStatus;
    @SerializedName("following_status")
    private int followingStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
