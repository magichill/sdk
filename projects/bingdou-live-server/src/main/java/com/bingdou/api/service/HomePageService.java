package com.bingdou.api.service;

import com.bingdou.api.request.FindLiveRequest;
import com.bingdou.api.response.FindLiveResponse;
import com.bingdou.api.response.HomePageResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/2/16.
 */
@Service
public class HomePageService extends LiveBaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        FindLiveRequest findLiveRequest = new FindLiveRequest();
        findLiveRequest.parseRequest(request);
        return findLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "get_home_page";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        FindLiveRequest findLiveRequest = (FindLiveRequest) baseRequest;
        return dealFindLive(findLiveRequest,request);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        FindLiveRequest findLiveRequest = (FindLiveRequest) baseRequest;
        return dealFindLive(findLiveRequest,request);
    }

    private ServiceResult dealFindLive(FindLiveRequest findLiveRequest, HttpServletRequest request) throws Exception {
        Integer start = findLiveRequest.getStart();
        Integer limit = findLiveRequest.getLimit();
        Integer status = findLiveRequest.getStatus();
        String userId = findLiveRequest.getUserId();
        if(start!=null && start >0){
            start = (start-1)*limit;
        }
        List<Live> result = getLiveList(status,null,start,limit);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(buildHomePageResponse(result)));
    }

    private FindLiveResponse buildLiveResponse(List<Live> liveList){
        FindLiveResponse response = new FindLiveResponse();
        response.setLiveList(liveList);
        return response;
    }

    private HomePageResponse buildHomePageResponse(List<Live> liveList){
        HomePageResponse response = new HomePageResponse();
        response.parseFromLive(liveList);
        return response;
    }
}
