package com.bingdou.userserver.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/2/16.
 */
public class UserProfileResponse {

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
    private String avatar = "http://tva2.sinaimg.cn/crop.3.0.634.634.1024/cd516b22jw8fa4mlfynwzj20hs0hm0tr.jpg";
    @SerializedName("signature")
    private String signature = "这个人的签名被吃了";
    @SerializedName("certification_status")
    private Integer certificationStatus = 0;
    @SerializedName("city")
    private String city = "";
    @SerializedName("registered_at")
    private long registerTime = 0l;
    @SerializedName("open_id")
    private String openId = "";
    @SerializedName("longtitude")
    private float longtitude = 0f;
    @SerializedName("latitude")
    private float latitude = 0f;

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

    public Integer getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(Integer certificationStatus) {
        this.certificationStatus = certificationStatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
