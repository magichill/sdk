package com.bingdou.core.helper;

/**
 * Created by gaoshan on 16-10-28.
 */

import com.bingdou.core.constants.Constants;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.constants.EncodeMethod;
import com.bingdou.tools.constants.KeyGroup;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求类父类
 */
public abstract class BaseRequest {

    /**
     * 设备信息
     */
    @SerializedName("device_info")
    protected DeviceInfo deviceInfo;
    /**
     * 其他信息
     */
    @SerializedName("other_info")
    protected OtherInfo otherInfo;

    /**
     * 应用ID
     */
    @SerializedName("app_id")
    protected String appId;
    /**
     * 令牌
     */
    @SerializedName("token")
    protected String token;
    /**
     * 渠道号(服务器端)
     */
    @SerializedName("channel")
    protected String channel;
    /**
     * 请求来源
     */
    private String requestSource;
    /**
     * HTTP请求对象
     */
    private HttpServletRequest request;
    /**
     * 请求方法名
     */
    private String method;

    /**
     * 解析请求对象
     */
    public void parseRequest(HttpServletRequest request) throws Exception {
        this.request = request;
        LogContext logContext = LogContext.instance();
        logContext.clearLoggerName();
        logContext.setLoggerName(getLoggerName());
        logContext.info("---------------请求开始---------------");
        try {
            this.method = request.getParameter("method");
            String param = request.getParameter("param");
            String sign = request.getParameter("sign");
            this.requestSource = request.getParameter("request_source_index");
            if (StringUtils.isEmpty(param)) {
                logContext.error("请求参数为空");
                throw new Exception("请求参数为空");
            }
            KeyGroup keyGroup = (KeyGroup) request.getAttribute(Constants.REQUEST_KEY_GROUP_NAME);
            String requestString;
            if (EncodeMethod.AES.equals(keyGroup.getEncodeMethod())) {
                requestString = CodecUtils.aesDecode(param, keyGroup);
            } else {
                logContext.error("非法加密方法");
                throw new Exception("非法加密方法");
            }
            logContext.info("请求内容(解密):" + requestString.replaceAll("\"[a-zA-Z]*[_]?password\"[\\s]*?:[\\s]*?\"[\\w\\W]*?\"",
                    "\"(*)password\": \"******\""));
            String mySign = CodecUtils.getMySign(requestString, keyGroup);
            if (mySign.equals(sign)) {
                BaseRequest baseRequest = setFields(requestString);
                this.appId = baseRequest.getAppId();
                this.deviceInfo = baseRequest.getDeviceInfo();
                this.otherInfo = baseRequest.getOtherInfo();
                this.token = baseRequest.getToken();
                this.channel = baseRequest.getChannel();
            } else {
                logContext.error("签名错误");
                throw new Exception("签名错误");
            }
        } catch (Exception e) {
            logContext.error(e, "转换请求内容错误");
            throw e;
        }
    }

    /**
     * 日志名称
     */
    protected abstract String getLoggerName();

    /**
     * 设置子类请求字段
     */
    protected abstract BaseRequest setFields(String requestString);

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public OtherInfo getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(OtherInfo otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRequestSource() {
        return this.requestSource;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String getMethod() {
        return this.method;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
