package com.bingdou.cdn.service;

import com.bingdou.cdn.constant.CdnType;
import com.bingdou.cdn.constant.UpYunConstant;
import com.bingdou.cdn.request.CreateLiveRequest;
import com.bingdou.cdn.response.CreateLiveResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-11.
 * 又拍云cdn服务类
 *
 */
@Service
public class UpYunService extends BaseService implements ICdnService{

    @Autowired
    private CdnBaseService cdnBaseService;

    @Override
    public String getCdnName() {
        return CdnType.UPYUN.getName();
    }

    @Override
    public ServiceResult createCdnLive(BaseRequest baseRequest) {
        LogContext.instance().info("创建又拍云直播流");

        try {
            CreateLiveRequest createLiveRequest = (CreateLiveRequest) baseRequest;

            String streamName = getStreamName(createLiveRequest);
            CreateLiveResponse response = buildUpyunLiveResponse(streamName);

            cdnBaseService.createLive(createLiveRequest,response,streamName);
            return ServiceResultUtil.success(response);
        }catch (Exception e){
            LogContext.instance().error(e,"创建又拍云直播失败");
            return ServiceResultUtil.serverError("创建直播失败");
        }
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
