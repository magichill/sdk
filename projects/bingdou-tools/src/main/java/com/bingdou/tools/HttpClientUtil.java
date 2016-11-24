package com.bingdou.tools;

import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 */
@SuppressWarnings("Duplicates")
public class HttpClientUtil {

    private static String[] appConfigFiles = {"httpclient-config.xml"};
    private static Properties prop;
    private static HttpClient httpClient;
    private static final String GET_METHOD = "Get Method";
    private static final String POST_METHOD = "Post Method";
    private static final String PUT_METHOD = "Put Method";
    private static final String UTF_8 = "UTF-8";
    private static final int DEFAULT_TIMEOUT_FLAG = -1;

    private static Logger logger = LogManager.getLogger(CommonLoggerNameConstants.HTTP_CLIENT_LOGGER);

    private HttpClientUtil() {
    }

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext(appConfigFiles);
        httpClient = (HttpClient) context.getBean("sdkHttpClient");
        try {
            prop = PropertiesLoaderUtils.loadAllProperties("httpclient.properties");
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static String doGetHttpClient(String bizName, String url) throws Exception {
        return doGetHttpClient(bizName, url, DEFAULT_TIMEOUT_FLAG, DEFAULT_TIMEOUT_FLAG);
    }

    public static String doGetHttpClient(String bizName, String url, int connectionTimeout, int readTimeout) throws Exception {
        return doGetHttpClient(bizName, url, null, null, connectionTimeout, readTimeout);
    }

    public static String doGetHttpClient(String bizName, String url, Map<String, String> paramMap, Map<String, String> headerMap,
                                         int connectionTimeout, int readTimeout) throws Exception {
        String uuid = CodecUtils.getRequestUUID();
        logger.info(uuid + " - " + bizName + "请求开始 - " + GET_METHOD + " - " + url);
        long startTime = System.currentTimeMillis();
        String result = null;
        GetMethod method = new GetMethod(url);
        try {
            if (paramMap != null && !paramMap.isEmpty())
                method.setQueryString(URIUtil.encodeQuery(parseRequestQueryString(paramMap, uuid)));
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    method.setRequestHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info(uuid + " - header参数 - " + headerLogStr);
            }
            method.getParams().setContentCharset(UTF_8);
            setTimeout(connectionTimeout, readTimeout);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                byte[] bytes = method.getResponseBody();
                String charset = method.getResponseCharSet();
                result = new String(bytes, Charset.forName(charset));
            } else {
                throw new Exception("返回码不是200(" + method.getStatusCode() + ")");
            }
            writeResultLog(bizName, result, method.getStatusCode(), uuid);
        } catch (Exception e) {
            logger.error(uuid + " - " + bizName + "请求异常", e);
            throw e;
        } finally {
            method.releaseConnection();
            writeTimeLog(bizName, startTime, uuid);
        }
        return result;
    }

    public static String doPostStringHttpClient(String bizName, String url, String param,
                                                int connectionTimeout, int readTimeout) throws Exception {
        return doPostByContentTypeHttpClient(bizName, url, param, "application/x-www-form-urlencoded",
                connectionTimeout, readTimeout);
    }

    public static String doPostJsonOrXmlHttpClient(String bizName, String url, String param, boolean isXml,
                                                   int connectionTimeout, int readTimeout) throws Exception {
        String contentType = "application/json";
        if (isXml) {
            contentType = "text/xml";
        }
        return doPostByContentTypeHttpClient(bizName, url, param, contentType,
                connectionTimeout, readTimeout);
    }


    public static String doPostByContentTypeHttpClient(String bizName, String url, String param, String contentType,
                                                       int connectionTimeout, int readTimeout) throws Exception {
        String uuid = CodecUtils.getRequestUUID();
        logger.info(uuid + " - " + bizName + "请求开始 - " + POST_METHOD + " - " + url);
        long startTime = System.currentTimeMillis();
        String result = null;
        PostMethod method = new PostMethod(url);
        try {
            logger.info(uuid + " - xml/json参数 - " + param);
            StringRequestEntity entity = new StringRequestEntity(param, contentType, UTF_8);
            method.setRequestEntity(entity);
            method.getParams().setContentCharset(UTF_8);
            setTimeout(connectionTimeout, readTimeout);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                byte[] bytes = method.getResponseBody();
                String charset = method.getResponseCharSet();
                result = new String(bytes, Charset.forName(charset));
            } else {
                throw new Exception("返回码不是200(" + method.getStatusCode() + ")");
            }
            writeResultLog(bizName, result, method.getStatusCode(), uuid);
        } catch (Exception e) {
            logger.error(uuid + " - " + bizName + "请求异常", e);
            throw e;
        } finally {
            method.releaseConnection();
            writeTimeLog(bizName, startTime, uuid);
        }
        return result;
    }

    public static String doPostJsonOrXmlHttpClient(String bizName, String url, String param, boolean isXml,Map<String,String> headerMap,
                                                   int connectionTimeout, int readTimeout) throws Exception {
        String contentType = "application/json";
        if (isXml) {
            contentType = "text/xml";
        }
        return doPostByContentTypeHttpClient(bizName, url, param, contentType,headerMap,
                connectionTimeout, readTimeout);
    }

    public static String doPostByContentTypeHttpClient(String bizName, String url, String param, String contentType,Map<String,String> headerMap,
                                                       int connectionTimeout, int readTimeout) throws Exception {
        String uuid = CodecUtils.getRequestUUID();
        logger.info(uuid + " - " + bizName + "请求开始 - " + POST_METHOD + " - " + url);
        long startTime = System.currentTimeMillis();
        String result = null;
        PostMethod method = new PostMethod(url);
        try {
            logger.info(uuid + " - xml/json参数 - " + param);
            StringRequestEntity entity = new StringRequestEntity(param, contentType, UTF_8);
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    method.setRequestHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info(uuid + " - header参数 - " + headerLogStr);
            }
            method.setRequestEntity(entity);
            method.getParams().setContentCharset(UTF_8);
            setTimeout(connectionTimeout, readTimeout);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                byte[] bytes = method.getResponseBody();
                String charset = method.getResponseCharSet();
                result = new String(bytes, Charset.forName(charset));
                System.out.println(result);
            } else {
                throw new Exception("返回码不是200(" + method.getStatusCode() + ")");
            }
            writeResultLog(bizName, result, method.getStatusCode(), uuid);
        } catch (Exception e) {
            logger.error(uuid + " - " + bizName + "请求异常", e);
            throw e;
        } finally {
            method.releaseConnection();
            writeTimeLog(bizName, startTime, uuid);
        }
        return result;
    }

    public static String doPostHttpClient(String bizName, String url, Map<String, String> headerMap,
                                          Map<String, String> paramsMap,
                                          int connectionTimeout, int readTimeout) throws Exception {
        String uuid = CodecUtils.getRequestUUID();
        logger.info(uuid + " - " + bizName + "请求开始 - " + POST_METHOD + " - " + url);
        long startTime = System.currentTimeMillis();
        String result = null;
        PostMethod method = new PostMethod(url);
        try {
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuilder paramsLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> entries = paramsMap.entrySet();
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : entries) {
                    pairs.add(new NameValuePair(param.getKey(), param.getValue()));
                    paramsLogStr.append("[").append(param.getKey()).append(" : ")
                            .append(param.getValue()).append("]");
                }
                logger.info(uuid + " - 参数 - " + paramsLogStr);
                method.setRequestBody(pairs.toArray(new NameValuePair[pairs.size()]));
            }
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    method.setRequestHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info(uuid + " - header参数 - " + headerLogStr);
            }
            method.getParams().setContentCharset(UTF_8);
            setTimeout(connectionTimeout, readTimeout);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                byte[] bytes = method.getResponseBody();
                String charset = method.getResponseCharSet();
                result = new String(bytes, Charset.forName(charset));
            } else {
                throw new Exception("返回码不是200(" + method.getStatusCode() + ")");
            }
            writeResultLog(bizName, result, method.getStatusCode(), uuid);
        } catch (Exception e) {
            logger.error(uuid + " - " + bizName + "请求异常", e);
            throw e;
        } finally {
            method.releaseConnection();
            writeTimeLog(bizName, startTime, uuid);
        }
        return result;
    }


    public static String doPutHttpClient(String bizName, String url, Map<String, String> headerMap,
                                          Map<String, Object> paramsMap,
                                          int connectionTimeout, int readTimeout) throws Exception {
        String uuid = CodecUtils.getRequestUUID();
        logger.info(uuid + " - " + bizName + "请求开始 - " + PUT_METHOD + " - " + url);
        long startTime = System.currentTimeMillis();
        String result = null;
        PutMethod method = new PutMethod(url);
        try {
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuilder paramsLogStr = new StringBuilder();
                Set<Map.Entry<String, Object>> entries = paramsMap.entrySet();
//                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                HttpMethodParams params = new HttpMethodParams();
//                for (Map.Entry<String, Object> param : entries) {
////                    pairs.add(new NameValuePair(param.getKey(), param.getValue()));
//                    params.setParameter(param.getKey(),param.getValue());
//                    paramsLogStr.append("[").append(param.getKey()).append(" : ")
//                            .append(param.getValue()).append("]");
//                }
                method.setRequestBody(JsonUtil.bean2JsonStr(entries));
                logger.info(uuid + " - 参数 - " + paramsLogStr);
//                method.setRequestBody(pairs.toArray(new NameValuePair[pairs.size()]));
                method.setParams(params);
            }
            if (headerMap != null && !headerMap.isEmpty()) {
                StringBuilder headerLogStr = new StringBuilder();
                Set<Map.Entry<String, String>> heads = headerMap.entrySet();
                for (Map.Entry<String, String> head : heads) {
                    method.setRequestHeader(head.getKey(), head.getValue());
                    headerLogStr.append("[").append(head.getKey()).append(" : ")
                            .append(head.getValue()).append("]");
                }
                logger.info(uuid + " - header参数 - " + headerLogStr);
            }
            method.getParams().setContentCharset(UTF_8);
            method.addRequestHeader( "Content-Type","application/json" );
            setTimeout(connectionTimeout, readTimeout);
            httpClient.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                byte[] bytes = method.getResponseBody();
                String charset = method.getResponseCharSet();
                result = new String(bytes, Charset.forName(charset));
            } else {
                byte[] bytes = method.getResponseBody();
                String charset = method.getResponseCharSet();
                result = new String(bytes, Charset.forName(charset));
                System.out.println(result);
                throw new Exception("返回码不是200(" + method.getStatusCode() + ")");
            }
            writeResultLog(bizName, result, method.getStatusCode(), uuid);
        } catch (Exception e) {
            logger.error(uuid + " - " + bizName + "请求异常", e);
            throw e;
        } finally {
            method.releaseConnection();
            writeTimeLog(bizName, startTime, uuid);
        }
        return result;
    }
    private static void setTimeout(int connectionTimeout, int readTimeout) {
        if (isDefaultConfig(connectionTimeout, readTimeout)) {
            String connTimeout = prop.getProperty("httpConnectionManagerParams.connectionTimeout");
            String soTimeout = prop.getProperty("httpConnectionManagerParams.soTimeout");
            if (StringUtils.isEmpty(connTimeout) || StringUtils.isEmpty(soTimeout))
                return;
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.parseInt(connTimeout));
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.parseInt(soTimeout));
        } else {
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
        }
    }

    private static void writeResultLog(String bizName, String result, int statusCode, String uuid) {
        if (statusCode == HttpStatus.SC_OK) {
            if (StringUtils.isEmpty(result) || "null".equals(result))
                logger.error(uuid + " - " + bizName + "请求结果为空");
            else
                logger.info(uuid + " - " + bizName + "结果:" + result);
        } else {
            logger.error(uuid + " - 状态码不是200:" + statusCode);
        }
    }

    private static void writeTimeLog(String bizName, long startTime, String uuid) {
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        logger.info(uuid + " - java-" + bizName + "请求结束 - time:[" + time + "]");
        RecordLogger.timeLog(bizName, time);
    }

    private static boolean isDefaultConfig(int connectionTimeout, int readTimeout) {
        return connectionTimeout == -1 && readTimeout == -1;
    }

    private static String parseRequestQueryString(Map<String, String> params, String uuid) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        logger.info(uuid + " - 参数 - " + result);
        if (result.length() > 0) {
            return result.substring(1);
        }
        return null;
    }

}
