package com.bingdou.cdn.controller;

import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-14.
 */
@RequestMapping("cdn")
@Controller
public class CdnController extends CdnBaseController {

    @RequestMapping(value = "/api", method = RequestMethod.POST)
    @ResponseBody
    public String cdnServer(HttpServletRequest request) throws Exception {
        String result = baseDispatch(request, CommonLoggerNameConstants.REQUEST_PARAM_LOG_NAME);
        return result;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    @ResponseBody
    public String notify(HttpServletRequest request) throws Exception {
        String result = "ok";
        return result;
    }
}
