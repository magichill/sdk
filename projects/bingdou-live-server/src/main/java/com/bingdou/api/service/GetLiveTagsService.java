package com.bingdou.api.service;

import com.bingdou.api.request.GetLiveTagRequest;
import com.bingdou.api.response.GetLiveTagResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.VideoTag;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.VideoTagService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/4/4.
 */
@Service
public class GetLiveTagsService extends LiveBaseService implements IMethodService {

    @Autowired
    private VideoTagService videoTagService;

    @Override
    public String getMethodName() {
        return "get_live_tags";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveTagRequest getLiveTagRequest = (GetLiveTagRequest) baseRequest;
        return dealGetVideoTag(getLiveTagRequest,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveTagRequest getLiveTagRequest = (GetLiveTagRequest) baseRequest;
        return dealGetVideoTag(getLiveTagRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetLiveTagRequest getLiveTagRequest = new GetLiveTagRequest();
        getLiveTagRequest.parseRequest(request);
        return getLiveTagRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetLiveTagRequest getLiveTagRequest = (GetLiveTagRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getLiveTagRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getLiveTagRequest.getAccount());
        return user;
    }

    public ServiceResult dealGetVideoTag(GetLiveTagRequest getLiveTagRequest,User user){
        LogContext.instance().info("获取直播标签");
        if(StringUtils.isEmpty(getLiveTagRequest.getAccount())){
            LogContext.instance().info("参数不正确");
            return ServiceResultUtil.illegal("参数不正确");
        }
        List<VideoTag> videoTagList = videoTagService.getVideoTags();
        GetLiveTagResponse getLiveTagResponse = new GetLiveTagResponse();
        getLiveTagResponse.parseLiveTags(videoTagList);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(getLiveTagResponse));
    }
}
