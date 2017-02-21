package com.bingdou.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by gaoshan on 17/2/19.
 */
public class CommentResponse {

    @SerializedName("live_id")
    private long liveId;

    @SerializedName("comment_id")
    private long commentId;

    @SerializedName("comment_content")
    private String commentContent = "";

    @SerializedName("comment_date")
    private Date commentDate;

    @SerializedName("likeCount")
    private int likeCount = 0;

    @SerializedName("liked")
    private int liked = 0;

    @SerializedName("reply")
    private String reply = "";

    @SerializedName("user_info")
    private UserResponse userResponse;

    public long getLiveId() {
        return liveId;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
