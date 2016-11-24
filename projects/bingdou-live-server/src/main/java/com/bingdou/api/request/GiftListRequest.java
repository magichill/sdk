package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;

/**
 * Created by gaoshan on 16-11-4.
 */
public class GiftListRequest extends BaseRequest{

    @Override
    protected String getLoggerName() {
        return "GiftListRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        GiftListRequest request = JsonUtil.jsonStr2Bean(requestString,
                GiftListRequest.class);
        return request;
    }
}
