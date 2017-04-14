package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.ICommentMapper;
import com.bingdou.core.model.live.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
@Repository
public class CommentDao {

    @Autowired
    private ICommentMapper commentMapper;

    public void addComment(Comment comment){
        commentMapper.addComment(comment);
    }

    public Comment getCommentById(Integer commentId){
        return commentMapper.getCommentById(commentId);
    }

    public List<Comment> getCommentListByLiveId(Integer liveId, int start, int limit){
        return commentMapper.getCommentListByLiveId(liveId,start,limit);
    }

    public List<Comment> getPopularList(Integer liveId){
        return commentMapper.getPopularList(liveId);
    }
    public void likeOrNotComment(Integer commentId,int voteUp){
        commentMapper.likeOrNotComment(commentId,voteUp);
    }

    public void removeComment(Integer commentId){
        commentMapper.removeComment(commentId);
    }

    public Comment getNewCommentByUserId(Integer userId){
        return commentMapper.getNewCommentByUserId(userId);
    }


}
