package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public interface ICommentMapper {

    Comment getCommentById(@Param("commentId") long commentId);

    void addComment(Comment coment);

    void removeComment(@Param("commentId") long commentId);

    void likeOrNotComment(@Param("commentId") long commentId,@Param("voteUp") int voteUp);

    List<Comment> getCommentListByLiveId(@Param("liveId") long liveId,@Param("start") int start,
                                         @Param("limit") int limit);
}
