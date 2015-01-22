package com.dianping.rotate.admin.controller;

import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.combiz.service.CityService;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import com.dianping.rotate.admin.serviceAgent.UserServiceAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/1/21.
 */
@Controller
@RequestMapping("/territory")
public class CommonDataController {

    @Autowired
    private UserServiceAgent userServiceAgent;

    @Autowired
    private ApolloBaseServiceAgent apolloBaseServiceAgent;

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDto> searchUser(@RequestParam("userName") String userName){
        return userServiceAgent.searchUser(userName);
    }

    @RequestMapping(value = "/getBizInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map getBizInfo(){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("bizInfo",apolloBaseServiceAgent.getAllBizInfo());
        return result;
    }


//    @RequestMapping(value = "/getEnum", method = RequestMethod.GET)
//    @ResponseBody
//    public Map getEnum(){
//        Map<String,Object> result = new HashMap<String, Object>();
//        result.put("bizInfo",apolloBaseServiceAgent.getAllBizInfo());
//        return result;
//    }

}
