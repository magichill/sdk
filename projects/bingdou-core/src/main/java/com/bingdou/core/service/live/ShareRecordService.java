package com.bingdou.core.service.live;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.ReportShare;
import com.bingdou.core.repository.live.ReportShareDao;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaoshan on 17/4/10.
 */
@Service
public class ShareRecordService {

    @Autowired
    private ReportShareDao reportShareDao;

    public void addReportShare(User user,Live live,Integer shareType,Integer shareChannel,Integer broadercasterId){
        LogContext.instance().info("增加分享日志");
        ReportShare reportShare = new ReportShare();
        reportShare.setMid(user.getId());
        if(live != null) {
            reportShare.setLiveId(live.getId());

        }
        reportShare.setBroadcasterId(broadercasterId);
        reportShare.setShareType(shareType);
        reportShare.setShareChannel(shareChannel);
        reportShareDao.addReportShare(reportShare);
    }

    public void updateReportShare(ReportShare reportShare){
        LogContext.instance().info("更新分享日志");
        if(reportShare == null)
            return;
        reportShare.setShareCount(reportShare.getShareCount()+1);
        reportShareDao.updateReportShare(reportShare);
    }

    public ReportShare getReportShare(Integer broadcasterId,Integer shareType,Integer mid){
        LogContext.instance().info("获取分享日志");
        return reportShareDao.getReportShare(broadcasterId,shareType,mid);
    }
}
