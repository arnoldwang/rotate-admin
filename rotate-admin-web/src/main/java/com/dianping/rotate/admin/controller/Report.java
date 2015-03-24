package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.serviceAgent.CrossBuShopReportServiceAgent;
import com.dianping.rotate.admin.vo.SearchShopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by shenyoujun on 15/3/23.
 */
@Controller
@RequestMapping("/report")
public class Report {


    @Autowired
    CrossBuShopReportServiceAgent crossBuShopReportServiceAgent;

    @RequestMapping(value = "/searchShop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryShopInfo(@RequestBody SearchShopVo searchShopVo){

        Map<String, Object> result = crossBuShopReportServiceAgent.getCrossBuShopReport(searchShopVo);

        return result;
    }
}
