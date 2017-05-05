package com.bingdou.core.model.live;

/**
 * Created by gaoshan on 16/12/3.
 */
public enum LiveStatus {

    //视频状态 0（预告）；1（直播）；2（回放）；-1（删除）
    REVIEW("review",0),LIVE("live",1),REPLAY("replay",2),DEL_LIVE("del_live",-1);

    private int index;
    private String name;

    LiveStatus(String name, int index){
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

    public static LiveStatus getByIndex(int index) {
        for (LiveStatus liveStatus : LiveStatus.values()) {
            if (index == liveStatus.getIndex())
                return liveStatus;
        }
        return null;
    }
}
