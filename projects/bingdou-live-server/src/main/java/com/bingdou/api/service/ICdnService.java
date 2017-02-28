package com.bingdou.api.service;

import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.User;

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

    boolean createLive(BaseRequest baseRequest,User user);

    String getStreamName(CreateLiveRequest createLiveRequest);
}
