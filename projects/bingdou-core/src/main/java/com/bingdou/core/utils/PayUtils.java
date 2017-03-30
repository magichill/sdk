package com.bingdou.core.utils;

import com.bingdou.core.constants.Constants;
import com.bingdou.core.constants.PayConstants;
import com.bingdou.core.constants.PayTypeData;
import com.bingdou.core.model.*;
import com.bingdou.core.service.pay.paytype.PayTypeCallBackResponse;
import com.bingdou.core.service.pay.paytype.PayTypeRequest;
import com.bingdou.core.service.pay.paytype.PayTypeResponse;
import com.bingdou.tools.DateUtil;
import com.bingdou.tools.HttpClientUtil;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.NumberUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class PayUtils {

    private PayUtils() {
    }

    public static boolean isSpecialActivity(String appId, String channel) {
        if (StringUtils.isEmpty(appId)
                || StringUtils.isEmpty(channel))
            return false;
        if (!PayConstants.SPECIAL_ACTIVITY_CHANNEL.equals(channel))
            return false;
        String[] appIdArray = PayConstants.SPECIAL_ACTIVITY_APP_IDS.split(",");
        for (String str : appIdArray) {
            if (str.equals(appId))
                return true;
        }
        return false;
    }

    public static String generateConsumeOrderIdByPayType(PayType payType) throws Exception {
        String prefix;
        String dateStr = DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS);
        long randomNum = NumberUtil.getRandomNum(1000, 9999);
        if (PayType.ALI_MOBILE.equals(payType)) {
            prefix = "PY";
        } else if (PayType.ALI_NO_PWD.equals(payType)) {
            prefix = "AP";
        } else if (PayType.ALI_SCAN.equals(payType)) {
            prefix = "AS";
        } else if (PayType.UPMP.equals(payType)) {
            prefix = "UP";
        } else if (PayType.WEIXIN.equals(payType)) {
            prefix = "WX";
        } else if (PayType.PUBLIC_WEIXIN.equals(payType)) {
            prefix = "PUBLICWX";
        } else if (PayType.PP.equals(payType)) {
            prefix = "PP";
        } else if (PayType.BINGDOU_COIN.equals(payType)) {
            prefix = "XF";
        } else if (PayType.HEEPAY_WEIXIN_SDK.equals(payType)) {
            prefix = "HEE";
        } else if (PayType.HEEPAY_WEIXIN_SCAN_CODE.equals(payType)) {
            prefix = "HEES";
        } else if (PayType.HEEPAY_WEIXIN_WAP.equals(payType)) {
            prefix = "HEEW";
        } else if (PayType.CHINA_PAY_UNION.equals(payType)) {
            prefix = "CHUN";
        } else {
            throw new Exception("非法支付方式,生成消费订单号失败");
        }
        return prefix + dateStr + randomNum;
    }

    public static String generateMixConsumeOrderId() {
        String dateStr = DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS);
        return "MIX" + dateStr + NumberUtil.getRandomNum(1000, 9999);
    }

    public static String generateRechargeOrderId() {
        String dateStr = DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS);
        return "CZ" + dateStr + NumberUtil.getRandomNum(1000, 9999);
    }

    public static String generateRechargeVoucherOrderId() {
        String dateStr = DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS);
        return "RV" + dateStr + NumberUtil.getRandomNum(1000, 9999);
    }

    public static String generateAppleInnerPayOrderId() {
        String dateStr = DateUtil.format(new Date(), DateUtil.YYYYMMDDHHMMSS);
        return "AP_IN" + dateStr + NumberUtil.getRandomNum(1000, 9999);
    }

    public static String getUnionPayOrderId(PayType payType, PayTypeResponse response) {
        if (response == null)
            return "";
        String unionPayOrderId = "";
//        if (PayType.UPMP.equals(payType)) {
//            unionPayOrderId = response.getUpmpResponse().getPreOrderId();
//        }
        return unionPayOrderId;
    }

    public static String getSignSeedByArray(String[] array, boolean isFilterEmptyValue, boolean isOrderAsc) {
        if (isOrderAsc) {
            Arrays.sort(array);
        }
        String signSeed = "";
        int i = 0;
        for (String str : array) {
            if (str.contains("sign=")) {
                i++;
                continue;
            }
            if (isFilterEmptyValue) {
                String[] temp = str.split("=");
                if (temp.length < 2 || StringUtils.isEmpty(temp[1])) {
                    i++;
                    continue;
                }
            }
            if (i != array.length - 1)
                signSeed += str + "&";
            else
                signSeed += str;
            i++;
        }
        return signSeed;
    }

    public static String getSignSeedByMap(Map<String, String> postParamsMap) {
        String[] params = convertArrayFromMap(postParamsMap);
        String signStr = "";
        int i = 0;
        for (String str : params) {
            if (i != params.length - 1)
                signStr += str + "&";
            else
                signStr += str;
            i++;
        }
        return signStr;
    }

    public static Map<String, String> parseMap(String responseStr) {
        String[] array = responseStr.split("&");
        if (array.length < 0) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        for (String str : array) {
            String[] temp = str.split("=");
            if (temp.length < 0)
                continue;
            map.put(temp[0], temp[1]);
        }
        return map;
    }

    public static String getParamStrByArray(String[] array) {
        String paramStr = "";
        int i = 0;
        for (String str : array) {
            if (i != array.length - 1)
                paramStr += str + "&";
            else
                paramStr += str;
            i++;
        }
        return paramStr;
    }

    public static int validateOrderStatus(Order order) {
        int verifyResult;
        if (order == null) {
            verifyResult = OrderStatus.NOT_EXISTS.getIndex();
        } else {
            verifyResult = OrderStatus.NOT_PAY.getIndex();
            if (OrderStatus.PAYED.getIndex() == order.getStatus()) {
                verifyResult = OrderStatus.PAYED.getIndex();
            }
        }
        return verifyResult;
    }

    public static int validateOrderStatus(int status) {
        if (status <= 0)
            return OrderStatus.NOT_EXISTS.getIndex();
        int verifyResult = OrderStatus.NOT_PAY.getIndex();
        if (OrderStatus.PAYED.getIndex() == status) {
            verifyResult = OrderStatus.PAYED.getIndex();
        }
        return verifyResult;
    }

    public static MoneyLog buildRechargeVoucherMoneyLog(int userId, String rechargeOrderId, int voucherAmount,
                                                        int remainBalanceFen) {
        MoneyLog moneyLog = new MoneyLog();
        moneyLog.setUserId(userId);
        moneyLog.setItem(rechargeOrderId);
        moneyLog.setMoney(voucherAmount);
        moneyLog.setMoneyBalance(remainBalanceFen);
        moneyLog.setReason("充值代金券");
        moneyLog.setType(MoneyLogType.RECHARGE_VOUCHER.getIndex());
        return moneyLog;
    }

    public static MoneyLog buildConsumeMoneyLog(int userId, String consumeOrderId, int consumeMoney,
                                                int remainBalanceFen, boolean isBingdouCoin) {
        MoneyLog moneyLog = new MoneyLog();
        moneyLog.setUserId(userId);
        moneyLog.setItem(consumeOrderId);
        moneyLog.setMoney(-consumeMoney);
        moneyLog.setMoneyBalance(remainBalanceFen);
        if (isBingdouCoin) {
            moneyLog.setReason("消费冰豆币余额");
            moneyLog.setType(MoneyLogType.CONSUME_BINGDOU_COIN.getIndex());
        } else {
            moneyLog.setReason("消费游戏币余额");
            moneyLog.setType(MoneyLogType.CONSUME_VIRTUAL_MONEY.getIndex());
        }
        return moneyLog;
    }

    public static MoneyLog buildRechargeBackMoneyLog(String rechargeOrderId, PayType payType, int userId,
                                                     int userVirtualMoneyFen, int backMoneyFen) {
        MoneyLog moneyLog = new MoneyLog();
        moneyLog.setUserId(userId);
        moneyLog.setItem(rechargeOrderId);
        moneyLog.setMoney(backMoneyFen);
        moneyLog.setMoneyBalance(userVirtualMoneyFen + backMoneyFen);
        moneyLog.setReason(payType.getName() + "充值返利");
        moneyLog.setType(MoneyLogType.BACK.getIndex());
        return moneyLog;
    }

    public static MoneyLog buildRechargeMoneyLog(String rechargeOrderId, PayType payType, int userId,
                                                 int amount, int userMoney) {
        MoneyLog moneyLog = new MoneyLog();
        moneyLog.setUserId(userId);
        moneyLog.setItem(rechargeOrderId);
        moneyLog.setMoney(amount);
        //TODO 充值金额与冰豆比例 1:10
        moneyLog.setMoneyBalance(userMoney + amount*10);
        moneyLog.setReason(payType.getName() + "充值");
        moneyLog.setType(MoneyLogType.RECHARGE.getIndex());
        return moneyLog;
    }

    public static MoneyLog buildTransferVirtualMoneyLog(int userId, String orderId, int transferMoneyFen,
                                                        int remainVirtualMoney, boolean isAddMoney) {
        MoneyLog merchantLog = new MoneyLog();
        merchantLog.setUserId(userId);
        merchantLog.setReason("冰豆账户转账");
        merchantLog.setItem(orderId);
        if (isAddMoney)
            merchantLog.setMoney(transferMoneyFen);
        else
            merchantLog.setMoney(-transferMoneyFen);
        merchantLog.setMoneyBalance(remainVirtualMoney);
        merchantLog.setType(MoneyLogType.TRANSFER.getIndex());
        return merchantLog;
    }

    public static List<Float> convertYuanList(List<Integer> fenList) {
        List<Float> resultList = new ArrayList<Float>(fenList.size());
        for (Integer fen : fenList) {
            resultList.add(NumberUtil.convertYuanFromFen(fen));
        }
        return resultList;
    }

    public static List<Integer> convertFenList(List<Float> yuanList) {
        List<Integer> resultList = new ArrayList<Integer>(yuanList.size());
        for (Float yuan : yuanList) {
            resultList.add(NumberUtil.convertFenFromYuan(yuan));
        }
        return resultList;
    }

    public static String getSign4Ali(Map<String, String> postParamsMap) throws UnsupportedEncodingException {
        String[] params = getSignParams4Ali(postParamsMap);
        String sign = "";
        int i = 0;
        for (String str : params) {
            if (i != params.length - 1)
                sign += str + "&";
            else
                sign += str;
            i++;
        }
        sign += PayTypeData.PAY_TYPE_ALI_SIGN_KEY;
        return DigestUtils.md5Hex(sign.getBytes("utf-8"));
    }

    public static boolean verifyAliResponse(Map<String, String> params) {
        boolean result = false;
        try {
            String responseTxt = "";
            if (params.get("notify_id") != null) {
                String notifyId = params.get("notify_id");
                responseTxt = callAliVerify(notifyId);
            }
            String signType = params.get("sign_type");
            LogContext.instance().info("SIGN TYPE:" + signType);
            signType = StringUtils.isEmpty(signType) ? "" : signType.toLowerCase();
            boolean isSign = true;
            if (signType.equals("md5")) {
                String sign = "";
                if (params.get("sign") != null) {
                    sign = params.get("sign");
                }
                String mySign = getSign4Ali(params);
                LogContext.instance().info("MY SIGN:" + mySign);
                isSign = mySign.equals(sign);
            }
            result = isSign && responseTxt.equals("true");
        } catch (Exception e) {
            LogContext.instance().error(e, "验证支付宝结果失败");
        }
        return result;
    }

