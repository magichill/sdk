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
 * XML������
 * Created by gaoshan on 16/2/15.
 */
public class XmlUtil {

    private XmlUtil() {
    }

    /**
     * ����MAP����XML�ṹ(�ٷ�΢��֧����)
     */
    public static String buildXmlFromMap4Wx(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            LogContext.instance().warn("MAPΪ��");
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
            LogContext.instance().error(e, "ͨ��MAP����XMLʧ��");
        }
        return xml;
    }

    /**
     * ����MAP����XML�ṹ(�ٷ�΢��֧���ûص������)
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
            LogContext.instance().error(e, "ͨ��MAP����XMLʧ��");
        }
        return xml;
    }

    /**
     * ����XML�ַ�����ȡMAP(�����ڸ��ڵ��¾�һ�������)
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getMapFromXmlStr4OneLevel(String xmlStr) {
        if (StringUtils.isEmpty(xmlStr)) {
            LogContext.instance().warn("XML����Ϊ�ջ��߽ڵ�����Ϊ��");
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
            LogContext.instance().error(e, "����XMLʧ��");
        }
        return map;
    }

    /**
     * ���ӽڵ����ƻ�ȡ�ڵ�����(�����ڸ��ڵ��¾�һ�������)
     */
    public static String getNodeTextByNodeName4OneLevel(Document document, String nodeName) {
        String value = "";
        if (document == null || StringUtils.isEmpty(nodeName)) {
            LogContext.instance().warn("documentΪ�ջ��߽ڵ�����Ϊ��");
            return value;
        }
        try {
            Element root = document.getRootElement();
            Element element = root.element(nodeName);
            value = element.getTextTrim();
        } catch (Exception e) {
            LogContext.instance().error(e, "���ݽڵ����ƻ�ȡ�ڵ�����ʧ��");
        }
        return value;
    }

    public static Document getDocumentByXmlStr(String xmlStr) {
        if (StringUtils.isEmpty(xmlStr)) {
            LogContext.instance().error("XML����Ϊ��");
            return null;
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (Exception e) {
            LogContext.instance().error(e, "����XML���ݻ�ȡDOC����ʧ��");
        }
        return document;
    }

    public static String getNodeTextByXPath(Document document, String xPath) {
        String value = "";
        if (document == null || StringUtils.isEmpty(xPath)) {
            LogContext.instance().error("documentΪ�ջ�XPATHΪ��");
            return value;
        }
        try {
            Element element = (Element) document.selectSingleNode(xPath);
            if (element != null) {
                value = element.getTextTrim();
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "����XPATH��ȡ�ڵ�����ʧ��");
        }
        return value;
    }

    public static Map<String, String> getMapByRequestStream(HttpServletRequest request) {
        try {
            String xmlStr = getByStream(request.getInputStream());
            return getMapFromXmlStr4OneLevel(xmlStr);
        } catch (Exception e) {
            LogContext.instance().error(e, "������������ȡXML MAPʧ��");
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
            LogContext.instance().error(e, "��������ȡXML�ַ�������");
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    LogContext.instance().error(e, "��������ȡXML�ַ�������");
                }
        }
        return result.toString();
    }

}
