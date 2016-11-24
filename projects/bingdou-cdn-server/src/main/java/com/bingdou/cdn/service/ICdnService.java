package com.bingdou.cdn.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;

/**
 * Created by gaoshan on 16-11-11.
 */
public interface ICdnService {

    /**
     * cdn服务商名称
     */
    String getCdnName();

    /**
     * 创建cdn推流服务
     * @return
     */
    ServiceResult createCdnLive(BaseRequest baseRequest);
}
