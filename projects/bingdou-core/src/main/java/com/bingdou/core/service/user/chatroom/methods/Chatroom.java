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

    /**
     * 添加禁言聊天室成员方法（在 App 中如果不想让某一用户在聊天室中发言时，可将此用户在聊天室中禁言，被禁言用户可以接收查看聊天室中用户聊天信息，但不能发送消息.）
     *
     * @param  userId:用户 Id。（必传）
     * @param  chatroomId:聊天室 Id。（必传）
     * @param  minute:禁言时长，以分钟为单位，最大值为43200分钟。（必传）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessReslut addGagUser(String userId, String chatroomId, String minute) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }

        if (chatroomId == null) {
            throw new IllegalArgumentException("Paramer 'chatroomId' is required");
        }

        if (minute == null) {
            throw new IllegalArgumentException("Paramer 'minute' is required");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId.toString(), UTF8));
        sb.append("&minute=").append(URLEncoder.encode(minute.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }

        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, appKey, appSecret, "/chatroom/user/gag/add.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);

        return (CodeSuccessReslut) GsonUtil.fromJson(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

    /**
     * 移除禁言聊天室成员方法
     *
     * @param  userId:用户 Id。（必传）
     * @param  chatroomId:聊天室Id。（必传）
     *
     * @return CodeSuccessResult
     **/
    public CodeSuccessReslut rollbackGagUser(String userId, String chatroomId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }

        if (chatroomId == null) {
            throw new IllegalArgumentException("Paramer 'chatroomId' is required");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }

        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, appKey, appSecret, "/chatroom/user/gag/rollback.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);

        return (CodeSuccessReslut) GsonUtil.fromJson(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }
}
