package com.bingdou.tools;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML工具类
 * Created by gaoshan on 16/2/15.
 */
public class XmlUtil {

    private XmlUtil() {
    }

    /**
     * 根据MAP创建XML结构(官方微信支付用)
     */
    public static String buildXmlFromMap4Wx(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            LogContext.instance().warn("MAP为空");
            return "";
        }
        String xml = "";
        try {
            Document document = DocumentHelper.createDocument();
            Element element = document.addElement("xml");
            for (String key : map.keySet()) {
                element.addElement(key).setText(map.get(key));
            }
            xml = document.asXML();
        } catch (Exception e) {
            LogContext.instance().error(e, "通过MAP创建XML失败");
        }
        return xml;
    }

    /**
     * 根据MAP创建XML结构(官方微信支付用回调结果用)
     */
    public static String buildXmlFromMap4WxCallBackResult(boolean success) {
        String xml = "";
        try {
            Document document = DocumentHelper.createDocument();
            Element element = document.addElement("xml");
            String returnCode = success ? "SUCCESS" : "FAIL";
            String returnMessage = success ? "OK" : "CALL BACK ERROR";
            element.addElement("return_code").setText(returnCode);
            element.addElement("return_msg").setText(returnMessage);
            xml = document.asXML();
        } catch (Exception e) {
            LogContext.instance().error(e, "通过MAP创建XML失败");
        }
        return xml;
    }

    /**
     * 根据XML字符串获取MAP(适用于根节点下就一级的情况)
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getMapFromXmlStr4OneLevel(String xmlStr) {
        if (StringUtils.isEmpty(xmlStr)) {
            LogContext.instance().warn("XML内容为空或者节点名称为空");
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for (Element el : elements) {
                map.put(el.getName(), el.getTextTrim());
            }
        } catch (Exception e) {
            map = null;
            LogContext.instance().error(e, "解析XML失败");
        }
        return map;
    }

    /**
     * 更加节点名称获取节点内容(适用于根节点下就一级的情况)
     */
    public static String getNodeTextByNodeName4OneLevel(Document document, String nodeName) {
        String value = "";
        if (document == null || StringUtils.isEmpty(nodeName)) {
            LogContext.instance().warn("document为空或者节点名称为空");
            return value;
        }
        try {
            Element root = document.getRootElement();
            Element element = root.element(nodeName);
            value = element.getTextTrim();
        } catch (Exception e) {
            LogContext.instance().error(e, "根据节点名称获取节点内容失败");
        }
        return value;
    }

    public static Document getDocumentByXmlStr(String xmlStr) {
        if (StringUtils.isEmpty(xmlStr)) {
            LogContext.instance().error("XML内容为空");
            return null;
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (Exception e) {
            LogContext.instance().error(e, "根据XML内容获取DOC对象失败");
        }
        return document;
    }

    public static String getNodeTextByXPath(Document document, String xPath) {
        String value = "";
        if (document == null || StringUtils.isEmpty(xPath)) {
            LogContext.instance().error("document为空或XPATH为空");
            return value;
        }
        try {
            Element element = (Element) document.selectSingleNode(xPath);
            if (element != null) {
                value = element.getTextTrim();
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "根据XPATH获取节点内容失败");
        }
        return value;
    }

    public static Map<String, String> getMapByRequestStream(HttpServletRequest request) {
        try {
            String xmlStr = getByStream(request.getInputStream());
            return getMapFromXmlStr4OneLevel(xmlStr);
        } catch (Exception e) {
            LogContext.instance().error(e, "根据请求流获取XML MAP失败");
        }
        return null;
    }

    private static String getByStream(InputStream inputStream) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "根据流读取XML字符串错误");
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LogContext.instance().error(e, "根据流读取XML字符串错误");
                }
        }
        return result.toString();
    }

}
