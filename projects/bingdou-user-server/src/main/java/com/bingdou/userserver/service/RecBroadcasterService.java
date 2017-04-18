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
import com.bingdou.userserver.constant.BroadcasterType;
import com.bingdou.userserver.request.RecBroadcasterRequest;
import com.bingdou.userserver.response.GetFollowersResponse;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/4/16.
 */
@Service
public class RecBroadcasterService extends BaseService implements IMethodService {

    @Autowired
    private FocusService focusService;

    @Override
    public String getMethodName() {
        return "get_broadcaster_list";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RecBroadcasterRequest recBroadcasterRequest = (RecBroadcasterRequest) baseRequest;
        return dealRecommend(recBroadcasterRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RecBroadcasterRequest recBroadcasterRequest = (RecBroadcasterRequest) baseRequest;
        return dealRecommend(recBroadcasterRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        RecBroadcasterRequest recBroadcasterRequest = new RecBroadcasterRequest();
        recBroadcasterRequest.parseRequest(request);
        return recBroadcasterRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        RecBroadcasterRequest recBroadcasterRequest = (RecBroadcasterRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(recBroadcasterRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(recBroadcasterRequest.getAccount());
        return user;
    }

    private ServiceResult dealRecommend(RecBroadcasterRequest recBroadcasterRequest,User user){
        LogContext.instance().info("获取推荐主播列表");
        List<User> users = Lists.newArrayList();
        if(recBroadcasterRequest.getBroadcasterType()==null){
            Integer start = recBroadcasterRequest.getPage();
            Integer limit = recBroadcasterRequest.getCount();
            if (start != null && start > 0) {
                start = (start - 1) * limit;
            }
            users = userBaseService.getUserList(start,limit);
        }else{
            BroadcasterType broadcasterType = BroadcasterType.getByIndex(recBroadcasterRequest.getBroadcasterType());

            if(broadcasterType == null){
                return ServiceResultUtil.illegal("推荐列表类型错误");
            }
            if(broadcasterType == BroadcasterType.POPULAR){
                users = focusService.getPopularUsers();
            }else if(broadcasterType == BroadcasterType.RECOMMEND){
                users = userBaseService.getRecommendUsers();
            }else {
                users = userBaseService.getActiveUsers();
            }
        }
        GetFollowersResponse getFollowersResponse = new GetFollowersResponse();
        getFollowersResponse.parseFromUserList(users);
        JsonElement result = JsonUtil.bean2JsonTree(getFollowersResponse);
        LogContext.instance().info("获取推荐用户列表成功");
        return ServiceResultUtil.success(result);
    }
}
