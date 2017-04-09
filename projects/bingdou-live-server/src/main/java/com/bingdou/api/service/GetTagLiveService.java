package com.bingdou.api.service;

import com.bingdou.api.request.GetTagLiveRequest;
import com.bingdou.api.response.HomePageResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/4/9.
 */
@Service
public class GetTagLiveService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "get_tag_lives";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetTagLiveRequest getTagLiveRequest = (GetTagLiveRequest) baseRequest;
        return dealTagLive(getTagLiveRequest,request);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetTagLiveRequest getTagLiveRequest = (GetTagLiveRequest) baseRequest;
        return dealTagLive(getTagLiveRequest,request);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetTagLiveRequest getTagLiveRequest = new GetTagLiveRequest();
        getTagLiveRequest.parseRequest(request);
        return getTagLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    private ServiceResult dealTagLive(GetTagLiveRequest getTagLiveRequest, HttpServletRequest request) throws Exception {
        LogContext.instance().info("请求获取标签视频列表");
        Integer start = getTagLiveRequest.getStart();
        Integer limit = getTagLiveRequest.getLimit();
        Integer tagId = getTagLiveRequest.getTagId();
        if(tagId == null){
            return ServiceResultUtil.illegal("参数不正确");
        }
        if(start!=null && start >0){
            start = (start-1)*limit;
        }
        List<Live> result = getLiveInfoByTagId(tagId,start,limit);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(buildHomePageResponse(result)));
    }

    private HomePageResponse buildHomePageResponse(List<Live> liveList){
        HomePageResponse response = new HomePageResponse();
        response.parseFromLive(liveList);
        return response;
    }
}
