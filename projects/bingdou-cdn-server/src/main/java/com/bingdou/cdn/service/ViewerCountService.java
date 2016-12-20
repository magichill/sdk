package com.bingdou.cdn.service;

import com.bingdou.cdn.constant.CncConstant;
import com.bingdou.cdn.request.ViewerCountRequest;
import com.bingdou.cdn.response.ViewerCountResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoshan on 16/12/13.
 */
@Component
public class ViewerCountService extends BaseService implements IMethodService {


    @Override
    public String getMethodName() {
        return "viewer_count";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ViewerCountRequest viewerCountRequest = (ViewerCountRequest)baseRequest;
        String errorMessage = checkRequest(viewerCountRequest);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return dealViewerCount(viewerCountRequest);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ViewerCountRequest viewerCountRequest = (ViewerCountRequest)baseRequest;
        String errorMessage = checkRequest(viewerCountRequest);
        if (StringUtils.isNotEmpty(errorMessage)) {
            return ServiceResultUtil.illegal(errorMessage);
        }
        return dealViewerCount(viewerCountRequest);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ViewerCountRequest viewerCountRequest = new ViewerCountRequest();
        viewerCountRequest.parseRequest(request);
        return viewerCountRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    private ServiceResult dealViewerCount(ViewerCountRequest viewerCountRequest){
        String streamName = viewerCountRequest.getStreamName();
        LogContext.instance().info("查询直播在线人数开始");
        int count = 0;
        try {
            String result = HttpClientUtil.doGetHttpClient("查询直播在线人数",getRequestUrl(),3000,3000);
            LogContext.instance().info("查询返回结果:"+result);
            if(StringUtils.isNotEmpty(result)){
                ViewerCountResponse response = JsonUtil.jsonStr2Bean(result, ViewerCountResponse.class);
                List<ViewerCountResponse.DataValue> list = response.getDataValue();
                for(ViewerCountResponse.DataValue dataValue : list){
                    if(dataValue.getProg().equals(streamName)){
                        count = dataValue.getValue();
                        break;
                    }
                }
            }
            return ServiceResultUtil.success(buildResponse(count));
        } catch (Exception e) {
            LogContext.instance().error("查询在线人数失败");
            return ServiceResultUtil.serverError("查询在线人数失败");
        }
    }

    private Map buildResponse(int count) {
        Map map = new HashMap();
        map.put("count",count);
        return map;
    }

    private String checkRequest(ViewerCountRequest viewerCountRequest){
        if (StringUtils.isEmpty(viewerCountRequest.getStreamName())) {
            return "流名称不能为空";
        }
        return "";
    }

    private String getRequestUrl(){
        String randomNum = DateUtil.format(new Date(),DateUtil.DHHMMSSSSS);
        String key = DigestUtils.md5Hex(randomNum+CncConstant.CNC_KEY);
        return MessageFormat.format(CncConstant.VIEWER_QUERY_URL,CncConstant.CNC_PORTAL, randomNum,key,CncConstant.CNC_DOMAIN);
    }

}
