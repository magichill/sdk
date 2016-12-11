package com.bingdou.api.service;

import com.bingdou.api.request.GetLiveInfoRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-4.
 */
@Service
public class GetLiveInfoService extends BaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "get_live_info";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveInfoRequest getLiveInfoRequest = (GetLiveInfoRequest) baseRequest;

        return null;
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetLiveInfoRequest getLiveInfoRequest = (GetLiveInfoRequest) baseRequest;

        return null;
    }

    private ServiceResult getLiveInfo(GetLiveInfoRequest getLiveInfoRequest, HttpServletRequest request) throws Exception {

        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(""));
    }

}
