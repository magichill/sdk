package com.bingdou.api.request;


import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gaoshan on 17/4/11.
 */
public class AddCommentRequest extends BaseRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("live_id")
    private Integer liveId;

    @SerializedName("comment_content")
    private String commentContent;

    @SerializedName("reply")
    private String reply;

    @Override
    protected String getLoggerName() {
        return "AddCommentRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        AddCommentRequest request = JsonUtil.jsonStr2Bean(requestString, AddCommentRequest.class);
        this.account = request.getAccount();
        this.commentContent = request.getCommentContent();
        this.reply = request.getReply();
        this.liveId = request.getLiveId();
        return request;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
