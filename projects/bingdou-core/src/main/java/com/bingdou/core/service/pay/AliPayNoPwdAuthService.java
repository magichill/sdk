package com.bingdou.core.service.pay;

import com.bingdou.core.cache.IAliPayNoPwdAuthCacheManager;
import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.model.AliPayNoPwdAuth;
import com.bingdou.core.model.AliPayNoPwdAuthStatus;
import com.bingdou.core.repository.pay.AliPayNoPwdAuthDao;
import com.bingdou.core.utils.PayUtils;
import com.bingdou.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝免密授权服务类
 */
@Service
public class AliPayNoPwdAuthService {

    @Autowired
    private AliPayNoPwdAuthDao aliPayNoPwdAuthDao;
    @Autowired
    private IAliPayNoPwdAuthCacheManager aliPayNoPwdAuthCacheManager;

    public String getAuthSignUrl(int userId, String returnUrl) throws UnsupportedEncodingException {
        String authNo = getAuthNo(userId);
        Map<String, String> paramMap = buildAgreementSignParams(authNo, returnUrl);
        String sign = PayUtils.getSign4Ali(paramMap);
        paramMap.put("sign", sign);
        String paramStr = getAuthSignParamStr(paramMap);
        return URLEncoder.encode(PayTypeData.PAY_TYPE_ALI_GATE_WAY + paramStr, "UTF-8");
    }

    public boolean isSigned(int userId) {
        LogContext.instance().info("获取支付宝免密是否授权");
        if (userId <= 0)
            return false;
        boolean result = false;
        try {
            boolean isSign = getSignStatus(userId);
            if (isSign) {
                LogContext.instance().info("检查状态为授权,需要去支付宝再次验证授权状态");
                result = isSignFromAliServer(userId);
            }
        } catch (Exception e) {
            LogContext.instance().error(e, "获取已授权信息错误");
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean authSignCallBack(HttpServletRequest request) throws Exception {
        Map<String, String> callResultMap = PayUtils.getResultMapFromRequestMap(request.getParameterMap(), false);
        if (callResultMap == null || callResultMap.isEmpty()) {
            LogContext.instance().error("解析响应结果为空");
            return false;
        }
        LogContext.instance().info("响应:" + callResultMap);
        boolean isVerify = PayUtils.verifyAliResponse(callResultMap);
        if (!isVerify) {
            LogContext.instance().error("验证失败");
            return false;
        }
        aliPayNoPwdAuthDao.insertAuthSignDetail(callResultMap);
        String externalSignNo = callResultMap.get("external_sign_no");
        if (StringUtils.isEmpty(externalSignNo)) {
            LogContext.instance().error("商户签约号为空");
            return false;
        }
        AliPayNoPwdAuthStatus status = AliPayNoPwdAuthStatus.getByName(callResultMap.get("status"));
        if (status == null) {
            LogContext.instance().error("未知签约状态");
            return false;
        }
        AliPayNoPwdAuth auth = aliPayNoPwdAuthDao.getUserIdByExternalSignNo(externalSignNo);
        if (auth == null) {
            LogContext.instance().error("不存在创建签约记录");
            return false;
        }
        if (auth.getStatus() == AliPayNoPwdAuthStatus.SIGNED.getIndex()) {
            LogContext.instance().info("已经签约,不需要再签约");
            return true;
        }
        long signTime = DateUtils.parseDate(callResultMap.get("sign_time"),
                DateUtil.YYYY_MM_DD_HH_MM_SS).getTime() / 1000;
        long validTime = DateUtils.parseDate(callResultMap.get("valid_time"),
                DateUtil.YYYY_MM_DD_HH_MM_SS).getTime() / 1000;
        callResultMap.put("sign_time", signTime + "");
        callResultMap.put("valid_time", validTime + "");
        callResultMap.put("status", status.getIndex() + "");
        int updateCount = aliPayNoPwdAuthDao.updateByExternalSignNo(callResultMap);
        if (updateCount <= 0)
            throw new Exception("更新支付宝免密授权签约DB错误");
        boolean updateCacheResult = aliPayNoPwdAuthCacheManager.updateAuthStatus(auth.getUserId(), status.getIndex());
        if (!updateCacheResult)
            LogContext.instance().error("更新支付宝免密授权签约缓存错误");
        LogContext.instance().info("开通支付免密成功");
        return true;
    }

    private String getAuthSignParamStr(Map<String, String> paramMap) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> object : paramMap.entrySet()) {
            result.append(object.getKey()).append("=")
                    .append(URLEncoder.encode(object.getValue(), "UTF-8"));
            if (i < paramMap.size() - 1)
                result.append("&");
            i++;
        }
        return result.toString();
    }