//    public static boolean verifyChinaPayUnionResponse(Map<String, String> params) {
//        if (params == null || params.isEmpty())
//            return false;
//        SecssUtil secssUtil = new SecssUtil();
//        secssUtil.init();
//        secssUtil.verify(params);
//        if (!"00".equals(secssUtil.getErrCode())) {
//            LogContext.instance().error("CHINA PAY验证签名错误:" + secssUtil.getErrCode() + " "
//                    + secssUtil.getErrMsg());
//            return false;
//        }
//        return true;
//    }

    public static Map<String, String> getResultMapFromRequestMap(Map requestParams, boolean isUrlDecode) {
        String paramStr = "";
        Map<String, String> params = new HashMap<String, String>();
        try {
            for (Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext(); ) {
                String name = (String) iterator.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                if (isUrlDecode) {
                    params.put(name, URLDecoder.decode(valueStr, "utf-8"));
                } else {
                    params.put(name, valueStr);
                }
                paramStr += name + "=" + valueStr + "&";
            }
        } catch (Exception e) {
            params = null;
            LogContext.instance().error(e, "从REQUEST MAP解析错误");
        }
        LogContext.instance().info("参数:" + paramStr);
        return params;
    }

    public static WebPayTypeRequest buildWebPayTypeRequest(PayTypeRequest payTypeRequest) {
        WebPayTypeRequest webPayTypeRequest = new WebPayTypeRequest();
        webPayTypeRequest.setUserId(payTypeRequest.getUserId());
        webPayTypeRequest.setOrderId(payTypeRequest.getOrderId());
        webPayTypeRequest.setOrderMoney(payTypeRequest.getMoneyFen());
        webPayTypeRequest.setOrderDesc(payTypeRequest.getOrderDesc());
        webPayTypeRequest.setClientIp(payTypeRequest.getClientIP());
        if (payTypeRequest.isRecharge()) {
            webPayTypeRequest.setOrderType(1);
        } else {
            webPayTypeRequest.setOrderType(2);
        }
        HttpServletRequest request = payTypeRequest.getBaseRequest().getRequest();
        Object object = request.getAttribute(Constants.REQUEST_SAFE_INFO_NAME);
        if (object == null) {
            LogContext.instance().error("安全加固对象为空");
            return null;
        }
        SafeInfo safeInfo = (SafeInfo) object;
        if (safeInfo.getIsClient() == 1) {
            webPayTypeRequest.setReturnUrl(PayTypeData.CLIENT_PAY_SUCCESS_RETURN_URL);
        } else {
            webPayTypeRequest.setReturnUrl(payTypeRequest.getReturnUrl());
        }
        webPayTypeRequest.setPayType(payTypeRequest.getPayType());
        webPayTypeRequest.setRequestSourceIndex(payTypeRequest.getBaseRequest().getRequestSource());
        return webPayTypeRequest;
    }

