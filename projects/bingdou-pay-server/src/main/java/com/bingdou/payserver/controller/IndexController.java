package com.bingdou.payserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页控制器
 * Created by gaoshan on 16/11/29.
 */
@RequestMapping("index")
@Controller
public class IndexController {

    @RequestMapping("")
    @ResponseBody
    public String index() {
        return "SDK PAY API SERVER";
    }

}
