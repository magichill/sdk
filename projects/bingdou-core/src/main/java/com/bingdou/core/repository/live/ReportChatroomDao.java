package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IReportChatroomMapper;
import com.bingdou.core.model.live.ReportChatroom;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by gaoshan on 17/4/10.
 */
@Repository
public class ReportChatroomDao {

    @Autowired
    private IReportChatroomMapper reportChatroomMapper;


    public void addReportChatroom(ReportChatroom reportChatroom ){
        if(reportChatroom == null){
            LogContext.instance().error("增加直播间人数日志失败,参数不正确");
        }
        reportChatroomMapper.addReportChatroom(reportChatroom);
    }

    public void updateReportChatroom(ReportChatroom reportChatroom ){
        if(reportChatroom == null){
            LogContext.instance().error("更新直播间人数日志失败,参数不正确");
        }
        reportChatroomMapper.updateReportChatroom(reportChatroom);
    }

    public ReportChatroom getReportChatroom(Integer liveId){
        return reportChatroomMapper.getReportChatroom(liveId);
    }
}
