package com.bingdou.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 礼物列表响应
 * Created by gaoshan on 16-11-4.
 */
public class GiftListResponse {

    @SerializedName("gift_info_list")
    private List<GiftResponse> giftInfoList;

    public List<GiftResponse> getGiftInfoList() {
        return giftInfoList;
    }

    public void setGiftInfoList(List<GiftResponse> giftInfoList) {
        this.giftInfoList = giftInfoList;
    }
}
