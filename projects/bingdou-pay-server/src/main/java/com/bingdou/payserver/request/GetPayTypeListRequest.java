package com.bingdou.payserver.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * ��ȡ֧���б�����
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
