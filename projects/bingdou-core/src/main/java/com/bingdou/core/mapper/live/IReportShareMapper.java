package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.ReportShare;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/4/10.
 */
public interface IReportShareMapper {

    void addReportShare(ReportShare reportShare);

    void updateReportShare(ReportShare reportShare);

    ReportShare getReportShare(@Param("broadcasterId") Integer broadcasterId,
                               @Param("shareType") Integer shareType,
                               @Param("mid") Integer mid);

}
