package com.bingdou.api.service;

import com.bingdou.api.request.GetUserLivesRequest;
import com.bingdou.api.response.HomePageResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/3/22.
 */
@Service
public class GetUserLivesService extends LiveBaseService implements IMethodService {
    @Override
    public String getMethodName() {
        return "get_user_lives";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetUserLivesRequest getUserLivesRequest = (GetUserLivesRequest) baseRequest;
        return dealUserLives(getUserLivesRequest,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetUserLivesRequest getUserLivesRequest = (GetUserLivesRequest) baseRequest;
        return dealUserLives(getUserLivesRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetUserLivesRequest getUserLivesRequest = new GetUserLivesRequest();
        getUserLivesRequest.parseRequest(request);
        return getUserLivesRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetUserLivesRequest getUserLivesRequest = (GetUserLivesRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getUserLivesRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getUserLivesRequest.getAccount());
        return user;
    }

    private User getQueryUser(BaseRequest baseRequest){
        GetUserLivesRequest getUserLivesRequest = (GetUserLivesRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getUserLivesRequest.getQueryAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getUserLivesRequest.getQueryAccount());
        return user;
    }

    private ServiceResult dealUserLives(GetUserLivesRequest getUserLivesRequest,User user){
        LogContext.instance().info("查询用户全部视频数据");
        if(StringUtils.isEmpty(getUserLivesRequest.getAccount()) || StringUtils.isEmpty(getUserLivesRequest.getQueryAccount())){
            return ServiceResultUtil.illegal("参数不正确");
        }
        Integer start = getUserLivesRequest.getStart();
        Integer limit = getUserLivesRequest.getLimit();
        if(start!=null && start >0){
            start = (start-1)*limit;
        }
        User queryUser = user;
        if(!getUserLivesRequest.getAccount().equals(getUserLivesRequest.getQueryAccount())) {
            queryUser = getQueryUser(getUserLivesRequest);
            if (queryUser == null) {
                return ServiceResultUtil.illegal("查询的用户不存在");
            }
        }
        List<Live> liveList = getLiveInfoByMid(queryUser,start,limit);
        LogContext.instance().info("查询用户视频数据成功");
        HomePageResponse homePageResponse = new HomePageResponse();
        homePageResponse.parseFromLive(liveList);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(homePageResponse));
    }
}
