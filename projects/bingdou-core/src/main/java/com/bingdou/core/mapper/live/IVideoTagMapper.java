package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.VideoTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 17/4/4.
 */
public interface IVideoTagMapper {

    void addVideoTag(@Param("title") String title, @Param("tagPic") String tagPic, @Param("tagType") Integer tagType);

    Integer existVideoTag(@Param("title") String title);

    List<VideoTag> getAllTags();
}
