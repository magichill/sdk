package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * 更新昵称请求
 */
public class UpdateNicknameRequest extends BaseRequest {

    /**
     * 用户账号,可以是登录名或者CP ID
     */
    @SerializedName("account")
    private String account;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("gender")
    private Integer gender;
    @SerializedName("signature")
    private String signature;

    @Override
    protected String getLoggerName() {
        return "UpdateUserProfileRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        UpdateNicknameRequest request = JsonUtil.jsonStr2Bean(requestString, UpdateNicknameRequest.class);
        this.account = request.getAccount();
        this.nickname = request.getNickname();
        this.avatar = request.getAvatar();
        this.gender = request.getGender();
        this.signature = request.getSignature();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
