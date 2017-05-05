package com.bingdou.api.service;

import com.bingdou.api.request.FindLiveRequest;
import com.bingdou.api.response.FindLiveResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.User;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 直播列表服务类
 * Created by gaoshan on 16-11-4.
 */
@Service
public class FindLiveService extends LiveBaseService implements IMethodService {

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
        return "find_live";
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
        int status = findLiveRequest.getStatus();
        String userId = findLiveRequest.getUserId();
        int liveId = findLiveRequest.getLiveId();

        List<Live> result = getLiveList(status,userId,1,1,null);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(buildLiveResponse(result)));
    }

    private FindLiveResponse buildLiveResponse(List<Live> liveList){
        FindLiveResponse response = new FindLiveResponse();
        response.setLiveList(liveList);
        return response;
    }
}
