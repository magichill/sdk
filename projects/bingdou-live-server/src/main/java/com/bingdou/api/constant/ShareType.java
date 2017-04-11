package com.bingdou.api.constant;

/**
 * Created by gaoshan on 17/4/10.
 */
public enum ShareType {

    BROADCASTER("broadcaster",1),VIDEO("video",2);

    private int index;
    private String name;

    ShareType(String name, int index){
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

    public static ShareType getByIndex(int index) {
        for (ShareType shareType : ShareType.values()) {
            if (index == shareType.getIndex())
                return shareType;
        }
        return null;
    }
}
