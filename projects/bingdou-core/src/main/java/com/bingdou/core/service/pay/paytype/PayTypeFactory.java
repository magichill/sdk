package com.bingdou.core.service.pay.paytype;

import com.bingdou.core.model.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayTypeFactory {

    @Autowired
    private WxPayTypeService wxPayTypeService;

    @Autowired
    private WxPublicPayTypeService wxPublicPayTypeService;
//    @Autowired
//    private PPPayTypeService ppPayTypeService;
//    @Autowired
//    private UpmpPayTypeService upmpPayTypeService;
//    @Autowired
//    private CardPayTypeService cardPayTypeService;
    @Autowired
    private AliMobilePayTypeService aliMobilePayTypeService;
//    @Autowired
//    private HeepayWxPayTypeService heepayWxPayTypeService;
//    @Autowired
//    private HeepaySdkWxPayTypeService heepaySdkWxPayTypeService;
    @Autowired
    private AliPayScanPayTypeService aliPayScanPayTypeService;
//    @Autowired
//    private ChinaPayUnionPayTypeService chinaPayUnionPayTypeService;
    @Autowired
    private AliNoPwdPayTypeService aliNoPwdPayTypeService;

    public IPayTypeService getPayTypeService(PayType payType) {
//        if (PayType.UPMP.equals(payType)) {
//            return upmpPayTypeService;
//        } else if (PayType.PAY_19.equals(payType)) {
//            return cardPayTypeService;
//        } else if (PayType.WEIXIN.equals(payType)) {
//            return wxPayTypeService;
//        } else if (PayType.PP.equals(payType)) {
//            return ppPayTypeService;
//        } else if (PayType.ALI_MOBILE.equals(payType)) {
//            return aliMobilePayTypeService;
//        } else if (PayType.HEEPAY_WEIXIN_WAP.equals(payType)
//                || PayType.HEEPAY_WEIXIN_SCAN_CODE.equals(payType)) {
//            return heepayWxPayTypeService;
//        } else if (PayType.ALI_SCAN.equals(payType)) {
//            return aliPayScanPayTypeService;
//        } else if (PayType.CHINA_PAY_UNION.equals(payType)) {
//            return chinaPayUnionPayTypeService;
//        } else if (PayType.HEEPAY_WEIXIN_SDK.equals(payType)) {
//            return heepaySdkWxPayTypeService;
//        } else if (PayType.ALI_NO_PWD.equals(payType)) {
//            return aliNoPwdPayTypeService;
//        }

        if (PayType.WEIXIN.equals(payType)){
            return wxPayTypeService;
        } else if( PayType.PUBLIC_WEIXIN.equals(payType)){
            return wxPublicPayTypeService;
        }else if (PayType.ALI_MOBILE.equals(payType)){
            return aliMobilePayTypeService;
        } else if(PayType.ALI_SCAN.equals(payType)){
            return aliPayScanPayTypeService;
        } else if (PayType.ALI_NO_PWD.equals(payType)) {
            return aliNoPwdPayTypeService;
        }
        return null;
    }

}
