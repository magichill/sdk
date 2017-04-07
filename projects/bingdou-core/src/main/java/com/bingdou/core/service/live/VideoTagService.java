package com.bingdou.core.service.live;

import com.bingdou.core.model.live.VideoTag;
import com.bingdou.core.repository.live.VideoTagDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaoshan on 17/4/4.
 */
@Service
public class VideoTagService {

    @Autowired
    private VideoTagDao videoTagDao;

    public void addVideoTag(VideoTag videoTag){
        LogContext.instance().info("插入视频标签");
        videoTagDao.addVideoTag(videoTag);
    }

    public boolean existTag(String title){
        LogContext.instance().info("检查是否有重复标签");
        Integer existTag = videoTagDao.existVideoTag(title);
        if(existTag > 0){
            LogContext.instance().info("已有该标签");
            return true;
        }else{
            return false;
        }
    }

    public List<VideoTag> getVideoTags(){
        LogContext.instance().info("获取标签列表");
        return videoTagDao.getVideoTags();
    }
}
