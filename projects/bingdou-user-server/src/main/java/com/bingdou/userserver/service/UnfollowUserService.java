package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.FocusService;
import com.bingdou.tools.LogContext;
import com.bingdou.userserver.request.FocusRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/2/13.
 */
public class UnfollowUserService extends BaseService implements IMethodService {

    @Autowired
    private FocusService focusService;


    @Override
    public String getMethodName() {
        return "unfollow_user";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        FocusRequest focusRequest = (FocusRequest) baseRequest;
        return dealUnfollowUser(request, focusRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        FocusRequest focusRequest = (FocusRequest) baseRequest;
        return dealUnfollowUser(request, focusRequest, user);
    }

    private ServiceResult dealUnfollowUser(HttpServletRequest request, FocusRequest focusRequest, User user) throws Exception {
        if (StringUtils.isEmpty(focusRequest.getUserId()) || StringUtils.isEmpty(focusRequest.getFollowId()) ) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        User followerUser = getFollower(focusRequest.getFollowId());
        if(user != null && followerUser != null){
            boolean focusInfoExist = focusService.isFocusInfoExist(user,followerUser);
            if(focusInfoExist){
                LogContext.instance().info("关注用户信息存在，更新为未关注状态！");
                focusService.updateFocusInfo(user, followerUser,0);
            }else {
                LogContext.instance().info("关注用户信息不存在，无法取消关注！");
                return ServiceResultUtil.illegal("未关注当前用户");
            }
            LogContext.instance().info("取消关注用户成功！");
            return ServiceResultUtil.success();
        }else{
            LogContext.instance().info("取消关注用户失败！");
            return ServiceResultUtil.illegal("用户不存在！");
        }
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        FocusRequest focusRequest = new FocusRequest();
        focusRequest.parseRequest(request);
        return focusRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        FocusRequest focusRequest = (FocusRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(focusRequest.getUserId());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(focusRequest.getUserId());
        return user;
    }

    private User getFollower(String followerId){
        User user = userBaseService.getUserDetailByAccount(followerId);
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(followerId);
        return user;
    }
}
