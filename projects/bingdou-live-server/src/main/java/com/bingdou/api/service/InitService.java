package com.bingdou.api.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.User;
import com.bingdou.core.service.IMethodService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-8.
 */
public class InitService implements IMethodService {

    @Override
    public String getMethodName() {
        return "init";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }
}
