package com.bingdou.api.service;

import com.bingdou.api.request.GetLiveCommentsRequest;
import com.bingdou.api.response.GetLiveCommentsResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Comment;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.CommentService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/2/19.
 */
@Service
public class GetLiveCommentsService extends BaseService implements IMethodService {

    @Autowired
    private CommentService commentService;

    @Override
    public String getMethodName() {
        return "get_live_comments";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveCommentsRequest getLiveCommentsRequest = (GetLiveCommentsRequest) baseRequest;
        return dealGetLiveComments(request, getLiveCommentsRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveCommentsRequest getLiveCommentsRequest = (GetLiveCommentsRequest) baseRequest;
        return dealGetLiveComments(request, getLiveCommentsRequest, user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetLiveCommentsRequest getLiveCommentsRequest = new GetLiveCommentsRequest();
        getLiveCommentsRequest.parseRequest(request);
        return getLiveCommentsRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetLiveCommentsRequest getLiveCommentsRequest = (GetLiveCommentsRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getLiveCommentsRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getLiveCommentsRequest.getAccount());
        return user;
    }

    private ServiceResult dealGetLiveComments(HttpServletRequest request, GetLiveCommentsRequest getLiveCommentsRequest, User user) throws Exception {
        Integer liveId = getLiveCommentsRequest.getLiveId();
        if (StringUtils.isEmpty(getLiveCommentsRequest.getAccount()) || liveId == null) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        GetLiveCommentsResponse getLiveCommentsResponse = new GetLiveCommentsResponse();

        Integer start = getLiveCommentsRequest.getPage();
        Integer limit = getLiveCommentsRequest.getCount();
        if(start!=null && start >0){
            start = (start-1)*limit;
        }
        List<Comment> commentList = commentService.getCommentListByLiveId(liveId,start,limit);
        List<Comment> popularList = commentService.getPopularCommentList(liveId);
        getLiveCommentsResponse.parseFromCommentList(popularList,commentList);
        JsonElement result = JsonUtil.bean2JsonTree(getLiveCommentsResponse);
        LogContext.instance().info("获取视频评论信息成功");
        return ServiceResultUtil.success(result);
    }
}
