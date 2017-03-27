package com.bingdou.core.mapper.live;

import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/3/20.
 */
public interface IConsumeMapper {

    void addConsumeRecord(@Param("liveId") int liveId, @Param("mid") Integer mid,
                 @Param("price") Integer price,@Param("hostId") Integer hostId);

    Integer existRecord(@Param("liveId") int liveId, @Param("mid") Integer mid);
}
