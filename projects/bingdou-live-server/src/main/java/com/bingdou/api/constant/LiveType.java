package com.bingdou.api.constant;

/**
 * Created by gaoshan on 16-11-18.
 * 直播类型枚举
 */
public enum LiveType {

    //视频的类型 1（免费）；2（加密）；3（普通付费）；4（分销付费）
    FREE("free",1),ENCODE("encode",2),PAY("pay",3),CHANNEL_PAY("channel_pay",4);

    private int index;
    private String name;

    LiveType(String name, int index){
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

    public static LiveType getByIndex(int index) {
        for (LiveType liveType : LiveType.values()) {
            if (index == liveType.getIndex())
                return liveType;
        }
        return null;
    }
}
