package com.bingdou.cdn.constant;


/**
 * Created by gaoshan on 16-11-13.
 */
public class UpYunConstant {

    public static final String API_URL = "https://api.upyun.com";

    public static final String ACCESS_TOKEN = "20433004-e709-4ef2-9a05-cac8c9fcc163";

    public static final String REQUEST_HEADER = "Bearer "+ ACCESS_TOKEN;

    public static final String CREATE_LIVE_URL = API_URL+"/srs/app";  //创建或修改直播接入点接口,POST方式请求



    public static final String BUCKET_NAME = "bingdou";

    public static final String UPYUN_PUSH_URL = "rtmp://upyunpublish.bingdou.tv/";
    public static final String UPYUN_PLAY_URL = "rtmp://upyunplay.bingdou.tv/";

    public static final String UPYUN_APP_NAME = "live";
    public static final String FLV_SUFFIX = ".flv";
    public static final String M3U8_SUFFIX = ".m3u8";


}
