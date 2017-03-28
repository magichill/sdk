package com.bingdou.api.service;

import com.bingdou.api.request.GetGiftListRequest;
import com.bingdou.api.response.GiftListResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.GiftService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 17/3/27.
 */
@Service
public class GetGiftListService extends LiveBaseService implements IMethodService {

    @Autowired
    private GiftService giftService;


    @Override
    public String getMethodName() {
        return "get_gift_list";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetGiftListRequest getGiftListRequest = (GetGiftListRequest) baseRequest;
        return dealGiftList(getGiftListRequest);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        GetGiftListRequest getGiftListRequest = (GetGiftListRequest) baseRequest;
        return dealGiftList(getGiftListRequest);
    }

    private ServiceResult dealGiftList(GetGiftListRequest giftListRequest){
        LogContext.instance().info("请求获取礼物列表");
        if(StringUtils.isEmpty(giftListRequest.getAccount())){
            return ServiceResultUtil.illegal("请求参数错误");
        }
        List<Gift> gifts = giftService.getGiftList();
        GiftListResponse result = new GiftListResponse();
        result.parseFromGiftList(gifts);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        GetGiftListRequest getGiftListRequest = new GetGiftListRequest();
        getGiftListRequest.parseRequest(request);
        return getGiftListRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        GetGiftListRequest getGiftListRequest = (GetGiftListRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(getGiftListRequest.getAccount()));
    }
}
