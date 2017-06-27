package com.bingdou.api.service;

import com.bingdou.api.constant.ChatConstant;
import com.bingdou.api.constant.Constant;
import com.bingdou.api.request.BanUserChatRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.user.chatroom.RongCloud;
import com.bingdou.core.service.user.chatroom.model.CodeSuccessReslut;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 27/05/17.
 */
@Service
public class BanUserChatService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "ban_user_chat";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BanUserChatRequest banUserChatRequest = (BanUserChatRequest) baseRequest;
        User banUser = getBanUser(banUserChatRequest);
        return dealBanChat(banUserChatRequest, request,user,banUser);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BanUserChatRequest banUserChatRequest = (BanUserChatRequest) baseRequest;
        User banUser = getBanUser(banUserChatRequest);
        return dealBanChat(banUserChatRequest, request,user,banUser);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        BanUserChatRequest banUserChatRequest = new BanUserChatRequest();
        banUserChatRequest.parseRequest(request);
        return banUserChatRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        BanUserChatRequest banUserChatRequest = (BanUserChatRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(banUserChatRequest.getAccount()));
    }

    public User getBanUser(BaseRequest baseRequest) {
        BanUserChatRequest banUserChatRequest = (BanUserChatRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(banUserChatRequest.getBanAccount()));
    }

    private ServiceResult dealBanChat(BanUserChatRequest banUserChatRequest, HttpServletRequest request,User user,User banUser) throws Exception {
        LogContext.instance().info("禁言用户逻辑");
        if(StringUtils.isEmpty(banUserChatRequest.getAccount()) ||
            StringUtils.isEmpty(banUserChatRequest.getLiveId()) ||
                StringUtils.isEmpty(banUserChatRequest.getMinute())) {
            LogContext.instance().info("参数不正确");
            return ServiceResultUtil.illegal("参数不正确");
        }
        Live live = getLiveInfo(Integer.valueOf(banUserChatRequest.getLiveId()));
        if(live == null){
            LogContext.instance().info("直播不存在");
            return ServiceResultUtil.illegal("直播不存在");
        }else{
            if(live.getMid() == user.getId()) {
                RongCloud rongCloud = RongCloud.getInstance(Constant.APP_KEY, Constant.APP_SECRET);
                CodeSuccessReslut banResult = rongCloud.chatroom.addGagUser("userId"+banUser.getId(),
                        banUserChatRequest.getLiveId(), banUserChatRequest.getMinute());
                if (banResult.getCode() == 200) {
                    LogContext.instance().info("禁言用户成功");
                } else {
                    LogContext.instance().info("禁言用户失败：" + banResult.toString());
                    return ServiceResultUtil.illegal("禁言用户失败");
                }
            }
        }
        return ServiceResultUtil.success("禁言用户成功");
    }
}
