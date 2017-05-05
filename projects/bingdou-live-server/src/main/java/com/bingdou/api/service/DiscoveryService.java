package com.bingdou.api.service;

import com.bingdou.api.request.DiscoveryRequest;
import com.bingdou.api.response.DiscoveryResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoshan on 17/3/15.
 */
@Service
public class DiscoveryService extends LiveBaseService implements IMethodService {

    //TODO 为推荐主播列表增加缓存设计

    @Override
    public String getMethodName() {
        return "get_live_discovery";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        DiscoveryRequest discoveryRequest = (DiscoveryRequest) baseRequest;
        return dealDiscovery(discoveryRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        DiscoveryRequest discoveryRequest = (DiscoveryRequest) baseRequest;
        return dealDiscovery(discoveryRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        DiscoveryRequest discoveryRequest = new DiscoveryRequest();
        discoveryRequest.parseRequest(request);
        return discoveryRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        DiscoveryRequest discoveryRequest = (DiscoveryRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(discoveryRequest.getAccount()));
    }

    private ServiceResult dealDiscovery(DiscoveryRequest discoveryRequest,User user){
        LogContext.instance().info("请求发现直播数据");
        if(StringUtils.isEmpty(discoveryRequest.getAccount()) || discoveryRequest.getStatus() == null){
            return ServiceResultUtil.illegal("参数不合法");
        }
        Integer start = discoveryRequest.getStart();
        Integer limit = discoveryRequest.getLimit();
        Integer status = discoveryRequest.getStatus();
        if(start!=null && start >0){
            start = (start-1)*limit;
        }
        String timestamp = "";
        if(StringUtils.isEmpty(discoveryRequest.getTimestamp()) || start == 0){
            timestamp = DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        }else{
            timestamp = discoveryRequest.getTimestamp();
        }
        List<Live> liveList = getLiveList(status,null,start,limit,timestamp);
        List<User> userList = userBaseService.getCertificateUsers();
        DiscoveryResponse discoveryResponse = new DiscoveryResponse();
        discoveryResponse.parseFromLiveAndUser(liveList,userList);
        discoveryResponse.setTimestamp(timestamp);
        LogContext.instance().info("获取发现直播数据成功");
        JsonElement result = JsonUtil.bean2JsonTree(discoveryResponse);
        return ServiceResultUtil.success(result);
    }
}
