package com.bingdou.api.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.User;
import com.bingdou.core.service.IMethodService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/4/11.
 */
public class GetShareRankService extends LiveBaseService implements IMethodService {

    @Autowired
    private ReportShareService reportShareService;

    @Override
    public String getMethodName() {
        return "get_share_rank";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

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
}
