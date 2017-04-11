package com.bingdou.api.constant;

/**
 * Created by gaoshan on 17/4/10.
 */
public enum ShareChannel {

    CIRCLE("circle",1),FRIEND("friend",2);

    private int index;
    private String name;

    ShareChannel(String name, int index){
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

    public static ShareChannel getByIndex(int index) {
        for (ShareChannel shareChannel : ShareChannel.values()) {
            if (index == shareChannel.getIndex())
                return shareChannel;
        }
        return null;
    }
}
