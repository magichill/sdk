package com.bingdou.api.service;

import com.bingdou.api.request.AddGiftRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.GiftService;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/3/28.
 */
//TODO 转移至后台接口系统
@Service
public class AddGiftService extends LiveBaseService implements IMethodService {

    @Autowired
    private GiftService giftService;

    @Override
    public String getMethodName() {
        return "add_gift";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AddGiftRequest addGiftRequest = (AddGiftRequest) baseRequest;
        return dealAddGift(addGiftRequest);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        AddGiftRequest addGiftRequest = new AddGiftRequest();
        addGiftRequest.parseRequest(request);
        return addGiftRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    private ServiceResult dealAddGift(AddGiftRequest addGiftRequest){
        if(StringUtils.isEmpty(addGiftRequest.getGiftTitle()) ||
                addGiftRequest.getPrice() == null ||
                addGiftRequest.getGiftType() == null){
            return ServiceResultUtil.illegal("参数不正确");
        }
        Gift gift = new Gift();
        gift.setGiftType(addGiftRequest.getGiftType());
        //TODO 换算成冰豆
        gift.setPrice(addGiftRequest.getPrice());
        gift.setGiftDesc(addGiftRequest.getGiftDesc());
        gift.setGiftPic(addGiftRequest.getGiftPic());
        gift.setGiftTitle(addGiftRequest.getGiftTitle());
        giftService.addGift(gift);
        return ServiceResultUtil.success();
    }
}
