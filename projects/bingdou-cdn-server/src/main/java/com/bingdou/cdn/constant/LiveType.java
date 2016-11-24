package com.bingdou.cdn.constant;

/**
 * Created by gaoshan on 16-11-18.
 * 直播类型枚举
 */
public enum LiveType {

    LIVE("live",1),RECORD("record",2),APPOINT("appoint",3);

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
