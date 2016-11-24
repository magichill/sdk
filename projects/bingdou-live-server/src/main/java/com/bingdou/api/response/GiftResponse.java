package com.bingdou.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 16-11-10.
 */
public class GiftResponse {

    @SerializedName("gift_id")
    private int giftId = 0;

    @SerializedName("gift_title")
    private String giftTitle = "";

    @SerializedName("gift_pic")
    private String giftPic;

    @SerializedName("gift_type")
    private int giftType;

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getGiftTitle() {
        return giftTitle;
    }

    public void setGiftTitle(String giftTitle) {
        this.giftTitle = giftTitle;
    }

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
    }

    public int getGiftType() {
        return giftType;
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }
}
