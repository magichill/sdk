package com.bingdou.api.service;

import com.bingdou.api.request.ReportChatRoomRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.ReportChatroom;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ChatroomRecordService;
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
public class ReportChatroomService extends LiveBaseService implements IMethodService {

    @Autowired
    private ChatroomRecordService chatroomRecordService;
    @Override
    public String getMethodName() {
        return "request_live_room";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ReportChatRoomRequest reportChatRoomRequest = (ReportChatRoomRequest) baseRequest;
        return dealReportChatroom(reportChatRoomRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ReportChatRoomRequest reportChatRoomRequest = (ReportChatRoomRequest) baseRequest;
        return dealReportChatroom(reportChatRoomRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ReportChatRoomRequest reportChatRoomRequest = new ReportChatRoomRequest();
        reportChatRoomRequest.parseRequest(request);
        return reportChatRoomRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        ReportChatRoomRequest reportChatRoomRequest = (ReportChatRoomRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(reportChatRoomRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(reportChatRoomRequest.getAccount());
        return user;
    }

    private ServiceResult dealReportChatroom(ReportChatRoomRequest reportChatRoomRequest ,User user){
        LogContext.instance().info("请求上报直播间数据");
        if(StringUtils.isEmpty(reportChatRoomRequest.getAccount())
                || reportChatRoomRequest.getLiveId() == null)
            return ServiceResultUtil.illegal("参数不正确");
        ReportChatroom reportChatroom = chatroomRecordService.getReportChatroom(reportChatRoomRequest.getLiveId());
        Live live = getLiveInfo(reportChatRoomRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播不存在或已删除");
        }
        if(reportChatroom == null){
            chatroomRecordService.addReportChatroom(user,live,reportChatRoomRequest.getAudienceCount(),
                    reportChatRoomRequest.getViewCount());
        }else{
            if(reportChatRoomRequest.getAudienceCount() != null) {
//                reportChatroom.setViewCount(reportChatRoomRequest.getViewCount());
                reportChatroom.setAudienceCount(reportChatRoomRequest.getAudienceCount());
            }
            chatroomRecordService.updateReportChatroom(reportChatroom);
        }
        Map result = Maps.newHashMap();
        result.put("audience_count", reportChatRoomRequest.getAudienceCount());
        if(reportChatroom == null){
            result.put("add_audience", 0);
        }else {
            result.put("add_audience", reportChatroom.getAddAudience());
        }
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
