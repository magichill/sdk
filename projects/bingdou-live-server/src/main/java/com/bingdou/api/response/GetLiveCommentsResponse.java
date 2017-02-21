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

    public void parseFromCommentList(List<Comment> commentList){
        if(commentList == null || commentList.size()==0){
            return;
        }
        List<CommentResponse> populars = Lists.newArrayList();
        List<CommentResponse> commons = Lists.newArrayList();
        int size = commentList.size();
        for(int i=0;i<=size-1;i++){
            Comment comment = commentList.get(i);
            CommentResponse commentResponse = buildCommentResponse(comment);
            if(i<=2){
                populars.add(commentResponse);
            }else{
                commons.add(commentResponse);
            }
        }

    }

    private CommentResponse buildCommentResponse(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentId(comment.getId());
        commentResponse.setCommentContent(comment.getText());
        commentResponse.setLiveId(comment.getLiveId());
        commentResponse.setCommentDate(comment.getCreatedTime());
        commentResponse.setLikeCount(comment.getVoteUp());
        return commentResponse;
    }
}
