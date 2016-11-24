package com.bingdou.userserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * �����ǳ�����
 */
public class UpdateNicknameRequest extends BaseRequest {

    /**
     * �û��˺�,�����ǵ�¼������CP ID
     */
    @SerializedName("account")
    private String account;
    @SerializedName("nickname")
    private String nickname;

    @Override
    protected String getLoggerName() {
        return "UpdateNicknameRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        UpdateNicknameRequest request = JsonUtil.jsonStr2Bean(requestString, UpdateNicknameRequest.class);
        this.account = request.getAccount();
        this.nickname = request.getNickname();
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
}
