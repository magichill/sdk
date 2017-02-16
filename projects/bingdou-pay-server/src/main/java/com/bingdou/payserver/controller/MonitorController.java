package com.bingdou.payserver.controller;

import com.bingdou.core.helper.BaseMonitorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * ½Ó¿ÚÌ½²â¼à¿Ø¿ØÖÆÆ÷
 * Created by gaoshan on 16/12/19.
 */
@RequestMapping("monitor")
@Controller
public class MonitorController extends BaseMonitorController {

    @RequestMapping("")
    public String monitor(HttpServletRequest request) {
        return monitor(request, false);
    }

}
