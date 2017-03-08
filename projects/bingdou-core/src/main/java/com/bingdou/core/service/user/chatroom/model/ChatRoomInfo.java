package com.bingdou.core.service.user.chatroom.model;

import com.bingdou.tools.JsonUtil;

/**
 * Created by gaoshan on 17/3/1.
 */
public class ChatRoomInfo {

    // 聊天室Id。
    String id;
    // 聊天室名称。
    String name;

    public ChatRoomInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 设置id
     *
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取id
     *
     * @return String
     */
    public String getId() {
        return id;
    }

    /**
     * 设置name
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this, ChatRoomInfo.class);
    }
}
