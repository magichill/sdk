package com.bingdou.api.service;

import com.bingdou.api.request.BoughtVideoRequest;
import com.bingdou.api.request.GetVideoIncomeRequest;
import com.bingdou.api.response.HomePageResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 27/05/17.
 */
@Service
public class BuyVideoListService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "get_live_bought";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BoughtVideoRequest boughtVideoRequest = (BoughtVideoRequest) baseRequest;
        return dealVideoIncome(boughtVideoRequest,request,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BoughtVideoRequest boughtVideoRequest = (BoughtVideoRequest) baseRequest;
        return dealVideoIncome(boughtVideoRequest,request,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        BoughtVideoRequest boughtVideoRequest = new BoughtVideoRequest();
        boughtVideoRequest.parseRequest(request);
        return boughtVideoRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        BoughtVideoRequest boughtVideoRequest = (BoughtVideoRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(boughtVideoRequest.getAccount()));
    }

    private ServiceResult dealVideoIncome(BoughtVideoRequest boughtVideoRequest, HttpServletRequest request, User user){
        LogContext.instance().info("获取已购视频列表");
        if(StringUtils.isEmpty(boughtVideoRequest.getAccount())){
            LogContext.instance().info("参数不正确");
            return ServiceResultUtil.illegal("参数不正确");
        }
        int start = boughtVideoRequest.getStart();
        int limit = boughtVideoRequest.getLimit();
        List<Live> boughtVideoList = getBoughtLiveList(String.valueOf(user.getId()),start,limit);

        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(buildVideoIncome(boughtVideoList)));
    }

    private HomePageResponse buildVideoIncome(List<Live> liveList){
        HomePageResponse response = new HomePageResponse();
        response.parseFromLive(liveList);
        return response;
    }
}
