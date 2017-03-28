package com.bingdou.api.service;

import com.bingdou.api.request.BuyLiveRequest;
import com.bingdou.api.utils.PayRequestUtils;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.Application;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ConsumeService;
import com.bingdou.tools.*;
import com.bingdou.tools.constants.KeyGroup;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * Created by gaoshan on 17/3/20.
 */
@Service
public class BuyLiveService extends LiveBaseService implements IMethodService {

    @Autowired
    private ConsumeService consumeService;

    @Override
    public String getMethodName() {
        return "buy_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BuyLiveRequest buyLiveRequest = (BuyLiveRequest) baseRequest;
        Application application = appBaseService.getAppByAppId(buyLiveRequest.getAppId());
        return dealBuyLive(buyLiveRequest,user,application);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BuyLiveRequest buyLiveRequest = (BuyLiveRequest) baseRequest;
        Application application = appBaseService.getAppByAppId(buyLiveRequest.getAppId());
        return dealBuyLive(buyLiveRequest,user,application);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        BuyLiveRequest buyLiveRequest = new BuyLiveRequest();
        buyLiveRequest.parseRequest(request);
        return buyLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        BuyLiveRequest buyLiveRequest = (BuyLiveRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(buyLiveRequest.getAccount()));
    }

    private ServiceResult dealBuyLive(BuyLiveRequest buyLiveRequest,User user,Application application) throws Exception {
        if(StringUtils.isEmpty(buyLiveRequest.getAccount()) || buyLiveRequest.getLiveId() == null){
            return ServiceResultUtil.illegal("参数不合法");
        }
        Live live = getLiveInfo(buyLiveRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播不存在");
        }
        //TODO 判断用户是否购买过当前直播
        boolean isBuy = consumeService.exisRecord(live,user);
        if(isBuy){
            return ServiceResultUtil.illegal("您已购买过该视频");
        }
        Map<String,String> map = requestConsumeCoin(buyLiveRequest,live);
        if(map!= null && StringUtils.isEmpty(map.get("error"))){
            if(StringUtils.isEmpty(map.get("error"))) {
                consumeService.addConsumeRecord(live,user);
                LogContext.instance().info("购买直播成功");
                return ServiceResultUtil.success(JsonUtil.bean2JsonTree(map));
            }else{
                LogContext.instance().info("购买直播失败");
                return ServiceResultUtil.illegal(map.get("error"));
            }
        }else{
            return ServiceResultUtil.illegal("支付失败");
        }
    }

    private Map requestConsumeCoin(BuyLiveRequest buyLiveRequest,Live live) throws Exception{

        String payUrl = PayTypeData.CONSUME_BINGDOU_URL;
        Map<String,String> paramMap = Maps.newHashMap();
        StringBuffer sb = new StringBuffer();
        sb.append("{\"account\":");
        sb.append("\"").append(buyLiveRequest.getAccount()).append("\",");
        sb.append("\"user_order_id\":");
        sb.append("\"").append(DigestUtils.md5Hex(buyLiveRequest.getAppId() + System.currentTimeMillis())).append("\",");
        sb.append("\"order_money\":");
        sb.append(NumberUtil.convertYuanFromFen(live.getPrice())).append(",");
        sb.append("\"goods_name\":");
        sb.append("\"").append("购买直播" + live.getId()).append("\",");
        sb.append("\"goods_description\":");
        sb.append("\"").append(buyLiveRequest.getAppId()).append("\",");
        sb.append("\"goods_price\":");
        sb.append(NumberUtil.convertYuanFromFen(live.getPrice())).append(",");
        sb.append("\"token\":");
        sb.append("\"").append(buyLiveRequest.getToken()).append("\",");
        sb.append("\"app_id\":");
        sb.append("\"").append("123456").append("\"");
        sb.append("}");
        paramMap.put("param", CodecUtils.aesEncode(sb.toString(), KeyGroup.DEFAULT));
        paramMap.put("request_source_index","live-server");
        paramMap.put("sign", CodecUtils.getMySign(sb.toString(),KeyGroup.DEFAULT));

        return PayRequestUtils.getPayInfo(payUrl,paramMap);
    }
}
