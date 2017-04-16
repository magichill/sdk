package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IReportShareMapper;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.model.live.ReportShare;
import com.bingdou.core.model.live.ShareUserRank;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/4/10.
 */
@Repository
public class ReportShareDao {

    @Autowired
    private IReportShareMapper reportShareMapper;


    public void addReportShare(ReportShare reportShare ){
        if(reportShare == null){
            LogContext.instance().error("增加分享日志失败,参数不正确");
        }
        reportShareMapper.addReportShare(reportShare);
    }

    public void updateReportShare(ReportShare reportShare ){
        if(reportShare == null){
            LogContext.instance().error("更新分享日志失败,参数不正确");
        }
        reportShareMapper.updateReportShare(reportShare);
    }

    public ReportShare getReportShare(Integer broadcasterId,Integer shareType,Integer mid){
        return reportShareMapper.getReportShare(broadcasterId,shareType,mid);
    }

    public List<ShareUserRank> getShareRankList(Integer broadcasterId, Integer start, Integer limit){
        return reportShareMapper.getShareRankList(broadcasterId,start,limit);
    }
}
