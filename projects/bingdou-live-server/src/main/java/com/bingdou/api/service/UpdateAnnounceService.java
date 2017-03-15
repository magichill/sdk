package com.bingdou.api.service;

import com.bingdou.api.request.UpdateAnnouceRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/3/14.
 */
@Service
public class UpdateAnnounceService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "update_announce";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        UpdateAnnouceRequest updateAnnouceRequest = (UpdateAnnouceRequest) baseRequest;
        return dealUpdateAnnounce(updateAnnouceRequest,request,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        UpdateAnnouceRequest updateAnnouceRequest = (UpdateAnnouceRequest) baseRequest;
        return dealUpdateAnnounce(updateAnnouceRequest,request,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        UpdateAnnouceRequest updateAnnouceRequest = new UpdateAnnouceRequest();
        updateAnnouceRequest.parseRequest(request);
        return updateAnnouceRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        UpdateAnnouceRequest updateAnnouceRequest = (UpdateAnnouceRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(updateAnnouceRequest.getUserId()));
    }

    private ServiceResult dealUpdateAnnounce(UpdateAnnouceRequest updateAnnouceRequest, HttpServletRequest request,User user) throws Exception {
        if(StringUtils.isEmpty(updateAnnouceRequest.getUserId()) || updateAnnouceRequest.getLiveId() < 0){
            return ServiceResultUtil.illegal("参数不合法");
        }
        if(updateAnnounceLive(user,updateAnnouceRequest)){
            LogContext.instance().info("更新预告直播成功");
            return ServiceResultUtil.success();
        }else{
            LogContext.instance().info("更新预告直播失败");
            return ServiceResultUtil.illegal("更新预告失败");
        }
    }
}
