package com.bingdou.api.service;

import com.bingdou.api.request.GetShareRankRequest;
import com.bingdou.api.response.ShareRankResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.ShareUserRank;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ShareRecordService;
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
 * Created by gaoshan on 17/4/11.
 */
@Service
public class GetShareRankService extends LiveBaseService implements IMethodService {

    @Autowired
    private ShareRecordService shareRecordService;

    @Override
    public String getMethodName() {
        return "get_share_rank";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetShareRankRequest getShareRankRequest = (GetShareRankRequest) baseRequest;
        return dealShareRank(getShareRankRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetShareRankRequest getShareRankRequest = (GetShareRankRequest) baseRequest;
        return dealShareRank(getShareRankRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetShareRankRequest getShareRankRequest = new GetShareRankRequest();
        getShareRankRequest.parseRequest(request);
        return getShareRankRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetShareRankRequest getShareRankRequest = (GetShareRankRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getShareRankRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getShareRankRequest.getAccount());
        return user;
    }

    private User getQueryUser(BaseRequest baseRequest) {
        GetShareRankRequest getShareRankRequest = (GetShareRankRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getShareRankRequest.getQueryAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getShareRankRequest.getQueryAccount());
        return user;
    }

    private ServiceResult dealShareRank(GetShareRankRequest getShareRankRequest,User user){
        LogContext.instance().info("获取用户分享榜列表");
        if(StringUtils.isEmpty(getShareRankRequest.getAccount())
                || StringUtils.isEmpty(getShareRankRequest.getQueryAccount())){
            return ServiceResultUtil.illegal("参数不合法");
        }

//        Live live = getLiveInfo(getShareRankRequest.getLiveId());
//        if(live == null){
//            return ServiceResultUtil.illegal("视频不存在或已下线");
//        }
        User queryUser = getQueryUser(getShareRankRequest);
        Integer start = getShareRankRequest.getPage();
        Integer limit = getShareRankRequest.getCount();
        if (start != null && start > 0) {
            start = (start - 1) * limit;
        }
        List<ShareUserRank> shareUsers = shareRecordService.getShareRankList(queryUser.getId(),start,limit);
        ShareRankResponse shareRankResponse = new ShareRankResponse();
        shareRankResponse.parseFromUserList(shareUsers);
        JsonElement result = JsonUtil.bean2JsonTree(shareRankResponse);
        LogContext.instance().info("获取用户分享列表成功");
        return ServiceResultUtil.success(result);
    }
}
