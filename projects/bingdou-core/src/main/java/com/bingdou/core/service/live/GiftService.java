package com.bingdou.core.service.live;

import com.bingdou.core.model.User;
import com.bingdou.core.model.live.Gift;
import com.bingdou.core.model.live.Live;
import com.bingdou.core.repository.live.GiftDao;
import com.bingdou.core.repository.live.SendGiftDao;
import com.bingdou.tools.LogContext;
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
}
