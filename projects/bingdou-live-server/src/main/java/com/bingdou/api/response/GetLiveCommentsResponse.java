package com.bingdou.api.response;

import com.bingdou.core.model.live.Comment;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gaoshan on 17/2/19.
 */
public class GetLiveCommentsResponse {

    @SerializedName("live_popular_comment")
    private List<CommentResponse> popularComment;

    @SerializedName("live_comment")
    private List<CommentResponse> liveComment;

    public List<CommentResponse> getPopularComment() {
        return popularComment;
    }

    public void setPopularComment(List<CommentResponse> popularComment) {
        this.popularComment = popularComment;
    }

    public List<CommentResponse> getLiveComment() {
        return liveComment;
    }

    public void setLiveComment(List<CommentResponse> liveComment) {
        this.liveComment = liveComment;
    }

    public void parseFromCommentList(List<Comment> popularList,List<Comment> commentList){
        List<CommentResponse> populars = Lists.newArrayList();
        List<CommentResponse> commons = Lists.newArrayList();
        parseList(populars,popularList);
        parseList(commons,commentList);
        setPopularComment(populars);
        setLiveComment(commons);
    }

    private void parseList(List<CommentResponse> targetList,List<Comment> commentList){
        if(commentList == null || commentList.size()==0){
            return;
        }
        for(Comment comment : commentList){
            CommentResponse commentResponse = buildCommentResponse(comment);
            UserResponse userResponse = new UserResponse();
            userResponse.parseFromUser(comment.getUser());
            commentResponse.setUserResponse(userResponse);
            targetList.add(commentResponse);
        }
    }



    private CommentResponse buildCommentResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.parseFromComment(comment);
        return commentResponse;
    }
}
