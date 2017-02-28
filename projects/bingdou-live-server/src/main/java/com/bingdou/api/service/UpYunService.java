package com.bingdou.api.service;

import com.bingdou.api.constant.CdnType;
import com.bingdou.api.constant.UpYunConstant;
import com.bingdou.api.request.CreateLiveRequest;
import com.bingdou.api.response.CreateLiveResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-11.
 * 又拍云cdn服务类
 *
 */
@Service
public class UpYunService extends LiveBaseService implements ICdnService{

    @Override
    public String getCdnName() {
        return CdnType.UPYUN.getName();
    }

    @Override
    public ServiceResult createCdnLive(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public boolean createLive(BaseRequest baseRequest,User user) {
        LogContext.instance().info("创建又拍云直播流");

        CreateLiveRequest createLiveRequest = (CreateLiveRequest) baseRequest;

        String streamName = getStreamName(createLiveRequest);
        createLiveRequest.setStreamId(streamName);
        CreateLiveResponse response = buildUpyunLiveResponse(streamName);
        return createLive(user,createLiveRequest,response,streamName);
    }

    @Override
    public String getStreamName(CreateLiveRequest createLiveRequest) {
        LogContext.instance().info("生成又拍云流名称");
        String streamName = StringUtils.EMPTY;
        if(StringUtils.isNotBlank(createLiveRequest.getStreamId())){
            streamName = createLiveRequest.getStreamId();
        }else {
            streamName = String.valueOf(System.currentTimeMillis()) + createLiveRequest.getUserId();
        }
        return streamName;
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

    private CreateLiveResponse buildUpyunLiveResponse(String streamName){
        CreateLiveResponse response = new CreateLiveResponse();
        String pushUrl = UpYunConstant.UPYUN_PUSH_URL+UpYunConstant.UPYUN_APP_NAME+"/"+streamName;
        String playUrl = UpYunConstant.UPYUN_PLAY_URL+UpYunConstant.UPYUN_APP_NAME+"/"+streamName;
        String h5Url = UpYunConstant.UPYUN_PLAY_URL+UpYunConstant.UPYUN_APP_NAME+"/"+streamName+UpYunConstant.M3U8_SUFFIX;
        response.setPushUrl(pushUrl);
        response.setPlayUrl(playUrl);
        response.setH5Url(h5Url);
        return response;
    }
}
