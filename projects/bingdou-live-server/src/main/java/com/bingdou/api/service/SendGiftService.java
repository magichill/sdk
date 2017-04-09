package com.bingdou.api.service;

import com.bingdou.api.request.SendGiftRequest;
import com.bingdou.api.utils.PayRequestUtils;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.GiftService;
import com.bingdou.tools.*;
import com.bingdou.tools.constants.KeyGroup;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaoshan on 17/3/27.
 */
@Service
public class SendGiftService extends LiveBaseService implements IMethodService {

    @Autowired
    private GiftService giftService;

    @Override
    public String getMethodName() {
        return "send_gift";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        SendGiftRequest sendGiftRequest = (SendGiftRequest) baseRequest;
        return dealSendGift(sendGiftRequest,user);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        SendGiftRequest sendGiftRequest = (SendGiftRequest) baseRequest;
        return dealSendGift(sendGiftRequest,user);
    }

    private ServiceResult dealSendGift(SendGiftRequest sendGiftRequest,User user) throws Exception {
        LogContext.instance().info("赠送礼物逻辑");
        if(StringUtils.isEmpty(sendGiftRequest.getAccount())
                || sendGiftRequest.getGiftId() == null
                || sendGiftRequest.getLiveId() == null){
            return ServiceResultUtil.illegal("赠送礼物参数错误");
        }
        Live live = getLiveInfo(sendGiftRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播不存在");
        }
        Gift gift = giftService.getGiftInfo(sendGiftRequest.getGiftId());
        if(gift == null){
            return ServiceResultUtil.illegal("礼物不存在或已下架");
        }
        Map<String,String> map = requestConsumeCoin(sendGiftRequest,gift);
        if(map!= null){
            if(StringUtils.isEmpty(map.get("error"))) {
                LogContext.instance().info("赠送礼物成功");
                giftService.recordSendGift(live,user,gift,1);
                return ServiceResultUtil.success(JsonUtil.bean2JsonTree(map));
            }else{
                LogContext.instance().info("赠送礼物失败");
                return ServiceResultUtil.illegal(map.get("error"));
            }
        }else{
            return ServiceResultUtil.illegal("支付失败");
        }
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        SendGiftRequest sendGiftRequest = new SendGiftRequest();
        sendGiftRequest.parseRequest(request);
        return sendGiftRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        SendGiftRequest sendGiftRequest = (SendGiftRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(sendGiftRequest.getAccount()));
    }

    private Map requestConsumeCoin(SendGiftRequest sendGiftRequest, Gift gift) throws Exception{

        String payUrl = PayTypeData.CONSUME_BINGDOU_URL;
        Map<String,String> paramMap = Maps.newHashMap();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":");
        sb.append("\"").append(sendGiftRequest.getAccount()).append("\",");
        sb.append("\"user_order_id\":");
        sb.append("\"").append(DigestUtils.md5Hex(sendGiftRequest.getAppId() + System.currentTimeMillis())).append("\",");
        sb.append("\"order_money\":");
        sb.append(NumberUtil.convertYuanFromBingdou(gift.getPrice())).append(",");
        sb.append("\"goods_name\":");
        sb.append("\"").append("赠送礼物" + gift.getId()).append("\",");
        sb.append("\"goods_description\":");
        sb.append("\"").append(sendGiftRequest.getAppId()).append("\",");
        sb.append("\"goods_price\":");
        sb.append(gift.getPrice()).append(",");
        sb.append("\"token\":");
        sb.append("\"").append(sendGiftRequest.getToken()).append("\",");
        sb.append("\"app_id\":");
        sb.append("\"").append("123456").append("\"");
        sb.append("}");
        paramMap.put("param", CodecUtils.aesEncode(sb.toString(), KeyGroup.DEFAULT));
        paramMap.put("request_source_index","live-server");
        paramMap.put("sign", CodecUtils.getMySign(sb.toString(),KeyGroup.DEFAULT));

        return PayRequestUtils.getPayInfo(payUrl,paramMap);
    }
}
