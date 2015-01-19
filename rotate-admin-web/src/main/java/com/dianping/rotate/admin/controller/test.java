package com.dianping.rotate.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shenyoujun on 15/1/19.
 */
@Controller
@RequestMapping("/territory")
public class test {

    @RequestMapping("/aaa")
    @ResponseBody
    public Object test(){

        return "1";
    }
}
