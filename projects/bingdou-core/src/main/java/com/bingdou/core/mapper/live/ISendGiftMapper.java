package com.bingdou.core.mapper.live;

import org.apache.ibatis.annotations.Param;

/**
 * Created by gaoshan on 17/3/27.
 */
public interface ISendGiftMapper {

    void addSendGift(@Param("liveId") int liveId, @Param("mid") Integer mid,
                     @Param("giftId") Integer giftId, @Param("sendMoney") Integer sendMoney,
                     @Param("hostId") Integer hostId,@Param("sendNum") Integer sendNum);

    Integer getSendMoneyByMid(@Param("mid") Integer mid);

    Integer getReceiveMoneyByHostId(@Param("hostId") Integer hostId);
}
