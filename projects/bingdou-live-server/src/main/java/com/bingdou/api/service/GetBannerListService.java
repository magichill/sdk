package com.bingdou.api.service;

import com.bingdou.api.request.GetBannerListRequest;
import com.bingdou.api.response.GetBannerListResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Banner;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.BannerService;
import com.bingdou.tools.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/4/22.
 */
@Service
public class GetBannerListService extends LiveBaseService implements IMethodService {

    @Autowired
    private BannerService bannerService;

    @Override
    public String getMethodName() {
        return "get_banner";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetBannerListRequest getBannerListRequest = (GetBannerListRequest) baseRequest;
        return dealGetBannerList(getBannerListRequest,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetBannerListRequest getBannerListRequest = (GetBannerListRequest) baseRequest;
        return dealGetBannerList(getBannerListRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetBannerListRequest getBannerListRequest = new GetBannerListRequest();
        getBannerListRequest.parseRequest(request);
        return getBannerListRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetBannerListRequest getBannerListRequest = (GetBannerListRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(getBannerListRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(getBannerListRequest.getAccount());
        return user;
    }

    private ServiceResult dealGetBannerList(GetBannerListRequest getBannerListRequest,User user){
        if(StringUtils.isEmpty(getBannerListRequest.getAccount())){
            return ServiceResultUtil.illegal("参数不正确");
        }
        List<Banner> bannerList = bannerService.getBannerList();
        GetBannerListResponse result = new GetBannerListResponse();
        result.parseFromBanners(bannerList);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
