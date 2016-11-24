package com.bingdou.userserver.service;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.service.BaseService;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.ValidateUtil;
import com.bingdou.userserver.request.UpdateNicknameRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 修改昵称服务类
 */
@Service
public class UpdateNicknameService extends BaseService implements IMethodService {

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        UpdateNicknameRequest updateNicknameRequest = new UpdateNicknameRequest();
        updateNicknameRequest.parseRequest(request);
        return updateNicknameRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        UpdateNicknameRequest updateNicknameRequest = (UpdateNicknameRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(updateNicknameRequest.getAccount());
    }

    @Override
    public String getMethodName() {
        return "update_nickname";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return deal(baseRequest, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return deal(baseRequest, user);
    }

    private ServiceResult deal(BaseRequest baseRequest, User user) throws Exception {
        UpdateNicknameRequest updateNicknameRequest = (UpdateNicknameRequest) baseRequest;
        if (StringUtils.isEmpty(updateNicknameRequest.getAccount())
                || StringUtils.isEmpty(updateNicknameRequest.getNickname())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        if (!ValidateUtil.isValidNickname(updateNicknameRequest.getNickname())) {
            return ServiceResultUtil.illegal("昵称格式错误");
        }
        boolean exist = userBaseService.existNickname(updateNicknameRequest.getNickname());
        if (exist) {
            return ServiceResultUtil.illegal("昵称已存在");
        }
        boolean success = userBaseService.updateNickname(user.getId(),
                updateNicknameRequest.getNickname());
        if (success) {
            LogContext.instance().info("修改成功");
            return ServiceResultUtil.success();
        } else {
            LogContext.instance().error("修改失败");
            return ServiceResultUtil.serverError("修改失败");
        }
    }

}
