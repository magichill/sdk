package com.bingdou.payserver.controller;


import com.bingdou.core.model.PayType;
import com.bingdou.core.service.pay.paytype.IPayTypeService;
import com.bingdou.core.service.pay.paytype.PayTypeCallBackResponse;
import com.bingdou.core.service.pay.paytype.PayTypeFactory;
import com.bingdou.payserver.service.PayTypeCallBackService;
import com.bingdou.tools.CodecUtils;
import com.bingdou.tools.LogContext;
import com.bingdou.tools.XmlUtil;
import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * SDK支付中心服务提供给支付方式的回调
 */
@RequestMapping("pay_type_call_back")
@Controller
public class PayTypeCallBackController {

    @Autowired
    private PayTypeFactory payTypeFactory;
    @Autowired
    private PayTypeCallBackService payTypeCallBackService;

    @RequestMapping(value = "card_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String cardRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("19PAY充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.PAY_19, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "19PAY充值回调失败");
        } finally {
            LogContext.instance().info("19PAY充值回调结果:" + isSuccess);
            LogContext.instance().info("19PAY充值回调结束");
        }
        return isSuccess ? "Y" : "N";
    }

    @RequestMapping(value = "pp_recharge", method = RequestMethod.GET)
    @ResponseBody
    public String ppRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("PP充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.PP, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "PP充值回调失败");
        } finally {
            LogContext.instance().info("PP充值回调结果:" + isSuccess);
            LogContext.instance().info("PP充值回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "pp_consume", method = RequestMethod.GET)
    @ResponseBody
    public String ppConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("PP直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.PP, false);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "PP直充回调失败");
        } finally {
            LogContext.instance().info("PP直充回调结果:" + isSuccess);
            LogContext.instance().info("PP直充回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "heepay_wx_sdk_recharge", method = RequestMethod.GET)
    @ResponseBody
    public String heepayWxSdkRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("汇元微信SDK充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.HEEPAY_WEIXIN_SDK, true);
            if (response == null)
                return "error";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "汇元微信SDK充值回调失败");
        } finally {
            LogContext.instance().info("汇元微信SDK充值回调结果:" + isSuccess);
            LogContext.instance().info("汇元微信SDK充值回调结束");
        }
        return isSuccess ? "ok" : "error";
    }

    @RequestMapping(value = "heepay_wx_sdk_consume", method = RequestMethod.GET)
    @ResponseBody
    public String heepayWxSdkConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("汇元微信SDK直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.HEEPAY_WEIXIN_SDK, false);
            if (response == null)
                return "error";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "汇元微信SDK直充回调失败");
        } finally {
            LogContext.instance().info("汇元微信SDK直充回调结果:" + isSuccess);
            LogContext.instance().info("汇元微信SDK直充回调结束");
        }
        return isSuccess ? "ok" : "error";
    }

    @RequestMapping(value = "heepay_wx_scan_code_recharge", method = RequestMethod.GET)
    @ResponseBody
    public String heepayWxScanCodeRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("汇元微信扫码充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.HEEPAY_WEIXIN_SCAN_CODE, true);
            if (response == null)
                return "error";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "汇元微信扫码充值回调失败");
        } finally {
            LogContext.instance().info("汇元微信扫码充值回调结果:" + isSuccess);
            LogContext.instance().info("汇元微信扫码充值回调结束");
        }
        return isSuccess ? "ok" : "error";
    }

    @RequestMapping(value = "heepay_wx_scan_code_consume", method = RequestMethod.GET)
    @ResponseBody
    public String heepayWxScanCodeConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("汇元微信扫码直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.HEEPAY_WEIXIN_SCAN_CODE, false);
            if (response == null)
                return "error";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "汇元微信扫码直充回调失败");
        } finally {
            LogContext.instance().info("汇元微信扫码直充回调结果:" + isSuccess);
            LogContext.instance().info("汇元微信扫码直充回调结束");
        }
        return isSuccess ? "ok" : "error";
    }

    @RequestMapping(value = "heepay_wx_wap_recharge", method = RequestMethod.GET)
    @ResponseBody
    public String heepayWxWapRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("汇元微信WAP充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.HEEPAY_WEIXIN_WAP, true);
            if (response == null)
                return "error";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "汇元微信WAP充值回调失败");
        } finally {
            LogContext.instance().info("汇元微信WAP充值回调结果:" + isSuccess);
            LogContext.instance().info("汇元微信WAP充值回调结束");
        }
        return isSuccess ? "ok" : "error";
    }

    @RequestMapping(value = "heepay_wx_wap_consume", method = RequestMethod.GET)
    @ResponseBody
    public String heepayWxWapConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("汇元微信WAP直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.HEEPAY_WEIXIN_WAP, false);
            if (response == null)
                return "error";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "汇元微信WAP直充回调失败");
        } finally {
            LogContext.instance().info("汇元微信WAP直充回调结果:" + isSuccess);
            LogContext.instance().info("汇元微信WAP直充回调结束");
        }
        return isSuccess ? "ok" : "error";
    }

    @RequestMapping(value = "ali_pay_scan_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String aliPayScanRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("支付宝扫码充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.ALI_SCAN, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "支付宝扫码充值回调失败");
        } finally {
            LogContext.instance().info("支付宝扫码充值回调结果:" + isSuccess);
            LogContext.instance().info("支付宝扫码充值回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "ali_pay_scan_consume", method = RequestMethod.POST)
    @ResponseBody
    public String aliPayScanConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("支付宝扫码直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.ALI_SCAN, false);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "支付宝扫码直充回调失败");
        } finally {
            LogContext.instance().info("支付宝扫码直充回调结果:" + isSuccess);
            LogContext.instance().info("支付宝扫码直充回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "ali_pay_mobile_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String aliPayMobileRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("支付宝移动支付充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.ALI_MOBILE, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "支付宝移动支付充值回调失败");
        } finally {
            LogContext.instance().info("支付宝移动支付充值回调结果:" + isSuccess);
            LogContext.instance().info("支付宝移动支付充值回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "ali_pay_mobile_consume", method = RequestMethod.POST)
    @ResponseBody
    public String aliPayMobileConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("支付宝移动支付直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.ALI_MOBILE, false);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "支付宝移动支付直充回调失败");
        } finally {
            LogContext.instance().info("支付宝移动支付直充回调结果:" + isSuccess);
            LogContext.instance().info("支付宝移动支付直充回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "ali_pay_no_pwd_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String aliPayNoPwdRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("支付宝免密充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.ALI_NO_PWD, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "支付宝免密充值回调失败");
        } finally {
            LogContext.instance().info("支付宝免密充值回调结果:" + isSuccess);
            LogContext.instance().info("支付宝免密充值回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "ali_pay_no_pwd_consume", method = RequestMethod.POST)
    @ResponseBody
    public String aliPayNoPwdConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("支付宝免密直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.ALI_NO_PWD, false);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "支付宝免密直充回调失败");
        } finally {
            LogContext.instance().info("支付宝免密直充回调结果:" + isSuccess);
            LogContext.instance().info("支付宝免密直充回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "china_pay_union_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String chinaPayUnionRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("CHINA PAY银联充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.CHINA_PAY_UNION, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "CHINA PAY银联充值回调失败");
        } finally {
            LogContext.instance().info("CHINA PAY银联充值回调结果:" + isSuccess);
            LogContext.instance().info("CHINA PAY银联充值回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "china_pay_union_consume", method = RequestMethod.POST)
    @ResponseBody
    public String chinaPayUnionConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("CHINA PAY银联直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.CHINA_PAY_UNION, false);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "CHINA PAY银联直充回调失败");
        } finally {
            LogContext.instance().info("CHINA PAY银联直充回调结果:" + isSuccess);
            LogContext.instance().info("CHINA PAY银联直充回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "weixin_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String weixinRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("官方微信充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.WEIXIN, true);
            if (response == null)
                return XmlUtil.buildXmlFromMap4WxCallBackResult(false);
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "官方微信充值回调失败");
        } finally {
            LogContext.instance().info("官方微信充值回调结果:" + isSuccess);
            LogContext.instance().info("官方微信充值回调结束");
        }
        return XmlUtil.buildXmlFromMap4WxCallBackResult(isSuccess);
    }

    @RequestMapping(value = "weixin_public_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String weixinPublicRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("官方微信公众号充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.PUBLIC_WEIXIN, true);
            if (response == null)
                return XmlUtil.buildXmlFromMap4WxCallBackResult(false);
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "官方微信公众号充值回调失败");
        } finally {
            LogContext.instance().info("官方微信公众号充值回调结果:" + isSuccess);
            LogContext.instance().info("官方微信公众号充值回调结束");
        }
        return XmlUtil.buildXmlFromMap4WxCallBackResult(isSuccess);
    }

    @RequestMapping(value = "weixin_consume", method = RequestMethod.POST)
    @ResponseBody
    public String weixinConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("官方微信直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.WEIXIN, false);
            if (response == null)
                return XmlUtil.buildXmlFromMap4WxCallBackResult(false);
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "官方微信直充回调失败");
        } finally {
            LogContext.instance().info("官方微信直充回调结果:" + isSuccess);
            LogContext.instance().info("官方微信直充回调结束");
        }
        return XmlUtil.buildXmlFromMap4WxCallBackResult(isSuccess);
    }

    @RequestMapping(value = "upmp_recharge", method = RequestMethod.POST)
    @ResponseBody
    public String upmpRecharge(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("融信优贝银联充值回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.UPMP, true);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonRecharge(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "融信优贝银联充值回调失败");
        } finally {
            LogContext.instance().info("融信优贝银联充值回调结果:" + isSuccess);
            LogContext.instance().info("融信优贝银联充值回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    @RequestMapping(value = "upmp_consume", method = RequestMethod.POST)
    @ResponseBody
    public String upmpConsume(HttpServletRequest request) {
        boolean isSuccess = false;
        try {
            initLogger();
            LogContext.instance().info("融信优贝银联直充回调开始");
            PayTypeCallBackResponse response = getResponse(request, PayType.UPMP, false);
            if (response == null)
                return "fail";
            isSuccess = payTypeCallBackService.commonConsume(response);
        } catch (Exception e) {
            LogContext.instance().error(e, "融信优贝银联直充回调失败");
        } finally {
            LogContext.instance().info("融信优贝银联直充回调结果:" + isSuccess);
            LogContext.instance().info("融信优贝银联直充回调结束");
        }
        return isSuccess ? "success" : "fail";
    }

    private PayTypeCallBackResponse getResponse(HttpServletRequest request, PayType payType, boolean isRecharge) {
        IPayTypeService payTypeService;
        payTypeService = payTypeFactory.getPayTypeService(payType);
        PayTypeCallBackResponse response = payTypeService.getPayTypeCallBackResult(request, isRecharge, payType);
        if (response == null) {
            LogContext.instance().warn(payType.getName() + "回调结果对象为空");
            return null;
        } else {
            LogContext.instance().info(payType.getName() + "回调结果对象:" + response);
            return response;
        }
    }

    private void initLogger() {
        LogContext logContext = LogContext.instance();
        logContext.clear();
        logContext.setRequestUUID(CodecUtils.getRequestUUID());
        logContext.setLoggerName(CommonLoggerNameConstants.PAY_TYPE_CALL_BACK_LOGGER);
    }

}

