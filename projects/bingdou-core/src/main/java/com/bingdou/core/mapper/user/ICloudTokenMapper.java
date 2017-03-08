package com.bingdou.core.mapper.user;

import com.bingdou.core.model.CloudToken;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/3/4.
 */
public interface ICloudTokenMapper {

    int existCloudToken(@Param("userId") Integer userId);

    CloudToken getCloudToken(@Param("userId") Integer userId);

    void insertCloudToken(@Param("userId") Integer userId, @Param("token") String token,
                          @Param("device") String device, @Param("requestSource") String requestSource);

    int updateCloudToken(@Param("userId") Integer userId, @Param("token") String token,
                         @Param("device") String device, @Param("requestSource") String requestSource);
}
