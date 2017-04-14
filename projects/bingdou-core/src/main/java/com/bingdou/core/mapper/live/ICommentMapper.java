package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 17/2/18.
 */
public interface ICommentMapper {

    Comment getCommentById(@Param("commentId") Integer commentId);
    Comment getNewCommentByUserId(@Param("userId") Integer userId);

    void addComment(Comment coment);

    void removeComment(@Param("commentId") Integer commentId);

    void likeOrNotComment(@Param("commentId") Integer commentId,@Param("voteUp") int voteUp);

    List<Comment> getCommentListByLiveId(@Param("liveId") Integer liveId,@Param("start") int start,
                                         @Param("limit") int limit);

    List<Comment> getPopularList(@Param("liveId") Integer liveId);
}
