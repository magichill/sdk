package com.bingdou.core.model.live;

/**
 * Created by gaoshan on 17/4/15.
 */
public class ContributeUserRank {

    /**
     * 主键id（mid）
     */
    private int id;

    /**
     * 性别
     */
    private int gender = -1;

    /**
     * 用户头像
     */
    private String avatar = "http://o8ov5bkvs.bkt.clouddn.com/1159184461660189";

    /**
     * 签名
     */
    private String signature;

    private String cpId;

    private String nickName;


    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
