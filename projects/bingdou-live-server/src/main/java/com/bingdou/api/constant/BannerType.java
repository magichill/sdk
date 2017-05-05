package com.bingdou.api.constant;

/**
 * Created by gaoshan on 17/4/22.
 */
public enum BannerType {

    //banner类型
    BROADCASTER("broadcaster://",1),REPLAY("playback://",2),VIDEO("video://",3),ADVERTISE("advertise://",4);

    private int index;
    private String name;

    BannerType(String name, int index){
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static BannerType getByIndex(int index) {
        for (BannerType bannerType : BannerType.values()) {
            if (index == bannerType.getIndex())
                return bannerType;
        }
        return null;
    }
}
