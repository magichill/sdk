package com.bingdou.api.service;

import com.bingdou.api.constant.LiveType;
import com.bingdou.api.request.RemoveLiveRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.bingdou.tools.LogContext;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by gaoshan on 16-11-4.
 */
@Service
public class RemoveLiveService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "delete_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RemoveLiveRequest removeLiveRequest = (RemoveLiveRequest) baseRequest;
        return dealRemoveLive(removeLiveRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        RemoveLiveRequest removeLiveRequest = (RemoveLiveRequest) baseRequest;
        return dealRemoveLive(removeLiveRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        RemoveLiveRequest removeLiveRequest = new RemoveLiveRequest();
        removeLiveRequest.parseRequest(request);
        return removeLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        RemoveLiveRequest removeLiveRequest = (RemoveLiveRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(removeLiveRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(removeLiveRequest.getAccount());
        return user;
    }

    private ServiceResult dealRemoveLive(RemoveLiveRequest removeLiveRequest,User user){
        LogContext.instance().info("删除直播");
        if(StringUtils.isEmpty(removeLiveRequest.getAccount())
                || removeLiveRequest.getLiveId() == null){
            return ServiceResultUtil.illegal("参数不正确");
        }

        Live live = getLiveInfo(removeLiveRequest.getLiveId());
        if(live == null || live.getLiveType() == LiveType.PAY.getIndex()
                || live.getLiveType() == LiveType.CHANNEL_PAY.getIndex()){
            return ServiceResultUtil.illegal("视频不可删除");
        }
        Integer liveId = getLiveIdByMid(removeLiveRequest.getLiveId(),user.getId());
        Map result = Maps.newHashMap();
        boolean removeStatus = false;
        if(liveId != null){
            removeStatus = removeLive(liveId);
        }
        result.put("result",removeStatus?0:1);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
