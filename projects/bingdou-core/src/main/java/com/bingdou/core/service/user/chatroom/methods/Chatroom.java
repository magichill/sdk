package com.bingdou.core.service.user.chatroom.methods;


import com.bingdou.core.service.user.chatroom.model.ChatRoomInfo;
import com.bingdou.core.service.user.chatroom.model.CodeSuccessReslut;
import com.bingdou.core.service.user.chatroom.util.GsonUtil;
import com.bingdou.core.service.user.chatroom.util.HostType;
import com.bingdou.core.service.user.chatroom.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by gaoshan on 17/3/2.
 */
public class Chatroom {

    private static final String UTF8 = "UTF-8";
    private String appKey;
    private String appSecret;

    public Chatroom(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;

    }


    /**
     * 创建聊天室方法
     *
     * @param  chatRoomInfo:id:要创建的聊天室的id；name:要创建的聊天室的name。（必传）
     *
     * @return CodeSuccessReslut
     **/
    public CodeSuccessReslut create(ChatRoomInfo[] chatRoomInfo) throws Exception {
        if (chatRoomInfo == null) {
            throw new IllegalArgumentException("Paramer 'chatRoomInfo' is required");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i< chatRoomInfo.length; i++) {
            ChatRoomInfo child  = chatRoomInfo[i];
            sb.append("&chatroom["+child.getId()+"]=").append(URLEncoder.encode(child.getName(), UTF8));
        }

        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }

        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, appKey, appSecret, "/chatroom/create.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);

        return (CodeSuccessReslut) GsonUtil.fromJson(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }
}
