package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IVideoTagMapper;
import com.bingdou.core.model.live.VideoTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/4/4.
 */
@Repository
public class VideoTagDao {

    @Autowired
    private IVideoTagMapper videoTagMapper;

    public void addVideoTag(VideoTag videoTag){
        videoTagMapper.addVideoTag(videoTag.getTitle(),videoTag.getTagPic(),videoTag.getTagType());
    }

    public Integer existVideoTag(String title){
        return videoTagMapper.existVideoTag(title);
    }

    public List<VideoTag> getVideoTags(){
        return videoTagMapper.getAllTags();
    }
}
