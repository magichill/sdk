package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.UserToken;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.userserver.request.GetUserInfoRequest;
import com.bingdou.userserver.response.GetUserInfoResponse;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息服务类
 */
@Service
public class UserInfoService extends BaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest();
        getUserInfoRequest.parseRequest(request);
        return getUserInfoRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetUserInfoRequest getUserInfoRequest = (GetUserInfoRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getUserInfoRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getUserInfoRequest.getAccount());
        return user;
    }

    @Override
    public String getMethodName() {
        return "get_user_info";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetUserInfoRequest getUserInfoRequest = (GetUserInfoRequest) baseRequest;
        return deal(request, getUserInfoRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetUserInfoRequest getUserInfoRequest = (GetUserInfoRequest) baseRequest;
        return deal(request, getUserInfoRequest, user);
    }

    private ServiceResult deal(HttpServletRequest request, GetUserInfoRequest getUserInfoRequest, User user) throws Exception {
        if (StringUtils.isEmpty(getUserInfoRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        GetUserInfoResponse getUserInfoResponse = new GetUserInfoResponse();
        //TODO 用户等级暂不考虑
//        UserVipGrade userVipGrade = vipGradeService.getUserVipGradeInfo(user.getId());
        LogContext.instance().info("获取用户TOKEN对象");
        UserToken userToken = userBaseService.getUserTokenObject(user.getId());
        if (userToken != null) {
            user.setToken(userToken.getToken());
            user.setvToken(userToken.getValidateToken());
        }
        boolean isSupportLive = false;
        boolean isSupportVirtualMoney = false;
        boolean isSigned = false;
        getUserInfoResponse.parseFromUser(user, null, isSupportVirtualMoney, isSupportLive,
                isSigned, 0);
        JsonElement result = JsonUtil.bean2JsonTree(getUserInfoResponse);
        LogContext.instance().info("获取用户信息成功");
        return ServiceResultUtil.success(result);
    }

}
