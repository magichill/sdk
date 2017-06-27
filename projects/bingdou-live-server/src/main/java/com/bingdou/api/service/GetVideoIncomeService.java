package com.bingdou.api.service;

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
public class GetVideoIncomeService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "get_live_income";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetVideoIncomeRequest getVideoIncomeRequest = (GetVideoIncomeRequest) baseRequest;
        return dealVideoIncome(getVideoIncomeRequest,request,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetVideoIncomeRequest getVideoIncomeRequest = (GetVideoIncomeRequest) baseRequest;
        return dealVideoIncome(getVideoIncomeRequest,request,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetVideoIncomeRequest getVideoIncomeRequest = new GetVideoIncomeRequest();
        getVideoIncomeRequest.parseRequest(request);
        return getVideoIncomeRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetVideoIncomeRequest getVideoIncomeRequest = (GetVideoIncomeRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(getVideoIncomeRequest.getAccount()));
    }

    private ServiceResult dealVideoIncome(GetVideoIncomeRequest getVideoIncomeRequest, HttpServletRequest request,User user){
        LogContext.instance().info("获取视频收入");
        if(StringUtils.isEmpty(getVideoIncomeRequest.getAccount())){
            LogContext.instance().info("参数不正确");
            return ServiceResultUtil.illegal("参数不正确");
        }
        int start = getVideoIncomeRequest.getStart();
        int limit = getVideoIncomeRequest.getLimit();
        List<Live> videoIncomeList = getLiveIncomeList(String.valueOf(user.getId()),start,limit);

        return ServiceResultUtil.success(ServiceResultUtil.success(JsonUtil.bean2JsonTree(buildHomePageResponse(videoIncomeList))));
    }

    private HomePageResponse buildHomePageResponse(List<Live> liveList){
        HomePageResponse response = new HomePageResponse();
        response.parseFromLive(liveList);
        return response;
    }
}
