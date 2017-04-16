package com.bingdou.core.mapper.live;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.ReportShare;
import com.bingdou.core.model.live.ShareUserRank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 17/4/10.
 */
public interface IReportShareMapper {

    void addReportShare(ReportShare reportShare);

    void updateReportShare(ReportShare reportShare);

    ReportShare getReportShare(@Param("broadcasterId") Integer broadcasterId,
                               @Param("shareType") Integer shareType,
                               @Param("mid") Integer mid);
    List<ShareUserRank> getShareRankList(@Param("broadcasterId") Integer broadcasterId, @Param("start") Integer start,
                                         @Param("limit") Integer limit);
}
