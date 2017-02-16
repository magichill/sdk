package com.bingdou.core.model;

/**
 * Created by gaoshan on 17/2/14.
 */
public class UserStat {

    private int liveCount;
    private int likeCount;
    private int followers;
    private long expValue;
    private long incomeLoyalty;
    private long consumeLoyalty;
    private String officialTag;
    private String otherTags;

    public int getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public long getExpValue() {
        return expValue;
    }

    public void setExpValue(long expValue) {
        this.expValue = expValue;
    }

    public long getIncomeLoyalty() {
        return incomeLoyalty;
    }

    public void setIncomeLoyalty(long incomeLoyalty) {
        this.incomeLoyalty = incomeLoyalty;
    }

    public long getConsumeLoyalty() {
        return consumeLoyalty;
    }

    public void setConsumeLoyalty(long consumeLoyalty) {
        this.consumeLoyalty = consumeLoyalty;
    }

    public String getOfficialTag() {
        return officialTag;
    }

    public void setOfficialTag(String officialTag) {
        this.officialTag = officialTag;
    }

    public String getOtherTags() {
        return otherTags;
    }

    public void setOtherTags(String otherTags) {
        this.otherTags = otherTags;
    }
}
