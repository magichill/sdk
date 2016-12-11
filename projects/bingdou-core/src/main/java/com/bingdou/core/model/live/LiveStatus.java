package com.bingdou.core.model.live;

/**
 * Created by gaoshan on 16/12/3.
 */
public enum LiveStatus {

    ONLINE(1),OFFLINE(2),DELETE(0);

    private int index;

    LiveStatus(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
