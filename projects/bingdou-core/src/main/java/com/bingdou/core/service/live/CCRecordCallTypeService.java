package com.bingdou.core.service.live;

import com.bingdou.core.model.live.CCRecordRequest;
import com.bingdou.core.model.live.RecordType;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16/12/22.
 */
@Component
public class CCRecordCallTypeService implements IRecordCallTypeService {

    private static final String START_ACTION = "on_publish";
    private static final String END_ACTION = "on_unpublish";

    private static final String CC_CALLBACK_SUCCESS = "5";  //录播回调状态成功值 失败为10

    @Autowired
    private RecordCallbackService recordCallbackService;

    @Override
    public String getCallType() {
        return RecordType.CC.getName();
    }


    /**
     * 直播开始回调参数
     * {
     "streamUrl":"rtmp://ccpublish.bingdou.tv/live",
     "clientIp":"202.189.0.2",
     "action":"on_publish",
     "startTime":"2016-12-16 15:44:04",
     "streamName":"stream234",
     "liveId":"stream234"
     }


     直播结束回调参数：
     {
     "streamUrl":"rtmp://ccpublish.bingdou.tv/live",
     "clientIp":"202.189.0.2",
     "action":"on_unpublish",
     "startTime":"2016-12-16 15:44:04",
     "endTime":"2016-12-16 15:46:27",
     "streamName":"stream234",
     "liveId":"stream234"
     }
     * @param request
     * @return
     */
    @Override
    public boolean dealRecordCallback(HttpServletRequest request) {
        boolean isSuccess = false;
        LogContext.instance().info("处理CC回调开始");
        CCRecordRequest ccRequest = getParam(request);
        if(ccRequest != null){
            if(ccRequest.getAction().equals(START_ACTION)){
                isSuccess = dealCallbackStart(ccRequest);
            } else if(ccRequest.getAction().equals(END_ACTION)){
                isSuccess = dealCallbackFinish(ccRequest);
            } else if(ccRequest.getStatus().equals(CC_CALLBACK_SUCCESS)){
                isSuccess = dealReplayFinish(ccRequest);
            } else{
                LogContext.instance().warn("CC回调参数不正确，回调参数:"+JsonUtil.bean2JsonStr(ccRequest));
            }
        }else{
            LogContext.instance().warn("CC回调参数对象为空");
        }
        return isSuccess;
    }

    private CCRecordRequest getParam(HttpServletRequest request){
        try {
            CCRecordRequest ccRecordRequest = JsonUtil.jsonStr2Bean((String) request.getAttribute("ccRequest"), CCRecordRequest.class);
            return ccRecordRequest;
        }catch (Exception e){
            LogContext.instance().error("CC回调参数解析异常");
            return null;
        }
    }

    private boolean dealCallbackStart(CCRecordRequest request){
        boolean isSuccess = false;
        LogContext.instance().info("处理CC录制开始回调请求");
        LogContext.instance().info("请求参数:"+ JsonUtil.bean2JsonStr(request));
        String streamName = request.getStreamName();
        if(StringUtils.isNotBlank(streamName)) {
            isSuccess = recordCallbackService.notifyAppServer(streamName, true, null);
        }
        return isSuccess;
    }

    private boolean dealCallbackFinish(CCRecordRequest request){
        boolean isSuccess = false;
        LogContext.instance().info("处理CC推流结束回调请求");
        LogContext.instance().info("请求参数:"+ JsonUtil.bean2JsonStr(request));

        String streamName = request.getStreamName();
        if(StringUtils.isNotBlank(streamName)) {
            isSuccess = recordCallbackService.notifyAppServer(streamName, false, null);
        }
        return isSuccess;
    }

    private boolean dealReplayFinish(CCRecordRequest request){
        boolean isSuccess = false;
        LogContext.instance().info("处理CC录制结束回调请求");
        LogContext.instance().info("请求参数:"+ JsonUtil.bean2JsonStr(request));

        String streamName = request.getStreamName();
        if(StringUtils.isNotBlank(streamName)) {
            isSuccess = recordCallbackService.notifyAppServer(streamName, false, request.getDownloadUrl());
        }
        return isSuccess;
    }

}
