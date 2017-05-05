package com.bingdou.api.service;

import com.bingdou.api.request.GetHomeFollowRequest;
import com.bingdou.api.response.HomeFollowResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoshan on 17/3/19.
 */
@Service
public class HomeFollowService extends LiveBaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetHomeFollowRequest getHomeFollowRequest = new GetHomeFollowRequest();
        getHomeFollowRequest.parseRequest(request);
        return getHomeFollowRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetHomeFollowRequest getHomeFollowRequest = (GetHomeFollowRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getHomeFollowRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getHomeFollowRequest.getAccount());
        return user;
    }

    @Override
    public String getMethodName() {
        return "get_home_follow";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetHomeFollowRequest getHomeFollowRequest = (GetHomeFollowRequest) baseRequest;
        return dealFocusLive(getHomeFollowRequest, user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetHomeFollowRequest getHomeFollowRequest = (GetHomeFollowRequest) baseRequest;
        return dealFocusLive(getHomeFollowRequest,user);
    }

    private ServiceResult dealFocusLive(GetHomeFollowRequest getHomeFollowRequest,User user) throws Exception {
        if (StringUtils.isEmpty(getHomeFollowRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        Integer start = getHomeFollowRequest.getStart();
        Integer limit = getHomeFollowRequest.getLimit();
        if (start != null && start > 0) {
            start = (start - 1) * limit;
        }
        String timestamp = "";
        if(StringUtils.isEmpty(getHomeFollowRequest.getTimestamp()) || start == 0){
            timestamp = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        }else{
            timestamp = getHomeFollowRequest.getTimestamp();
        }
        List<Live> result = getFocusLiveList(user.getId(), start, limit,timestamp);
        List<User> userList = userBaseService.getFocusUserWithoutLive(user.getId());
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(buildHomeFollowResponse(result,userList,timestamp)));
    }

    private HomeFollowResponse buildHomeFollowResponse(List<Live> liveList,List<User> userList,String timestamp){
        HomeFollowResponse response = new HomeFollowResponse();
        response.parseFromLive(liveList);
        response.parseFromUser(userList);
        response.setTimestamp(timestamp);
        return response;
    }
}
