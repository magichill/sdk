package com.bingdou.api.request;

import com.bingdou.core.helper.BaseRequest;
import com.bingdou.tools.JsonUtil;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoshan on 17/3/28.
 */
public class AddGiftRequest extends BaseRequest{

    @SerializedName("gift_title")
    private String giftTitle;

    @SerializedName("gift_pic")
    private String giftPic;

    @SerializedName("price")
    private Integer price;  //单位 冰豆

    @SerializedName("gift_type")
    private Integer giftType;

    @SerializedName("gift_desc")
    private String giftDesc;


    @Override
    protected String getLoggerName() {
        return "AddGiftRequest";
    }

    @Override
    protected BaseRequest setFields(String requestString) {
        AddGiftRequest request = JsonUtil.jsonStr2Bean(requestString, AddGiftRequest.class);
        this.giftTitle = request.getGiftTitle();
        this.giftDesc = request.getGiftDesc();
        this.giftType = request.getGiftType();
        this.giftPic = request.getGiftPic();
        this.price = request.getPrice();
        return request;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getGiftType() {
        return giftType;
    }

    public void setGiftType(Integer giftType) {
        this.giftType = giftType;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }
}
