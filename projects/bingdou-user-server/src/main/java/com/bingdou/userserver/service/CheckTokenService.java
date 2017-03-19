package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.LogContext;
import com.bingdou.userserver.request.CheckTokenRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/3/17.
 */
@Service
public class CheckTokenService extends BaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "check_token";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CheckTokenRequest checkTokenRequest = (CheckTokenRequest) baseRequest;
        return dealCheckToken(checkTokenRequest, user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CheckTokenRequest checkTokenRequest = (CheckTokenRequest) baseRequest;
        return dealCheckToken(checkTokenRequest, user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
        checkTokenRequest.parseRequest(request);
        return checkTokenRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        CheckTokenRequest checkTokenRequest = (CheckTokenRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(checkTokenRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(checkTokenRequest.getAccount());
        return user;
    }

    private ServiceResult dealCheckToken(CheckTokenRequest checkTokenRequest, User user) throws Exception {
        LogContext.instance().info("验证用户token");
        if (StringUtils.isEmpty(checkTokenRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        return ServiceResultUtil.success();
    }
}
