package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.utils.DataLogUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.RequestUtil;
import com.bingdou.tools.constants.HadoopLogAction;
import com.bingdou.userserver.request.LogoutRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 注销服务类
 */
@Service
public class LogoutService extends BaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.parseRequest(request);
        return logoutRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        LogoutRequest logoutRequest = (LogoutRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(logoutRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "logout";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        LogoutRequest logoutRequest = (LogoutRequest) baseRequest;
        return dealLogout(logoutRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        LogoutRequest logoutRequest = (LogoutRequest) baseRequest;
        return dealLogout(logoutRequest, user);
    }

    private ServiceResult dealLogout(LogoutRequest logoutRequest, User user) {
        if (StringUtils.isEmpty(logoutRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        boolean result = userBaseService.deleteToken(user.getId());
        if (result) {
            LogContext.instance().info("注销成功");
            DataLogUtils.recordHadoopLog(HadoopLogAction.LOGOUT, logoutRequest, user,
                    RequestUtil.getClientIp(logoutRequest.getRequest()), "", "", false);
            return ServiceResultUtil.success();
        } else {
            return ServiceResultUtil.illegal("注销失败");
        }
    }

}
