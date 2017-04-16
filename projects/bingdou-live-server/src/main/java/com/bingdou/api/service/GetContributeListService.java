package com.bingdou.api.service;

import com.bingdou.api.request.GetContributionRankRequest;
import com.bingdou.api.response.ContributeRankResponse;
import com.bingdou.api.response.ShareRankResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.ContributeUserRank;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.GiftService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/4/14.
 */
@Service
public class GetContributeListService extends LiveBaseService implements IMethodService {

    @Autowired
    private GiftService giftService;

    @Override
    public String getMethodName() {
        return "get_contribution_rank";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetContributionRankRequest getContributionRankRequest = (GetContributionRankRequest) baseRequest;
        return dealContributeList(getContributionRankRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetContributionRankRequest getContributionRankRequest = (GetContributionRankRequest) baseRequest;
        return dealContributeList(getContributionRankRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetContributionRankRequest getContributionRankRequest = new GetContributionRankRequest();
        getContributionRankRequest.parseRequest(request);
        return getContributionRankRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetContributionRankRequest getContributionRankRequest = (GetContributionRankRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getContributionRankRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getContributionRankRequest.getAccount());
        return user;
    }

    private User getQueryUser(BaseRequest baseRequest){
        GetContributionRankRequest getContributionRankRequest = (GetContributionRankRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getContributionRankRequest.getQueryAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getContributionRankRequest.getQueryAccount());
        return user;
    }

    private ServiceResult dealContributeList(GetContributionRankRequest getContributionRankRequest,User user){
        LogContext.instance().info("获取用户赠送礼物排行");
        if(StringUtils.isEmpty(getContributionRankRequest.getQueryAccount())){
            return ServiceResultUtil.illegal("参数不合法");
        }
        User queryUser = getQueryUser(getContributionRankRequest);
        if(queryUser == null){
            return ServiceResultUtil.illegal("用户不存在");
        }
        Integer start = getContributionRankRequest.getPage();
        Integer limit = getContributionRankRequest.getCount();
        if (start != null && start > 0) {
            start = (start - 1) * limit;
        }
        List<ContributeUserRank> contributeUsers = giftService.getContributeList(queryUser,start,limit);
        ContributeRankResponse contributeRankResponse = new ContributeRankResponse();
        contributeRankResponse.parseFromUserList(contributeUsers);
        JsonElement result = JsonUtil.bean2JsonTree(contributeRankResponse);
        LogContext.instance().info("获取用户赠送礼物排行成功");
        return ServiceResultUtil.success(result);
    }
}