    private boolean getSignStatus(int userId) {
        LogContext.instance().info("获取支付宝免密状态");
        if (userId <= 0)
            return false;
        boolean result;
        LogContext.instance().info("从缓存读取支付宝免密授权数据");
        String statusStr = aliPayNoPwdAuthCacheManager.getAuthInfo(userId);
        if (StringUtils.isEmpty(statusStr)) {
            LogContext.instance().info("缓存为空,从DB获取授权数据");
            int count = aliPayNoPwdAuthDao.getStatusCountByStatus(userId,
                    AliPayNoPwdAuthStatus.SIGNED.getIndex());
            result = count > 0;
        } else {
            int status = Integer.parseInt(statusStr);
            result = status == AliPayNoPwdAuthStatus.SIGNED.getIndex();
        }
        return result;
    }

    public boolean isSignFromAliServer(int userId) throws Exception {
        AliPayNoPwdAuth signedAuthInfo = aliPayNoPwdAuthDao.getAuthInfo(userId);
        if (signedAuthInfo == null) {
            LogContext.instance().error("未查询到授权用户信息");
            return false;
        }
        Map<String, String> queryAliResultMap = queryAliAuthInfo(signedAuthInfo.getAlipayUserId(),
                signedAuthInfo.getExternalSignNo());
        if (queryAliResultMap == null || queryAliResultMap.isEmpty()) {
            LogContext.instance().error("解析支付宝查询授权结果为空");
            return false;
        }
        String aliQueryResult = queryAliResultMap.get("status");
        LogContext.instance().info("支付宝查询结果:" + aliQueryResult);
        if (!AliPayNoPwdAuthStatus.SIGNED.getName().equals(aliQueryResult)) {
            LogContext.instance().info("更新海马用户授权状态为:未授权");
            boolean updateResult = updateAuthStatus(userId, AliPayNoPwdAuthStatus.CREATE_SIGN);
            LogContext.instance().info("更新授权状态结果:" + updateResult);
            if (!updateResult) {
                LogContext.instance().error("更新授权状态失败");
            }
            return false;
        }
        return true;
    }

    public AliPayNoPwdAuth getAuthInfo(int userId) {
        if (userId <= 0)
            return null;
        return aliPayNoPwdAuthDao.getAuthInfo(userId);
    }

    private boolean updateAuthStatus(int userId, AliPayNoPwdAuthStatus status) {
        int updateDBCount = aliPayNoPwdAuthDao.updateSignStatus(userId, status.getIndex());
        LogContext.instance().info("更新授权数据库数量:" + updateDBCount);
        if (updateDBCount <= 0) {
            return false;
        }
        boolean updateCacheResult = aliPayNoPwdAuthCacheManager.updateAuthStatus(userId, status.getIndex());
        LogContext.instance().info("更新授权缓存:" + updateCacheResult);
        return updateCacheResult && updateDBCount > 0;
    }

