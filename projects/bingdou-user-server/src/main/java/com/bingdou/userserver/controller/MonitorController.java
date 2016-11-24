package com.bingdou.userserver.controller;

import com.bingdou.core.helper.BaseMonitorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaoshan on 16-11-24.
 */
@RequestMapping("/monitor")
@Controller
public class MonitorController extends BaseMonitorController{

    @RequestMapping("")
    public String monitor(HttpServletRequest request) {
        return monitor(request, false);
    }
}
