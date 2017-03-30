package com.bingdou.core.repository.live;

import com.bingdou.core.mapper.live.IGiftMapper;
import com.bingdou.core.model.live.Gift;
import com.bingdou.tools.LogContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gaoshan on 17/3/27.
 */
@Repository
public class GiftDao {

    @Autowired
    private IGiftMapper giftMapper;

    public List<Gift> getGiftList(){
        return giftMapper.getEnableGiftList();
    }

    public Gift getGiftById(Integer giftId){
        return giftMapper.getGiftById(giftId);
    }

    public boolean addGift(Gift gift) {
        giftMapper.addGift(gift);
        return true;
    }

    public boolean updateGift(Gift gift){
        if(gift == null){
            return false;
        }
        giftMapper.updateGift(gift);
        return true;
    }

    public boolean deleteGift(Integer id){
        if(id == null){
            return false;
        }
        giftMapper.deleteGift(id);
        return true;
    }

}