    private Map<String, String> buildAgreementQueryParams(String aliUserId, String externalSignNo) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("service", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_QUERY_METHOD);
        params.put("partner", Constants.ALI_PID);
        params.put("sign_type", "MD5");
        params.put("product_code", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_PRODUCT_CODE);
        params.put("alipay_user_id", aliUserId);
        params.put("scene", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_SCENE);
        if (StringUtils.isNotEmpty(externalSignNo)) {
            params.put("external_sign_no", externalSignNo);
        }
        return params;
    }

    private Map<String, String> buildAgreementSignParams(String externalSignNo, String returnUrl) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("_input_charset", "utf-8");
        params.put("service", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_SIGN_METHOD);
        params.put("partner", Constants.ALI_PID);
        params.put("sign_type", "MD5");
        if (StringUtils.isNotEmpty(returnUrl))
            params.put("return_url", returnUrl);
        params.put("notify_url", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_NOTIFY_URL);
        params.put("product_code", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_PRODUCT_CODE);
        params.put("access_info", "{\"channel\":\"ALIPAYAPP\"}");
        params.put("scene", PayTypeData.PAY_TYPE_ALI_NO_PWD_AUTH_SCENE);
        params.put("external_sign_no", externalSignNo);
        return params;
    }

    private Map<String, String> queryAliAuthInfo(String aliUserId, String externalSignNo) throws Exception {
        Map<String, String> paramMap = buildAgreementQueryParams(aliUserId, externalSignNo);
        String sign = PayUtils.getSign4Ali(paramMap);
        paramMap.put("sign", sign);
        String result = HttpClientUtil.doGetHttpClient("ali-no-pwd-query-auth", PayTypeData.PAY_TYPE_ALI_URL_WITH_ENCODING,
                paramMap, null,
                PayTypeData.PAY_TYPE_ALI_TIMEOUT,
                PayTypeData.PAY_TYPE_ALI_TIMEOUT);
        LogContext.instance().info("支付宝免密授权结果:" + result);
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        Document document = XmlUtil.getDocumentByXmlStr(result);
        String resultCode = XmlUtil.getNodeTextByXPath(document, "/alipay/is_success");
        if (!"T".equals(resultCode)) {
            return null;
        }
        Map<String, String> resultMap = new HashMap<String, String>(14);
        String xpathPrefix = "/alipay/response/userAgreementInfo/";
        resultMap.put("agreement_detail",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "agreement_detail"));
        resultMap.put("agreement_no",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "agreement_no"));
        resultMap.put("external_sign_no",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "external_sign_no"));
        resultMap.put("invalid_time",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "invalid_time"));
        resultMap.put("pricipal_type",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "pricipal_type"));
        resultMap.put("principal_id",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "principal_id"));
        resultMap.put("product_code",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "product_code"));
        resultMap.put("scene",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "scene"));
        resultMap.put("sign_modify_time",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "sign_modify_time"));
        resultMap.put("sign_time",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "sign_time"));
        resultMap.put("status",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "status"));
        resultMap.put("thirdpart_id",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "thirdpart_id"));
        resultMap.put("thirdpart_type",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "thirdpart_type"));
        resultMap.put("valid_time",
                XmlUtil.getNodeTextByXPath(document, xpathPrefix + "valid_time"));
        return resultMap;
    }

    private String getAuthNo(int userId) {
        AliPayNoPwdAuth auth = aliPayNoPwdAuthDao.getAuthInfo(userId);
        if (auth == null) {
            LogContext.instance().info("不存在授权记录,插入");
            String authNo = buildAuthNo();
            aliPayNoPwdAuthDao.createAuth(userId, authNo);
            return authNo;
        }
        if (auth.getStatus() == AliPayNoPwdAuthStatus.SIGNED.getIndex()) {
            LogContext.instance().warn("已经签约,无需再次签约");
            return "";
        }
        return auth.getExternalSignNo();
    }

    private String buildAuthNo() {
        return DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS)
                + NumberUtil.getRandomNum(1000, 9999);
    }

}
