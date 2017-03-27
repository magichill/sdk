package com.bingdou.core.mapper.live;

import com.bingdou.core.model.live.Gift;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by gaoshan on 16-11-9.
 */
public interface IGiftMapper {

    Gift getGiftById(@Param("giftId") int giftId);

    List<Gift> getEnableGiftList();

    void addGift(@Param("giftTitle") String giftTitle, @Param("giftPic") String giftPic,
                 @Param("giftType") int gift_type);
}
