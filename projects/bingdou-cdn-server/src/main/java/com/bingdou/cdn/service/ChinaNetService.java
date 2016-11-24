package com.bingdou.cdn.service;


import com.bingdou.cdn.constant.CdnType;
import com.bingdou.cdn.constant.CncConstant;
import com.bingdou.cdn.request.CreateLiveRequest;
import com.bingdou.cdn.response.CreateLiveResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-11.
 * 网宿cdn服务类
 */
@Service
public class ChinaNetService extends BaseService implements ICdnService {

    @Autowired
    private CdnBaseService cdnBaseService;

    @Override
    public String getCdnName() {
        return CdnType.CHINANET.getName();
    }

    @Override
    public ServiceResult createCdnLive(BaseRequest baseRequest) {
        LogContext.instance().info("创建网宿直播流");
        try {
            CreateLiveRequest createLiveRequest = (CreateLiveRequest) baseRequest;

            String streamName = String.valueOf(System.currentTimeMillis()) + createLiveRequest.getUserId();
            CreateLiveResponse response = buildCncLiveResponse(streamName);

            cdnBaseService.createLive(createLiveRequest,response);
            return ServiceResultUtil.success(response);
        }catch (Exception e){
            LogContext.instance().error(e,"创建网宿直播失败");
            return ServiceResultUtil.serverError("创建直播失败");
        }
    }

    private CreateLiveResponse buildCncLiveResponse(String streamName){
        CreateLiveResponse response = new CreateLiveResponse();
        String pushUrl = CncConstant.CNC_PUSH_URL+CncConstant.CNC_APP_NAME+"/"+streamName;
        String playUrl = CncConstant.CNC_PLAY_URL+CncConstant.CNC_APP_NAME+"/"+streamName;
        String h5Url = CncConstant.CNC_H5_PLAY_URL+CncConstant.CNC_APP_NAME+"/"+streamName+"/playlist.m3u8";
        response.setPushUrl(pushUrl);
        response.setPlayUrl(playUrl);
        response.setH5Url(h5Url);
        return response;
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        CreateLiveRequest createLiveRequest = new CreateLiveRequest();
        createLiveRequest.parseRequest(request);
        return createLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }
}
