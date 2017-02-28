package com.bingdou.api.service;

import com.bingdou.api.constant.LiveType;
import com.bingdou.api.request.EntryRequest;
import com.bingdou.api.response.EntryResponse;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.EntryRoom;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.EntryDao;
import com.bingdou.core.service.IMethodService;
import com.bingdou.tools.JsonUtil;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/2/27.
 */
@Service
public class EntryService extends LiveBaseService implements IMethodService {

    @Autowired
    private EntryDao entryDao;

    @Override
    public String getMethodName() {
        return "report_entry_live";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        EntryRequest entryRequest = (EntryRequest) baseRequest;
        return dealEntry(request,entryRequest,user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        EntryRequest entryRequest = (EntryRequest) baseRequest;
        return dealEntry(request,entryRequest,user);
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        EntryRequest entryRequest = new EntryRequest();
        entryRequest.parseRequest(request);
        return entryRequest;
    }

    @Override
    public boolean checkUser() {
        return true;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        EntryRequest entryRequest = (EntryRequest) baseRequest;
        return userBaseService.getDetailByIdOrCpIdOrLoginName(String.valueOf(entryRequest.getUserId()));
    }

    private ServiceResult dealEntry(HttpServletRequest request, EntryRequest entryRequest, User user) throws Exception {
        if (StringUtils.isEmpty(entryRequest.getUserId()) || entryRequest.getLiveId() == null) {
            return ServiceResultUtil.illegal("请求参数错误");
        }
        Live live = getLiveInfo(entryRequest.getLiveId());
        if(live == null){
            return ServiceResultUtil.illegal("直播间不存在");
        }
        if(live.getLiveType() == LiveType.ENCODE.getIndex()){
            if(StringUtils.isEmpty(live.getPassword())){
                return ServiceResultUtil.illegal("观看密码为空");
            }else{
                if(!live.getPassword().equals(entryRequest.getPassword())){
                    return ServiceResultUtil.illegal("观看密码不正确");
                }
            }
        }
        EntryRoom entryRoom = entryDao.getEntryByUserIdAndLiveId(user.getId(),live.getId());
        int enterCount = 1;
        if(entryRoom == null){
            EntryRoom newEntry = new EntryRoom();
            newEntry.setLiveId(live.getId());
            newEntry.setUserId(user.getId());
            newEntry.setEnterCount(enterCount);
            entryDao.addEntry(newEntry);
        }else {
            enterCount = entryRoom.getEnterCount() + 1;
            entryRoom.setEnterCount(enterCount);
            entryDao.updateEntry(entryRoom);
        }

        EntryResponse response = new EntryResponse();
        response.setResult("1");
        response.setEnterCount(enterCount);
        JsonElement result = JsonUtil.bean2JsonTree(response);
        return ServiceResultUtil.success(result);
    }
}
