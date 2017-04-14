package com.bingdou.api.service;

import com.bingdou.api.request.UpdateLiveDescRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaoshan on 17/4/13.
 */
@Service
public class UpdateLiveDescService  extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "update_live_description";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        UpdateLiveDescRequest updateLiveDescRequest = (UpdateLiveDescRequest) baseRequest;
        return dealUpdateDesc(updateLiveDescRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        UpdateLiveDescRequest updateLiveDescRequest = (UpdateLiveDescRequest) baseRequest;
        return dealUpdateDesc(updateLiveDescRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        UpdateLiveDescRequest updateLiveDescRequest = new UpdateLiveDescRequest();
        updateLiveDescRequest.parseRequest(request);
        return updateLiveDescRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        UpdateLiveDescRequest updateLiveDescRequest = (UpdateLiveDescRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(updateLiveDescRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(updateLiveDescRequest.getAccount());
        return user;
    }

    private ServiceResult dealUpdateDesc(UpdateLiveDescRequest updateLiveDescRequest,User user){
        if(StringUtils.isEmpty(updateLiveDescRequest.getAccount())
                || StringUtils.isEmpty(updateLiveDescRequest.getDescription())
                || updateLiveDescRequest.getLiveId() == null){
            return ServiceResultUtil.illegal("参数不合法");
        }
        Live live = getLiveInfo(updateLiveDescRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播不存在");
        }
        live.setDescription(updateLiveDescRequest.getDescription());

        boolean result = updateDescription(live);
        Map map = Maps.newHashMap();
        map.put("result",result?0:1);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(map));

    }
}
