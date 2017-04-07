package com.bingdou.api.service;

import com.bingdou.api.request.AddTagRequest;
import com.bingdou.core.helper.BaseRequest;
import com.bingdou.core.helper.ServiceResult;
import com.bingdou.core.helper.ServiceResultUtil;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.VideoTag;
import com.bingdou.core.service.IMethodService;
import com.bingdou.core.service.live.VideoTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 17/4/4.
 */
@Service
public class AddLiveTagService extends LiveBaseService implements IMethodService {

    @Autowired
    private VideoTagService videoTagService;

    @Override
    public String getMethodName() {
        return "add_live_tag";
    }

    @Override
    public ServiceResult execute4Server(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        AddTagRequest addTagRequest = (AddTagRequest) baseRequest;
        return dealAddTag(addTagRequest);
    }

    @Override
    public ServiceResult execute4Client(HttpServletRequest request, BaseRequest baseRequest, User user) throws Exception {
        return null;
    }

    @Override
    public BaseRequest getBaseRequest(HttpServletRequest request) throws Exception {
        AddTagRequest addTagRequest = new AddTagRequest();
        addTagRequest.parseRequest(request);
        return addTagRequest;
    }

    @Override
    public boolean checkUser() {
        return false;
    }

    @Override
    public User getUser(BaseRequest baseRequest) {
        return null;
    }

    private ServiceResult dealAddTag(AddTagRequest addTagRequest){
        if(StringUtils.isEmpty(addTagRequest.getTagPic()) ||
                StringUtils.isEmpty(addTagRequest.getTagTitle()) ||
                addTagRequest.getTagType() == null){
            return ServiceResultUtil.illegal("参数不正确");
        }
        VideoTag videoTag = new VideoTag();
        videoTag.setTagType(addTagRequest.getTagType());
        videoTag.setTagPic(addTagRequest.getTagPic());
        videoTag.setTitle(addTagRequest.getTagTitle());
        videoTagService.addVideoTag(videoTag);
        return ServiceResultUtil.success();
    }
}
