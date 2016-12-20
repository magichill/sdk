package com.bingdou.cdn.controller;

import com.bingdou.core.helper.BaseController;
import com.bingdou.tools.constants.CommonLoggerNameConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16/12/13.
 */
@RequestMapping("/cdn/server")
@Controller
public class ServerController extends BaseController {

    @RequestMapping(value = "{method}", method = RequestMethod.POST)
    @ResponseBody
    public String dispatch(HttpServletRequest request, @PathVariable("method") String method) throws Exception {
        return baseDispatch(request, CommonLoggerNameConstants.REQUEST_PARAM_LOG_NAME,method);
    }

    @Override
    protected boolean isServerRequest() {
        return true;
    }
}
