package com.bingdou.userserver.constant;

/**
 * Created by gaoshan on 17/4/16.
 */
public enum BroadcasterType {

    RECOMMEND("recommend",0),POPULAR("popular",1),ACTIVE("active",2);

    private int index;
    private String name;

    BroadcasterType(String name, int index){
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

    public static BroadcasterType getByIndex(int index) {
        for (BroadcasterType broadcasterType : BroadcasterType.values()) {
            if (index == broadcasterType.getIndex())
                return broadcasterType;
        }
        return null;
    }
}
