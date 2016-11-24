package com.bingdou.api.service;

import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.LogContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-4.
 */
@Service
public class CreateLiveService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "create_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateLiveRequest registerRequest = (CreateLiveRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(registerRequest.getAppId());
        return dealCreateLive(registerRequest, request, "", "");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CreateLiveRequest registerRequest = (CreateLiveRequest) baseRequest;
//        Application application = appBaseService.getAppByAppId(registerRequest.getAppId());
        return dealCreateLive(registerRequest, request, "", "");
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

    private ServiceResult dealCreateLive(CreateLiveRequest createLiveRequest, HttpServletRequest request,
                                         String uid, String ua) throws Exception {
        LogContext.instance().info("创建直播成功");
        return ServiceResultUtil.success();
    }
}