//    public static String getSign4ChinaPayUnion(Map<String, String> params) {
//        SecssUtil secssUtil = new SecssUtil();
//        secssUtil.init();
//        secssUtil.sign(params);
//        if (!"00".equals(secssUtil.getErrCode())) {
//            LogContext.instance().error("CHINA PAY签名错误:" + secssUtil.getErrCode() + " "
//                    + secssUtil.getErrMsg());
//            return "";
//        }
//        return secssUtil.getSign();
//    }

    public static boolean isValid(Map<String, String> callResultMap) {
        boolean result = false;
        boolean isVerify = PayUtils.verifyAliResponse(callResultMap);
        if (isVerify) {
            String tradeStatus = callResultMap.get("trade_status");
            result = PayConstants.ALI_CALL_RESULT_FINISHED_CODE.equals(tradeStatus)
                    || PayConstants.ALI_CALL_RESULT_SUCCESS_CODE.equals(tradeStatus);
        } else {
            LogContext.instance().error("验证失败");
        }
        return result;
    }

    public static Map<String, String> getHeepayCallBackResponseMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>(9);
        map.put("result", request.getParameter("result"));
        map.put("payMessage", request.getParameter("pay_message"));
        map.put("agentId", request.getParameter("agent_id"));
        map.put("jnetBillNo", request.getParameter("jnet_bill_no"));
        map.put("agentBillId", request.getParameter("agent_bill_id"));
        map.put("payType", request.getParameter("pay_type"));
        map.put("payAmt", request.getParameter("pay_amt"));
        map.put("remark", request.getParameter("remark"));
        map.put("sign", request.getParameter("sign"));
        return map;
    }

    public static PayTypeCallBackResponse getAliPayTypeCallBackResult(HttpServletRequest request,
                                                                      PayType payType) {
        Map<String, String> callResultMap = PayUtils.getResultMapFromRequestMap(request.getParameterMap(), false);
        if (callResultMap == null || callResultMap.isEmpty()) {
            LogContext.instance().error("解析响应结果为空");
            return null;
        }
        LogContext.instance().info("响应:" + callResultMap);
        if (PayConstants.ALI_CALL_RESULT_WAIT_CODE.equals(callResultMap.get("trade_status"))) {
            LogContext.instance().info("等待支付状态,直接返回");
            return null;
        }
        if (!PayUtils.isValid(callResultMap)) {
            LogContext.instance().error("回调验证失败");
            return null;
        }
        PayTypeCallBackResponse callBackResponse = new PayTypeCallBackResponse();
        callBackResponse.setOrderId(callResultMap.get("trade_no"));
        callBackResponse.setBingdouOrderId(callResultMap.get("out_trade_no"));
        Float payAmt = Float.valueOf(callResultMap.get("total_fee"));
        callBackResponse.setAmount(NumberUtil.convertFenFromYuan(payAmt));
        if (PayType.ALI_SCAN.equals(payType)
                && NumberUtils.isNumber(callResultMap.get("extra_common_param"))) {
            callBackResponse.setUserId(Integer.parseInt(callResultMap.get("extra_common_param")));
        }
        callBackResponse.setPayType(payType);
        callBackResponse.setParamMap(callResultMap);
        return callBackResponse;
    }

    private static String[] getSignParams4Ali(Map<String, String> params) {
        Map<String, String> temp = new HashMap<String, String>();
        for (String key : params.keySet()) {
            if (key.equals("sign") || key.equals("sign_type")
                    || StringUtils.isEmpty(params.get(key)))
                continue;
            temp.put(key, params.get(key));
        }
        String[] array = new String[temp.size()];
        int i = 0;
        for (String key : temp.keySet()) {
            array[i] = key + "=" + temp.get(key);
            i++;
        }
        Arrays.sort(array);
        return array;
    }

    private static String[] convertArrayFromMap(Map<String, String> params) {
        Map<String, String> map = new HashMap<String, String>();
        for (String key : params.keySet()) {
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            map.put(key, params.get(key));
        }
        String[] array = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            array[i] = key + "=" + map.get(key);
            i++;
        }
        Arrays.sort(array);
        return array;
    }

    private static String callAliVerify(String notifyId) {
        String result = "";
        try {
            String url = PayTypeData.PAY_TYPE_VERIFY_URL + "&partner=" + Constants.ALI_PID + "&notify_id=" + notifyId;
            result = HttpClientUtil.doGetHttpClient("ali_verify", url, PayTypeData.PAY_TYPE_ALI_TIMEOUT,
                    PayTypeData.PAY_TYPE_ALI_TIMEOUT);
        } catch (Exception e) {
            LogContext.instance().error(e, "调用支付宝验证接口失败");
        }
        return result;
    }

}
