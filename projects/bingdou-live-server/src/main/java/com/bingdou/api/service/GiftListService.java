package com.bingdou.api.service;

import com.bingdou.api.request.GiftListRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-4.
 */
@Service
public class GiftListService extends BaseService implements IMethodService{

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GiftListRequest giftListRequest = new GiftListRequest();
        giftListRequest.parseRequest(request);
        return giftListRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "gift_list";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    private ServiceResult dealGiftList(GiftListRequest giftListRequest, HttpServletRequest request,
                                       String uid, String ua) throws Exception {

        return ServiceResultUtil.success();
    }


}
