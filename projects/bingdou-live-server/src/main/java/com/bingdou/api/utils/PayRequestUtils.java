package com.bingdou.api.utils;

import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.KeyGroup;

import java.util.Map;

/**
 * Created by gaoshan on 17/3/27.
 */
public class PayRequestUtils {

    public static Map getPayInfo(String payUrl,Map paramMap){
        try {
            String content = HttpClientUtil.doPostHttpClient("request_consume_coin",payUrl,null,paramMap,3000,3000);
            LogContext.instance().info("支付接口请求参数:");
            LogContext.instance().info("支付接口返回加密值："+content);
            Map contentMap = JsonUtil.jsonStr2Bean(CodecUtils.aesDecode(content, KeyGroup.DEFAULT), Map.class);
            LogContext.instance().info("支付接口返回解密值："+JsonUtil.bean2JsonStr(contentMap));
            Map resultMap = JsonUtil.jsonStr2Bean(String.valueOf(contentMap.get("result")), Map.class);
            resultMap.put("error", String.valueOf(contentMap.get("error_message")));
            return resultMap;
        }catch (Exception e){
            LogContext.instance().error("支付接口失败",e);
            return null;
        }
    }
}
