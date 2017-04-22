package com.bingdou.userserver.response;

import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;

/**
 * Created by gaoshan on 17/2/21.
 */
public class WeixinFastLoginAuthResult extends FastLoginAuthResult {

    private String channelId;

    @Override
    protected void parse(JsonObject authResult) throws Exception {
        if (authResult == null) {
            throw new Exception("转换微信授权结果JSON失败");
        }
        this.openId = authResult.get("open_id").getAsString();
        this.channelId = authResult.get("channel_id").getAsString();
        this.unionId = authResult.get("union_id").getAsString();
        this.authCode = authResult.get("access_token").getAsString();
    }

    @Override
    protected boolean isAuthSuccess() {
        if(StringUtils.isEmpty(getOpenId()) || StringUtils.isEmpty(getChannelId())
                || StringUtils.isEmpty(getUnionId())
                || StringUtils.isEmpty(getAuthCode()))
            return false;
        return true;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
