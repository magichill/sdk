package com.bingdou.core.service.user.chatroom;


import com.bingdou.core.service.user.chatroom.model.TokenResult;

/**
 * Created by gaoshan on 17/3/2.
 */
public class TestChatroom {

    public static void main(String[] args) throws Exception {

        String appKey = "uwd1c0sxug0u1";//替换成您的appkey
        String appSecret = "DQ28LXmrazw1Yu";//替换成匹配上面key的secret

        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);

        TokenResult userGetTokenResult = rongCloud.chatUser.getToken("userId1", "username", "http://www.rongcloud.cn/images/logo.png");
        System.out.println("getToken:  " + userGetTokenResult.toString());

//        ChatRoomInfo[] chatroomCreateChatRoomInfo = {new ChatRoomInfo("chatroomId1","chatroomName1" ), new ChatRoomInfo("chatroomId2","chatroomName2" ), new ChatRoomInfo("chatroomId3","chatroomName3" )};
//        CodeSuccessReslut chatroomCreateResult = rongCloud.chatroom.create(chatroomCreateChatRoomInfo);
//        System.out.println("create:  " + chatroomCreateResult.toString());
    }
}
