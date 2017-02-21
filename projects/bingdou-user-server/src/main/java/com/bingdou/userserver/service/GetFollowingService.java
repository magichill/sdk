package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.FocusService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.userserver.request.GetFollowingRequest;
import com.bingdou.userserver.response.GetFollowingResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/2/20.
 */
@Service
public class GetFollowingService extends BaseService implements IMethodService {

    @Autowired
    private FocusService focusService;

    @Override
    public String getMethodName() {
        return "get_user_following";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetFollowingRequest getFollowingRequest =  (GetFollowingRequest)baseRequest;
        return dealFollowing(request, getFollowingRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetFollowingRequest getFollowingRequest =  (GetFollowingRequest)baseRequest;
        return dealFollowing(request, getFollowingRequest, user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetFollowingRequest getFollowingRequest = new GetFollowingRequest();
        getFollowingRequest.parseRequest(request);
        return getFollowingRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetFollowingRequest getFollowingRequest = (GetFollowingRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getFollowingRequest.getUserId());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getFollowingRequest.getUserId());
        return user;
    }

    private ServiceResult dealFollowing(HttpServletRequest request, GetFollowingRequest getFollowingRequest, User user) throws Exception {
        if (StringUtils.isEmpty(getFollowingRequest.getUserId())){
            return ServiceResultUtil.illegal("请求参数错误");
        }

        GetFollowingResponse getFollowingResponse = new GetFollowingResponse();
        List<User> followers = focusService.getFollowing(user.getId());
        getFollowingResponse.parseFromUserList(followers);
        JsonElement result = JsonUtil.bean2JsonTree(getFollowingResponse);
        LogContext.instance().info("获取用户关注列表成功");
        return ServiceResultUtil.success(result);
    }
}
