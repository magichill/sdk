package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.ReportChatroom;
import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/4/10.
 */
public interface IReportChatroomMapper {

    void addReportChatroom(ReportChatroom reportChatroom);

    void updateReportChatroom(ReportChatroom reportChatroom);

    ReportChatroom getReportChatroom(@Param("liveId") Integer liveId);
}
