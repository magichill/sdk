package com.bingdou.cdn.constant;

import com.bingdou.tools.DateUtil;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by gaoshan on 16-11-13.
 */
public class CncConstant {

    public static final String CNC_PUSH_URL = "rtmp://cncpublish.bingdou.tv/";
    public static final String CNC_PLAY_URL = "rtmp://cncplay.bingdou.tv/";
    public static final String CNC_H5_PLAY_URL = "http://cnchls.bingdou.tv/";

    public static final String CNC_APP_NAME = "live";

    public static final String VIEWER_QUERY_URL = "http://qualiter.wscdns.com/api/playerCount.jsp?n={0}&r={1}&k={2}&u={3}";

    public static final String CNC_PORTAL = "bingdou";
    public static final String CNC_KEY = "ACEA1635C73D25A";
    public static final String CNC_DOMAIN = "cncpublish.bingdou.tv";


    public static void main(String[] args) throws Exception {
        String randomNum = DateUtil.format(new Date(),DateUtil.DHHMMSSSSS);
        String key = DigestUtils.md5Hex(randomNum+CncConstant.CNC_KEY);
        String url = MessageFormat.format(CncConstant.VIEWER_QUERY_URL,CncConstant.CNC_PORTAL, randomNum,key,CncConstant.CNC_DOMAIN);
        String result = HttpClientUtil.doGetHttpClient("查询直播在线人数",url,3000,3000);
        System.out.println(result);
    }
}
