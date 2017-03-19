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
        User user = userBaseService.getUserDetailByAccount(updateNicknameRequest.getAccount());
        if (user == null)
            user = userBaseService.getDetailByIdOrCpIdOrLoginName(updateNicknameRequest.getAccount());
        return user;
    }

    @Override
    public String getMethodName() {
        return "update_user_profile";
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
        String nickName = "";
        if (StringUtils.isEmpty(updateNicknameRequest.getAccount())) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        if(StringUtils.isEmpty(updateNicknameRequest.getNickname())){
            nickName = user.getNickName();
        }else {
            if (!ValidateUtil.isValidNickname(updateNicknameRequest.getNickname())) {
                return ServiceResultUtil.illegal("昵称格式错误");
            }
            nickName = updateNicknameRequest.getNickname();
            boolean exist = userBaseService.existNickname(updateNicknameRequest.getNickname());
            if (exist) {
                return ServiceResultUtil.illegal("昵称已存在");
            }
        }

        boolean success = userBaseService.updateNickname(user.getId(),
                nickName,
                updateNicknameRequest.getGender(),
                updateNicknameRequest.getSignature(),
                updateNicknameRequest.getAvatar());
        if (success) {
            LogContext.instance().info("修改成功");
            return ServiceResultUtil.success();
        } else {
            LogContext.instance().error("修改失败");
            return ServiceResultUtil.serverError("修改失败");
        }
    }

}
