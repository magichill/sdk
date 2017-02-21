package com.bingdou.core.service.live;

import com.bingdou.core.model.live.Comment;
import com.bingdou.core.repository.live.CommentDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoshan on 17/2/19.
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public boolean addComment(Comment comment){
        LogContext.instance().info("插入新的评论数据");
        if(comment == null){
            return false;
        }
        commentDao.addComment(comment);
        LogContext.instance().info("插入新的评论数据成功");
        return true;
    }

    public Comment getCommentById(Long commentId) {
        LogContext.instance().info("获取评论数据");
        if (commentId == null)
            return null;
        return commentDao.getCommentById(commentId);
    }

    public boolean likeOrNotComment(Long commentId,boolean status){
        if(commentId ==null){
            return false;
        }
        if(status){
            LogContext.instance().info("评论点赞");
        }else{
            LogContext.instance().info("取消评论点赞");
        }
        int voteUp = 0;
        commentDao.likeOrNotComment(commentId,voteUp);
        return true;
    }

    public List<Comment> getCommentListByLiveId(Long liveId, int start, int limit){
        LogContext.instance().info("获取直播的评论数据");
        if (liveId == null )
            return new ArrayList<Comment>();
        return commentDao.getCommentListByLiveId(liveId,start,limit);
    }

}
