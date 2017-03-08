package com.bingdou.core.service.user.chatroom;


import com.bingdou.core.service.user.chatroom.methods.ChatUser;
import com.bingdou.core.service.user.chatroom.methods.Chatroom;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gaoshan on 17/3/1.
 */
public class RongCloud {

    private static ConcurrentHashMap<String, RongCloud> rongCloud = new ConcurrentHashMap<String,RongCloud>();

    public Chatroom chatroom;

    public ChatUser chatUser;

    private RongCloud(String appKey, String appSecret) {
        chatUser = new ChatUser(appKey, appSecret);
        chatroom = new Chatroom(appKey, appSecret);
    }

    public static RongCloud getInstance(String appKey, String appSecret) {
        if (null == rongCloud.get(appKey)) {
            rongCloud.putIfAbsent(appKey, new RongCloud(appKey, appSecret));
        }
        return rongCloud.get(appKey);
    }

}
