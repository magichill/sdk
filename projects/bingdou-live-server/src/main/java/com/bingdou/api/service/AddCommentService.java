package com.bingdou.api.service;

import com.bingdou.api.request.AddCommentRequest;
import com.bingdou.api.request.GetLiveCommentsRequest;
import com.bingdou.api.response.CommentResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Comment;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.CommentService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/4/11.
 */
@Service
public class AddCommentService  extends LiveBaseService implements IMethodService {

    @Autowired
    private CommentService commentService;

    @Override
    public String getMethodName() {
        return "comment_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AddCommentRequest addCommentRequest = (AddCommentRequest)baseRequest;
        return dealAddComment(addCommentRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AddCommentRequest addCommentRequest = (AddCommentRequest)baseRequest;
        return dealAddComment(addCommentRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        AddCommentRequest addCommentRequest = new AddCommentRequest();
        addCommentRequest.parseRequest(request);
        return addCommentRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        AddCommentRequest addCommentRequest = (AddCommentRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(addCommentRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(addCommentRequest.getAccount());
        return user;
    }

    private ServiceResult dealAddComment(AddCommentRequest addCommentRequest,User user){
        LogContext.instance().info("发表评论");
        if(StringUtils.isEmpty(addCommentRequest.getAccount()) ||
                StringUtils.isEmpty(addCommentRequest.getCommentContent()) ||
                addCommentRequest.getLiveId() == null){
            LogContext.instance().warn("评论参数不正确");
            return ServiceResultUtil.illegal("参数不正确");
        }
        Live live = getLiveInfo(addCommentRequest.getLiveId());
        if(live == null ){
            LogContext.instance().warn("直播不存在或已下线");
            return ServiceResultUtil.illegal("直播不存在或已下线");
        }
        Comment comment = new Comment();
        comment.setLiveId(addCommentRequest.getLiveId());
        comment.setText(addCommentRequest.getCommentContent());
        comment.setUserId(user.getId());
        commentService.addComment(comment);

        Comment responseComment = commentService.getNewCommentByUserId(user.getId());
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.parseFromComment(responseComment);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(commentResponse));
    }
}
