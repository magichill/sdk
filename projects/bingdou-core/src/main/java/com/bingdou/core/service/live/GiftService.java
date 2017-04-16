package com.bingdou.core.service.live;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.ContributeUserRank;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.GiftDao;
import com.bingdou.core.repository.live.SendGiftDao;
import com.bingdou.tools.LogContext;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaoshan on 17/3/27.
 */
@Service
public class GiftService {

    @Autowired
    private GiftDao giftDao;

    @Autowired
    private SendGiftDao sendGiftDao;

    public List<Gift> getGiftList(){
        LogContext.instance().info("获取礼物列表");
        return giftDao.getGiftList();
    }

    public Gift getGiftInfo(Integer giftId){
        LogContext.instance().info("获取礼物详情");
        if(giftId == null || giftId <=0){
            return null;
        }
        return giftDao.getGiftById(giftId);
    }

    public void recordSendGift(Live live, User user, Gift gift,Integer sendNum){
        LogContext.instance().info("记录赠送礼物");
        sendGiftDao.addSendGift(live,user,gift,sendNum);
    }

    public boolean addGift(Gift gift){
        LogContext.instance().info("新增礼物");
        return giftDao.addGift(gift);
    }

    public boolean updateGift(Gift gift){
        LogContext.instance().info("更新礼物");
        return giftDao.updateGift(gift);
    }

    public boolean deleteGift(Integer id){
        LogContext.instance().info("删除礼物");
        return giftDao.deleteGift(id);
    }

    public Integer getReceiveMoney(User user){
        Integer receiveMoney = sendGiftDao.getReceiveMoneyByHostId(user);
        if(receiveMoney == null)
            return 0;
        return receiveMoney;
    }

    public Integer getSendMoney(User user){
        Integer sendMoney = sendGiftDao.getSendMoneByMid(user);
        if(sendMoney == null)
            return 0;
        return sendMoney;
    }

    public List<ContributeUserRank> getContributeList(User user, Integer start, Integer limit){
        if(user == null){
            return Lists.newArrayList();
        }
        return sendGiftDao.getSendUserList(user,start,limit);
    }
}
