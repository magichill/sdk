package com.bingdou.payserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.pay.AliPayNoPwdAuthService;
import com.bingdou.payserver.request.GetAliPayNoPwdAuthSignRequest;
import com.bingdou.payserver.response.GetAliPayNoPwdAuthSignResponse;
import com.bingdou.tools.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取支付宝免密授权签约信息服务类
 */
@Service
public class GetAliPayNoPwdAuthSignService extends BaseService implements IMethodService {

    @Autowired
    private AliPayNoPwdAuthService aliPayNoPwdAuthService;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetAliPayNoPwdAuthSignRequest authSignRequest = new GetAliPayNoPwdAuthSignRequest();
        authSignRequest.parseRequest(request);
        return authSignRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetAliPayNoPwdAuthSignRequest request = (GetAliPayNoPwdAuthSignRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    @Override
    public String getMethodName() {
        return "get_ali_no_pwd_auth_sign";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetAliPayNoPwdAuthSignRequest authSignRequest = (GetAliPayNoPwdAuthSignRequest) baseRequest;
        if (StringUtils.isEmpty(authSignRequest.getReturnUrl())) {
            return ServiceResultUtil.illegal("参数错误");
        }
        String url = aliPayNoPwdAuthService.getAuthSignUrl(user.getId(), authSignRequest.getReturnUrl());
        GetAliPayNoPwdAuthSignResponse response = new GetAliPayNoPwdAuthSignResponse();
        response.setAuthUrl(url);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(response));
    }

}
