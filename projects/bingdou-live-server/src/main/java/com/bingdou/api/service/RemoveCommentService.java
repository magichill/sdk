package com.bingdou.api.service;

import com.bingdou.api.request.BuyLiveRequest;
import com.bingdou.api.request.RemoveCommentRequest;
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
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaoshan on 17/4/17.
 */
@Service
public class RemoveCommentService  extends LiveBaseService implements IMethodService {

    @Autowired
    private CommentService commentService;

    @Override
    public String getMethodName() {
        return "delete_comment";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RemoveCommentRequest removeCommentRequest = (RemoveCommentRequest) baseRequest;
        return dealRemoveComment(removeCommentRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RemoveCommentRequest removeCommentRequest = (RemoveCommentRequest) baseRequest;
        return dealRemoveComment(removeCommentRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        RemoveCommentRequest removeCommentRequest = new RemoveCommentRequest();
        removeCommentRequest.parseRequest(request);
        return removeCommentRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        RemoveCommentRequest removeCommentRequest = (RemoveCommentRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(removeCommentRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(removeCommentRequest.getAccount());
        return user;
    }

    private ServiceResult dealRemoveComment(RemoveCommentRequest removeCommentRequest,User user){
        LogContext.instance().info("删除评论");
        if(StringUtils.isEmpty(removeCommentRequest.getAccount())
                || removeCommentRequest.getCommentId() == null){
            return ServiceResultUtil.illegal("参数不正确");
        }
        Comment comment = commentService.getCommentById(removeCommentRequest.getCommentId());
        if(comment == null){
            return ServiceResultUtil.illegal("评论不存在或已删除");
        }
        Integer liveId = getLiveIdByMid(comment.getLiveId(),user.getId());
        boolean isBroadcaster = liveId == null? false:true;
        Map result = Maps.newHashMap();
        boolean responseResult = false;
        if(comment.getUserId() == user.getId() || isBroadcaster){
            responseResult = commentService.removeComment(comment.getId());
        }
        result.put("result",responseResult?0:1);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
