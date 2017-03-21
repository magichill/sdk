package com.bingdou.api.service;

import com.bingdou.api.request.BuyLiveRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.Application;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.ConsumeService;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.LogContext;
import com.google.common.collect.Maps;
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
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        BuyLiveRequest buyLiveRequest = (BuyLiveRequest) baseRequest;
        Application application = appBaseService.getAppByAppId(buyLiveRequest.getAppId());
        return null;
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

    private ServiceResult dealBuyLive(BuyLiveRequest buyLiveRequest,User user,Application application){
        if(StringUtils.isEmpty(buyLiveRequest.getAccount()) || buyLiveRequest.getLiveId() == null){
            return ServiceResultUtil.illegal("参数不合法");
        }

        Live live = getLiveInfo(buyLiveRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播不存在");
        }
        consumeService.addConsumeRecord(live,user);
        return ServiceResultUtil.success();
    }

    private Map requestConsumeCoin(String payUrl,String param,BuyLiveRequest buyLiveRequest){

//        Map<String,String> paramMap = Maps.newHashMap();
//        paramMap.put("param", CodecUtils.aesEncode(param, ENCODE_KEY));
//        paramMap.put("request_source_index",buyLiveRequest.getRequestSource());
//        paramMap.put("sign", CodecUtils.getMySign(param,SIGN_KEY));
//        String content = "";
//        try {
//            content = HttpClientUtil.doPostHttpClient("request_consume_coin",payUrl,null,);
//            LogContext.instance().info("支付接口请求参数:"+param);
//            LogContext.instance().info("支付接口返回加密值："+content);
//            Map contentMap = JSON.parseObject(CodecUtils.aesDecode(content, ENCODE_KEY), Map.class);
//            LogContext.instance().info("支付接口返回解密值："+JSON.toJSONString(contentMap));
//            Map resultMap = JSON.parseObject(ObjectUtils.toString(contentMap.get("result")), Map.class);
//            resultMap.put("error", ObjectUtils.toString(contentMap.get("error_message"), ""));
//            return resultMap;
//        }catch (Exception e){
//            LogContext.instance().error("支付接口失败返回值："+content+"[exception in pay]");
//            return null;
//        }
        return null;
    }
}
