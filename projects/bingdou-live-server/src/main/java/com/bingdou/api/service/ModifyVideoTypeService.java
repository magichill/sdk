package com.bingdou.api.service;

import com.bingdou.api.request.ModifyVideoTypeRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
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
 * Created by gaoshan on 17/4/19.
 */
@Service
public class ModifyVideoTypeService extends LiveBaseService implements IMethodService {

    @Override
    public String getMethodName() {
        return "modify_video_type";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ModifyVideoTypeRequest modifyVideoTypeRequest = (ModifyVideoTypeRequest) baseRequest;
        return dealModifyVideoType(modifyVideoTypeRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        ModifyVideoTypeRequest modifyVideoTypeRequest = (ModifyVideoTypeRequest) baseRequest;
        return dealModifyVideoType(modifyVideoTypeRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        ModifyVideoTypeRequest modifyVideoTypeRequest = new ModifyVideoTypeRequest();
        modifyVideoTypeRequest.parseRequest(request);
        return modifyVideoTypeRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        ModifyVideoTypeRequest modifyVideoTypeRequest = (ModifyVideoTypeRequest) baseRequest;
        User user = userBaseService.getUserDetailByAccount(modifyVideoTypeRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(modifyVideoTypeRequest.getAccount());
        return user;
    }

    private ServiceResult dealModifyVideoType(ModifyVideoTypeRequest modifyVideoTypeRequest,User user){
        LogContext.instance().info("修改视频类型");
        if(StringUtils.isEmpty(modifyVideoTypeRequest.getAccount()) ||
                modifyVideoTypeRequest.getLiveId() == null){
            return ServiceResultUtil.illegal("参数不正确");
        }

        Integer liveId = getLiveIdByMid(modifyVideoTypeRequest.getLiveId(),user.getId());
        if(liveId == null){
            return ServiceResultUtil.illegal("无权限修改视频类型");
        }
        Map result = Maps.newHashMap();
        boolean modifyStatus = updateVideoType(liveId);
        result.put("result",modifyStatus?0:1);
        return ServiceResultUtil.success(JsonUtil.bean2JsonTree(result));
    }
}
