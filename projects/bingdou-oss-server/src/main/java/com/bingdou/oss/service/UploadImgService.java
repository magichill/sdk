package com.bingdou.oss.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/6/10.
 */
@Service
public class UploadImgService extends BaseService implements IMethodService {

    public String getMethodName() {
        return "upload_img";
    }

    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        return null;
    }

    public boolean checkUser() {
        return false;
    }

    public User getUser(BaseRequest baseRequest) {
        return null;
    }
}
