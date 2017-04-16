package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.ISendGiftMapper;
import com.bingdou.core.model.User;
import com.bingdou.core.model.live.ContributeUserRank;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.model.live.Live;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/3/27.
 */
@Repository
public class SendGiftDao {

    @Autowired
    private ISendGiftMapper sendGiftMapper;

    public void addSendGift(Live live, User user,Gift gift,Integer sendNum){
        LogContext.instance().info("增加赠送礼物记录");
        if(live == null || user == null
                || gift == null || sendNum ==null || sendNum <=0){
            LogContext.instance().error("赠送礼物失败,参数不正确");
        }
        sendGiftMapper.addSendGift(live.getId(),user.getId(),gift.getId(),gift.getPrice()*sendNum,live.getMid(),sendNum);
    }


    public Integer getReceiveMoneyByHostId(User user){
        LogContext.instance().info("获取收到礼物冰豆数");
        return sendGiftMapper.getReceiveMoneyByHostId(user.getId());
    }

    public Integer getSendMoneByMid(User user){
        LogContext.instance().info("获取赠送礼物冰豆数");
        return sendGiftMapper.getSendMoneyByMid(user.getId());
    }

    public List<ContributeUserRank> getSendUserList(User user, Integer start, Integer limit){
        LogContext.instance().info("获取赠送礼物用户排行");
        return sendGiftMapper.getSendUserList(user.getId(),start,limit);
    }

}
