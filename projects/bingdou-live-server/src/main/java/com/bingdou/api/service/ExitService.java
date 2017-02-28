package com.bingdou.api.service;

import com.bingdou.api.request.ExitRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.ExitRoom;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.ExitDao;
import com.bingdou.core.service.IMethodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/2/27.
 */
@Service
public class ExitService extends LiveBaseService implements IMethodService {

    @Autowired
    private ExitDao exitDao;

    @Override
    public String getMethodName() {
        return "report_exit_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ExitRequest exitRequest = (ExitRequest) baseRequest;
        return dealExit(request,exitRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ExitRequest exitRequest = (ExitRequest) baseRequest;
        return dealExit(request,exitRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ExitRequest exitRequest = new ExitRequest();
        exitRequest.parseRequest(request);
        return exitRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        ExitRequest ExitRequest = (ExitRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(ExitRequest.getUserId()));
    }

    private ServiceResult dealExit(HttpServletRequest request, ExitRequest exitRequest, User user) throws Exception {
        if (StringUtils.isEmpty(exitRequest.getUserId()) || exitRequest.getLiveId() == null) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        Live live = getLiveInfo(exitRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播间不存在");
        }
        ExitRoom exitRoom = exitDao.getExitByUserIdAndLiveId(user.getId(),live.getId());
        if(exitRoom == null){
            ExitRoom newExitRoom = new ExitRoom();
            newExitRoom.setLiveId(live.getId());
            newExitRoom.setUserId(user.getId());
            newExitRoom.setMessageCount(exitRequest.getMessageCount());
            newExitRoom.setPresentCount(exitRequest.getPresentCount());
            exitDao.addExit(newExitRoom);
        }else{
            exitRoom.setMessageCount(exitRoom.getMessageCount()+exitRequest.getMessageCount());
            exitRoom.setPresentCount(exitRoom.getPresentCount()+exitRequest.getPresentCount());
            exitDao.updateExit(exitRoom);
        }
        return ServiceResultUtil.success();
    }
}

