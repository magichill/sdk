package com.bingdou.core.service.live;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.ReportChatroom;
import com.bingdou.core.repository.live.ReportChatroomDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 17/4/10.
 */
@Service
public class ChatroomRecordService {

    @Autowired
    private ReportChatroomDao reportChatroomDao;

    public void addReportChatroom(User user, Live live, Integer audienceCount, Integer viewCount){
        LogContext.instance().info("增加直播间人数日志");
        ReportChatroom reportChatroom = new ReportChatroom();
        reportChatroom.setLiveId(live.getId());
        reportChatroom.setAudienceCount(audienceCount);
        reportChatroom.setViewCount(viewCount);
        reportChatroomDao.addReportChatroom(reportChatroom);
    }

    public void updateReportChatroom(ReportChatroom reportChatroom){
        LogContext.instance().info("更新直播间人数日志");
        if(reportChatroom == null)
            return;
        reportChatroomDao.updateReportChatroom(reportChatroom);
    }

    public ReportChatroom getReportChatroom(Integer liveId){
        LogContext.instance().info("获取直播间人数日志");
        return reportChatroomDao.getReportChatroom(liveId);
    }
}
