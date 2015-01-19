package com.dianping.rotate.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dev_wzhang on 15-1-19.
 */
@Controller
public class WelcomeController {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String welcome(Model model){
        return "welcome";
    }
}
