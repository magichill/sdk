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
import com.bingdou.userserver.request.GetFollowersRequest;
import com.bingdou.userserver.response.GetFollowersResponse;
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
public class GetFollowerService extends BaseService implements IMethodService {

    @Autowired
    private FocusService focusService;

    @Override
    public String getMethodName() {
        return "get_user_follower";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetFollowersRequest getFollowersRequest = (GetFollowersRequest)baseRequest;
        return dealFollowers(request, getFollowersRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetFollowersRequest getFollowersRequest = (GetFollowersRequest)baseRequest;
        return dealFollowers(request, getFollowersRequest, user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetFollowersRequest getFollowersRequest = new GetFollowersRequest();
        getFollowersRequest.parseRequest(request);
        return getFollowersRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetFollowersRequest getFollowersRequest = (GetFollowersRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getFollowersRequest.getUserId());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getFollowersRequest.getUserId());
        return user;
    }

    private ServiceResult dealFollowers(HttpServletRequest request, GetFollowersRequest getFollowersRequest, User user) throws Exception {
        if (StringUtils.isEmpty(getFollowersRequest.getUserId())){
            return ServiceResultUtil.illegal("请求参数错误");
        }
        user = getUser(getFollowersRequest);
        if(user == null){
            return ServiceResultUtil.illegal("用户不存在");
        }
        GetFollowersResponse getFollowersResponse = new GetFollowersResponse();
        List<User> followers = focusService.getFollower(user.getId());
        getFollowersResponse.parseFromUserList(followers);
        JsonElement result = JsonUtil.bean2JsonTree(getFollowersResponse);
        LogContext.instance().info("获取用户粉丝列表成功");
        return ServiceResultUtil.success(result);
    }
}
