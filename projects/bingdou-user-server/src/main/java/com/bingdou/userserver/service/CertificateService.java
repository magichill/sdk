package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.repository.user.CertificateDao;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.LogContext;
import com.bingdou.userserver.request.CertificateRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/1/31.
 */
@Service
public class CertificateService extends BaseService implements IMethodService {

    @Autowired
    private CertificateDao certificateDao;

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        CertificateRequest certificateRequest = new CertificateRequest();
        certificateRequest.parseRequest(request);
        return certificateRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        CertificateRequest certificateRequest = (CertificateRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(certificateRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(certificateRequest.getAccount());
        return user;
    }

    @Override
    public String getMethodName() {
        return "certificate_user";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CertificateRequest certificateRequest = (CertificateRequest) baseRequest;
        return dealCertificate(request,certificateRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        CertificateRequest certificateRequest = (CertificateRequest) baseRequest;
        return dealCertificate(request,certificateRequest,user);
    }

    private ServiceResult dealCertificate(HttpServletRequest request, CertificateRequest certificateRequest, User user) throws Exception {
        if (StringUtils.isEmpty(certificateRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        if(user != null){
            certificateDao.insertAnchorCertificate(user.getId(),1);
            LogContext.instance().info("认证用户成功！");
            return ServiceResultUtil.success();
        }else{
            LogContext.instance().info("认证用户失败！");
            return ServiceResultUtil.illegal("用户不存在！");
        }

    }

}
