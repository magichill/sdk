package com.bingdou.core.service.live;

import com.bingdou.core.model.live.CncRecordFinishRequest;
import com.bingdou.core.model.live.CncRecordStartRequest;
import com.bingdou.core.model.live.RecordType;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gaoshan on 16-11-25.
 */
@Service
public class CncRecordCallTypeService implements IRecordCallTypeService {

    private static final String MESSAGE_TYPE_START = "ws_record_start";
    private static final String MESSAGE_TYPE_FINISH = "ws_record_finish";

    @Autowired
    private RecordCallbackService recordCallbackService;

    @Override
    public String getCallType() {
        return RecordType.CNC.getName();
    }

    @Override
    public void dealRecordCallback(HttpServletRequest request) {
        String messageType = request.getParameter("message_type");
        if(messageType.equals(MESSAGE_TYPE_START)){
            dealCallbackStart(request);
        }else if(messageType.equals(MESSAGE_TYPE_FINISH)){
            dealCallbackFinish(request);
        }else{
            LogContext.instance().warn("网宿消息类型参数不正确");
        }

    }

    private void dealCallbackStart(HttpServletRequest request){
        CncRecordStartRequest cncRequest = JsonUtil.jsonStr2Bean((String)request.getAttribute("cncRequest"),CncRecordStartRequest.class);
        LogContext.instance().info("处理网宿录制开始回调请求");
        LogContext.instance().info("请求参数:"+ JsonUtil.bean2JsonStr(cncRequest));

    }

    private void dealCallbackFinish(HttpServletRequest request){
        CncRecordFinishRequest cncRequest = JsonUtil.jsonStr2Bean((String)request.getAttribute("cncRequest"),CncRecordFinishRequest.class);
        LogContext.instance().info("处理网宿录制结束回调请求");
        LogContext.instance().info("请求参数:"+ JsonUtil.bean2JsonStr(cncRequest));
        List<CncRecordFinishRequest.FinishItem> items = cncRequest.getItems();
        for(CncRecordFinishRequest.FinishItem item : items ){
            String streamName = dealCncStreamName(item.getStreamName());
            if(StringUtils.isNotBlank(streamName)) {
                recordCallbackService.notifyAppServer(streamName, false, item.getUrls().get(0));
            }
        }
    }

    private String dealCncStreamName(String callbackStreamName){
        LogContext.instance().info("网宿录播回调流名称"+callbackStreamName);
        String[] streamArg = callbackStreamName.split("-");

        if(streamArg.length>1) {
            String streamName = streamArg[1].substring(0,streamArg[1].indexOf("."));
            if(StringUtils.isNumeric(streamName)){
                LogContext.instance().info("处理后网宿录播回调流名称"+streamName);
                return streamName;
            }else{
                return "";
            }
        }else{
            return "";
        }
    }


}
