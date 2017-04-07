package com.bingdou.api.service;

import com.bingdou.api.constant.LiveType;
import com.bingdou.api.request.GetLiveInfoRequest;
import com.bingdou.api.response.ComposedLiveResponse;
import com.bingdou.api.response.UserResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ConsumeService;
import com.bingdou.core.service.user.FocusService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-4.
 */
@Service
public class GetLiveInfoService extends LiveBaseService implements IMethodService {

    @Autowired
    private FocusService focusService;

    @Autowired
    private ConsumeService consumeService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetLiveInfoRequest getLiveInfoRequest = new GetLiveInfoRequest();
        getLiveInfoRequest.parseRequest(request);
        return getLiveInfoRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetLiveInfoRequest getLiveInfoRequest = (GetLiveInfoRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getLiveInfoRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getLiveInfoRequest.getAccount());
        return user;
    }

    @Override
    public String getMethodName() {
        return "get_live_info";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveInfoRequest getLiveInfoRequest = (GetLiveInfoRequest) baseRequest;
        return getLiveInfo(getLiveInfoRequest,request,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveInfoRequest getLiveInfoRequest = (GetLiveInfoRequest) baseRequest;
        return getLiveInfo(getLiveInfoRequest,request,user);
    }

    private ServiceResult getLiveInfo(GetLiveInfoRequest getLiveInfoRequest, HttpServletRequest request,User user) throws Exception {
        LogContext.instance().info("获取直播数据详情");
        if(StringUtils.isEmpty(getLiveInfoRequest.getAccount()) || getLiveInfoRequest.getLiveId()==null){
            return ServiceResultUtil.illegal("参数不正确");
        }
        Live live = getLiveInfo(getLiveInfoRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播不存在或已删除");
        }
        ComposedLiveResponse composedLiveResponse = new ComposedLiveResponse();
        composedLiveResponse.parseFromLive(live);
        User liveOwner = userBaseService.getDetailById(live.getMid());
        boolean lock = true;
        int liveType = live.getLiveType();
        if(liveType == LiveType.PAY.getIndex() || liveType == LiveType.CHANNEL_PAY.getIndex()){
            lock = consumeService.exisRecord(live,user);
        }
        if(liveType == LiveType.ENCODE.getIndex()){
            String password = getLiveInfoRequest.getPassword();
            if(StringUtils.isEmpty(password)){
                return ServiceResultUtil.illegal("该直播需要密码");
            }else{
                if(!password.equals(live.getPassword())){
                    lock = false;
                }
            }

        }
        composedLiveResponse.setLock(lock);
        UserResponse userResponse = new UserResponse();
        userResponse.parseFromUser(liveOwner);
        Integer focusStatus = focusService.checkFocusInfoStatus(user.getId(),liveOwner.getId());
        userResponse.setFollowingStatus(focusStatus==null?0:focusStatus);
        composedLiveResponse.setUserResponse(userResponse);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(composedLiveResponse));
    }

}
