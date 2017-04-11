package com.bingdou.api.service;

import com.bingdou.api.constant.ShareChannel;
import com.bingdou.api.constant.ShareType;
import com.bingdou.api.request.ReportShareRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.ReportShare;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ShareRecordService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaoshan on 17/4/10.
 */
@Service
public class ReportShareService extends LiveBaseService implements IMethodService {

    @Autowired
    private ShareRecordService shareRecordService;
    @Override
    public String getMethodName() {
        return "report_share";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ReportShareRequest reportShareRequest = (ReportShareRequest) baseRequest;
        return dealReportShare(reportShareRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ReportShareRequest reportShareRequest = (ReportShareRequest) baseRequest;
        return dealReportShare(reportShareRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ReportShareRequest reportShareRequest = new ReportShareRequest();
        reportShareRequest.parseRequest(request);
        return reportShareRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        ReportShareRequest reportShareRequest = (ReportShareRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(reportShareRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(reportShareRequest.getAccount());
        return user;
    }

    private User getBroadcaster(BaseRequest baseRequest) {
        ReportShareRequest reportShareRequest = (ReportShareRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(reportShareRequest.getBroadcasterId());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(reportShareRequest.getBroadcasterId());
        return user;
    }

    private ServiceResult dealReportShare(ReportShareRequest reportShareRequest ,User user){
        LogContext.instance().info("请求上报分享数据");
        if(StringUtils.isEmpty(reportShareRequest.getAccount()) ||
                reportShareRequest.getShareType() == null ||
                reportShareRequest.getBroadcasterId() == null)
            return ServiceResultUtil.illegal("参数不正确");

        User broadcaster = getBroadcaster(reportShareRequest);
        if(broadcaster == null){
            return ServiceResultUtil.illegal("主播不存在");
        }
        ReportShare reportShare = shareRecordService.getReportShare(broadcaster.getId(),reportShareRequest.getShareType(),user.getId());
        Integer shareCount = 1;
        if(reportShare == null){
            Live live = null;
            if(reportShareRequest.getLiveId() != null && reportShareRequest.getShareType()== ShareType.VIDEO.getIndex()) {
                live = getLiveInfo(reportShareRequest.getLiveId());
            }
            shareRecordService.addReportShare(user, live,
                    ShareType.getByIndex(reportShareRequest.getShareType()).getIndex(),
                    ShareChannel.getByIndex(reportShareRequest.getShareChannel()).getIndex(),
                    broadcaster.getId());
        }else{
            shareCount = reportShare.getShareCount()+1;
            shareRecordService.updateReportShare(reportShare);
        }
        Map result = Maps.newHashMap();
        result.put("share_count",shareCount);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
