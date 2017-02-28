package com.bingdou.api.service;


import com.bingdou.api.constant.CdnType;
import com.bingdou.api.constant.CncConstant;
import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.api.response.CreateLiveResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.model.User;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-11.
 * 网宿cdn服务类
 */
@Service
public class ChinaNetService extends LiveBaseService implements ICdnService {

    @Override
    public String getCdnName() {
        return CdnType.CHINANET.getName();
    }

    @Override
    public ServiceResult createCdnLive(BaseRequest baseRequest) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createLive(BaseRequest baseRequest,User user) {
        LogContext.instance().info("创建网宿直播流");
        CreateLiveRequest createLiveRequest = (CreateLiveRequest) baseRequest;

        String streamName = getStreamName(createLiveRequest);
        createLiveRequest.setStreamId(streamName);
        CreateLiveResponse response = buildCncLiveResponse(streamName);
        return createLive(user,createLiveRequest,response,streamName);
    }

    @Override
    public String getStreamName(CreateLiveRequest createLiveRequest) {
        LogContext.instance().info("生成网宿流名称");
        String streamName = StringUtils.EMPTY;
        if(StringUtils.isNotBlank(createLiveRequest.getStreamId())){
            streamName = createLiveRequest.getStreamId();
        }else {
            streamName = String.valueOf(System.currentTimeMillis());
        }
        return streamName;
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
