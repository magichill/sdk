package com.bingdou.api.service;

import com.bingdou.api.request.UpdateGiftRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/3/28.
 */
@Service
public class UpdateGiftService extends LiveBaseService implements IMethodService {

    @Autowired
    private GiftService giftService;

    @Override
    public String getMethodName() {
        return "update_gift";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        UpdateGiftRequest UpdateGiftRequest = (UpdateGiftRequest) baseRequest;
        return dealUpdateGift(UpdateGiftRequest);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        UpdateGiftRequest UpdateGiftRequest = new UpdateGiftRequest();
        UpdateGiftRequest.parseRequest(request);
        return UpdateGiftRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    private ServiceResult dealUpdateGift(UpdateGiftRequest updateGiftRequest){
        if(updateGiftRequest.getGiftId() == null){
            return ServiceResultUtil.illegal("参数不正确");
        }
        Gift gift = giftService.getGiftInfo(updateGiftRequest.getGiftId());
        if(gift == null){
            return ServiceResultUtil.illegal("礼物不存在或已下架");
        }
        gift.setGiftType(updateGiftRequest.getGiftType());
        gift.setPrice(updateGiftRequest.getPrice());
        gift.setGiftDesc(updateGiftRequest.getGiftDesc());
        gift.setGiftPic(updateGiftRequest.getGiftPic());
        gift.setGiftTitle(updateGiftRequest.getGiftTitle());
        giftService.updateGift(gift);
        return ServiceResultUtil.success();
    }
}
