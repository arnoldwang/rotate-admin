package com.dianping.rotate.admin.controller;

import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.rotate.admin.serviceAgent.UserServiceAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by shenyoujun on 15/1/21.
 */
@Controller
@RequestMapping("/territory")
public class CommonDataController {

    @Autowired
    private UserServiceAgent userServiceAgent;

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDto> searchUser(@RequestParam("userName") String userName){
        return userServiceAgent.searchUser(userName);
    }
}
