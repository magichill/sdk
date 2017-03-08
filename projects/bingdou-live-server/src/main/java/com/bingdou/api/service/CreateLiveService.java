package com.bingdou.api.service;

import com.bingdou.api.chatroom.RongCloud;
import com.bingdou.api.chatroom.model.ChatRoomInfo;
import com.bingdou.api.chatroom.model.CodeSuccessReslut;
import com.bingdou.api.constant.CdnType;
import com.bingdou.api.constant.ChatConstant;
import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.api.response.CreateLiveResponse;
import com.bingdou.core.cache.ICdnConfigManager;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.gson.JsonElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-4.
 */
@Service
public class CreateLiveService extends LiveBaseService implements IMethodService {

    @Autowired
    private CdnServiceFactory cdnServiceFactory;

    @Autowired
    private ICdnConfigManager cdnConfigManager;

    @Override
    public String getMethodName() {
        return "create_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateLiveRequest registerRequest = (CreateLiveRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(registerRequest.getAppId());
        return dealCreateLive(registerRequest, request,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateLiveRequest registerRequest = (CreateLiveRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(registerRequest.getAppId());
        return dealCreateLive(registerRequest, request,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        CreateLiveRequest createLiveRequest = new CreateLiveRequest();
        createLiveRequest.parseRequest(request);
        return createLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        CreateLiveRequest createLiveRequest = (CreateLiveRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(createLiveRequest.getUserId()));
    }

    private ServiceResult dealCreateLive(CreateLiveRequest createLiveRequest, HttpServletRequest request,User user) throws Exception {
        LogContext.instance().info("开始创建直播");
        if(!createLiveRequest.checkValid()){
            return ServiceResultUtil.illegal("创建直播参数不合法");
        }
        String method = cdnConfigManager.getCdnConfig();
        if(StringUtils.isBlank(method)){
            method = CdnType.CHINANET.getName();
            cdnConfigManager.setCdnConfig(method);
        }
        ICdnService cdnService = cdnServiceFactory.getCdnServiceMap().get(method);
        boolean createSuccess = cdnService.createLive(createLiveRequest,user);
        if(createSuccess) {
            LogContext.instance().info("创建直播成功");
            LogContext.instance().info("创建融云聊天室");
            RongCloud rongCloud = RongCloud.getInstance(ChatConstant.APP_KEY, ChatConstant.APP_SECRET);

            Live live = getLiveInfoByStreamName(createLiveRequest.getStreamId());
            ChatRoomInfo[] chatroomCreateChatRoomInfo = {new ChatRoomInfo("chatroom"+live.getId(),live.getLiveTitle() ), new ChatRoomInfo("chatroomId2","chatroomName2" ), new ChatRoomInfo("chatroomId3","chatroomName3" )};
            CodeSuccessReslut chatroomCreateResult = rongCloud.chatroom.create(chatroomCreateChatRoomInfo);
            if(chatroomCreateResult.getCode() == 200){
                LogContext.instance().info("创建融云聊天室成功");
            }else{
                LogContext.instance().info("创建融云聊天室失败："+chatroomCreateResult.toString());
                throw new Exception("聊天室创建失败");
            }
            CreateLiveResponse response = new CreateLiveResponse();
            response.parseFromLive(live);
            JsonElement result = JsonUtil.bean2JsonTree(response);
            return ServiceResultUtil.success(result);
        }else {
            return ServiceResultUtil.illegal("创建直播失败");
        }

    }

}
