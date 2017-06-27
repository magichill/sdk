package com.bingdou.api.service;

import com.bingdou.api.constant.ChatConstant;
import com.bingdou.api.constant.Constant;
import com.bingdou.api.request.AllowUserChatRequest;
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
public class AllowUserChatService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "allow_user_chat";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AllowUserChatRequest allowUserChatRequest = (AllowUserChatRequest) baseRequest;
        User banUser = getBanUser(allowUserChatRequest);
        return dealAllowChat(allowUserChatRequest, request,user,banUser);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AllowUserChatRequest allowUserChatRequest = (AllowUserChatRequest) baseRequest;
        User banUser = getBanUser(allowUserChatRequest);
        return dealAllowChat(allowUserChatRequest, request,user,banUser);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        AllowUserChatRequest allowUserChatRequest = new AllowUserChatRequest();
        allowUserChatRequest.parseRequest(request);
        return allowUserChatRequest;
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
        AllowUserChatRequest allowUserChatRequest = (AllowUserChatRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(allowUserChatRequest.getBanAccount()));
    }

    private ServiceResult dealAllowChat(AllowUserChatRequest allowUserChatRequest, HttpServletRequest request,User user,User banUser) throws Exception {
        LogContext.instance().info("解除禁言逻辑");
        if(StringUtils.isEmpty(allowUserChatRequest.getAccount()) ||
                StringUtils.isEmpty(allowUserChatRequest.getLiveId())) {
            LogContext.instance().info("参数不正确");
            return ServiceResultUtil.illegal("参数不正确");
        }
        Live live = getLiveInfo(Integer.valueOf(allowUserChatRequest.getLiveId()));
        if(live == null){
            LogContext.instance().info("直播不存在");
            return ServiceResultUtil.illegal("直播不存在");
        }else{
            if(live.getMid() == user.getId()) {
                RongCloud rongCloud = RongCloud.getInstance(Constant.APP_KEY, Constant.APP_SECRET);
                CodeSuccessReslut banResult = rongCloud.chatroom.rollbackGagUser("userId"+banUser.getId(), allowUserChatRequest.getLiveId());
                if (banResult.getCode() == 200) {
                    LogContext.instance().info("解除禁言用户成功");
                } else {
                    LogContext.instance().info("解除禁言用户失败：" + banResult.toString());
                    return ServiceResultUtil.illegal("解除禁言用户失败");
                }
            }
        }
        return ServiceResultUtil.success("解除禁言成功");
    }
}
