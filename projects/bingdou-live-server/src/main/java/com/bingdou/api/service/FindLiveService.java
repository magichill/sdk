package com.bingdou.api.service;

import com.bingdou.api.request.FindLiveRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.Live;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 直播列表服务类
 * Created by gaoshan on 16-11-4.
 */
@Service
public class FindLiveService extends LiveBaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        FindLiveRequest findLiveRequest = new FindLiveRequest();
        findLiveRequest.parseRequest(request);
        return findLiveRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    @Override
    public String getMethodName() {
        return "find_live";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        FindLiveRequest findLiveRequest = (FindLiveRequest) baseRequest;
        return dealFindLive(findLiveRequest,request);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        FindLiveRequest findLiveRequest = (FindLiveRequest) baseRequest;
        return dealFindLive(findLiveRequest,request);
    }

    private ServiceResult dealFindLive(FindLiveRequest findLiveRequest, HttpServletRequest request) throws Exception {
        int start = findLiveRequest.getStart();
        int limit = findLiveRequest.getLimit();
        List<Live> result = getLiveList(start,limit);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }

}
