package com.bingdou.payserver.service;

import com.bingdou.core.cache.IWeChatCacheManager;
import com.bingdou.core.cache.WeChatJedisCacheManager;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.WechatTicket;
import com.bingdou.core.model.WechatToken;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.payserver.request.WechatAuthRequest;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoshan on 17/3/9.
 */
@Service
public class WechatAuthService extends BaseService implements IMethodService {

    @Autowired
    private WeChatJedisCacheManager weChatJedisCacheManager;

    @Override
    public String getMethodName() {
        return "wechat_auth";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        WechatAuthRequest wechatAuthRequest = (WechatAuthRequest) baseRequest;
        return dealWechatAuth(wechatAuthRequest);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        WechatAuthRequest wechatAuthRequest = (WechatAuthRequest) baseRequest;
        return dealWechatAuth(wechatAuthRequest);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        WechatAuthRequest wechatAuthRequest = new WechatAuthRequest();
        wechatAuthRequest.parseRequest(request);
        return wechatAuthRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        WechatAuthRequest request = (WechatAuthRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(request.getAccount());
    }

    private ServiceResult dealWechatAuth(WechatAuthRequest wechatAuthRequest) throws Exception {
        if(StringUtils.isEmpty(wechatAuthRequest.getAccount())){
            return ServiceResultUtil.illegal("请求参数不正确");
        }
        String ticketCache = weChatJedisCacheManager.getWechatTicket();
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ PayTypeData.PAY_TYPE_WX_APP_ID_H5+"&secret="+PayTypeData.PAY_TYPE_WX_SECRET_H5;
        if(StringUtils.isEmpty(ticketCache)){
            String tokenResult = HttpClientUtil.doGetHttpClient("wechat access token",url);
            WechatToken token = JsonUtil.jsonStr2Bean(tokenResult, WechatToken.class);
            weChatJedisCacheManager.setWechatToken(token.getAccessToken(),token.getExpireTime());


            String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token.getAccessToken()+"&type=jsapi";
            String ticketResult = HttpClientUtil.doGetHttpClient("wechat ticket",ticketUrl);
            WechatTicket ticket = JsonUtil.jsonStr2Bean(ticketResult,WechatTicket.class);
            if(ticket.getErrcode() == 0){
                weChatJedisCacheManager.setWechatTicket(ticket.getTicket(),ticket.getExpiresTime());
                ticketCache = ticket.getTicket();
            }else{
                LogContext.instance().error("获取ticket失败："+ticket.getErrmsg());
                return ServiceResultUtil.illegal(ticket.getErrmsg());
            }
        }
        Map<String,String> resultMap = Maps.newHashMap();
        long timestamp = DateUtil.getTimeSecondsByTimeZone(8);
        String nonceStr = DigestUtils.md5Hex(System.currentTimeMillis() + "");
        resultMap.put("jsapi_ticket",ticketCache);
        resultMap.put("noncestr",nonceStr);
        resultMap.put("timestamp",String.valueOf(timestamp));
        resultMap.put("url",wechatAuthRequest.getUrl());
        String signature = getSign(resultMap);
        resultMap.put("signature",signature);

        return ServiceResultUtil.success(resultMap);
    }

    private String getSign(Map<String, String> postParamsMap) {
        Map<String, String> signMap = new HashMap<String, String>(postParamsMap);
        signMap.remove("sign");
        String signStr = PayUtils.getSignSeedByMap(signMap);
        LogContext.instance().info("拼接签名参数:"+signStr);
        return DigestUtils.sha1Hex(signStr);
    }
}
