package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/4/17.
 */
public class RemoveCommentRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("comment_id")
    private Integer commentId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @Override
    protected String getLoggerName() {
        return "RemoveCommentRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        RemoveCommentRequest request = JsonUtil.jsonStr2Bean(requestString, RemoveCommentRequest.class);
        this.account = request.getAccount();
        this.commentId = request.getCommentId();
        return request;
    }
}
