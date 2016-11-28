package com.bingdou.cdn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gaoshan on 16-11-26.
 */
@RequestMapping("index")
@Controller
public class IndexController {

    @RequestMapping("")
    @ResponseBody
    public String index() {
        return "SDK CDN API SERVER";
    }
}
