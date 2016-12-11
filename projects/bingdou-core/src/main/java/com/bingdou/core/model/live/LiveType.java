package com.bingdou.core.model.live;

/**
 * Created by gaoshan on 16/12/3.
 */
public enum LiveType {

    LIVE(1),RECORD(2);

    private int index;

    LiveType(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
