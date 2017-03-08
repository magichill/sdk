package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * 获取支付列表请求
 */
public class GetPayTypeListRequest extends BaseRequest {

    @Override
    protected String getLoggerName() {
        return "GetPayTypeListRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        return JsonUtil.jsonStr2Bean(requestString, GetPayTypeListRequest.class);
    }

}
