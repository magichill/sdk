package com.bingdou.api.service;

import com.bingdou.api.chatroom.RongCloud;
import com.bingdou.api.chatroom.model.TokenResult;
import com.bingdou.api.constant.ChatConstant;
import com.bingdou.api.request.GetChatTokenRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/3/2.
 */
@Service
public class GetChatTokenService extends LiveBaseService implements IMethodService {



    @Override
    public String getMethodName() {
        return "get_chat_token";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetChatTokenRequest getChatTokenRequest = (GetChatTokenRequest) baseRequest;
        return dealGetToken(getChatTokenRequest,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetChatTokenRequest getChatTokenRequest = (GetChatTokenRequest) baseRequest;
        return dealGetToken(getChatTokenRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetChatTokenRequest getChatTokenRequest = new GetChatTokenRequest();
        getChatTokenRequest.parseRequest(request);
        return getChatTokenRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetChatTokenRequest getChatTokenRequest = (GetChatTokenRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getChatTokenRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getChatTokenRequest.getAccount());
        return user;
    }

    public ServiceResult dealGetToken(GetChatTokenRequest getChatTokenRequest,User user) throws Exception {
        LogContext.instance().info("获取聊天室token");
        if (StringUtils.isEmpty(getChatTokenRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        RongCloud rongCloud = RongCloud.getInstance(ChatConstant.APP_KEY, ChatConstant.APP_SECRET);
        TokenResult userGetTokenResult = rongCloud.chatUser.getToken("userId"+user.getId(), user.getNickName(), user.getAvatar());
        if(userGetTokenResult.getCode() == 200){
            LogContext.instance().info("获取用户聊天室token成功");
        }else{
            LogContext.instance().info("获取用户聊天室token失败:"+userGetTokenResult.toString());
            return ServiceResultUtil.illegal(userGetTokenResult.getErrorMessage());
        }
        JsonElement result = JsonUtil.bean2JsonTree(userGetTokenResult);
        return ServiceResultUtil.success(result);
    }
}
