package com.bingdou.core.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-1.
 */
public interface IMethodService {

    /**
     * 请求方法名称
     */
    String getMethodName();

    /**
     * 执行方法(服务器)
     */
    ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception;

    /**
     * 执行方法(客户端)
     */
    ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception;

}
