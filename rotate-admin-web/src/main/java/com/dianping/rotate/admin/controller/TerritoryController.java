package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dev_wzhang on 15-1-20.
 */
@Controller
@RequestMapping("/territory")
public class TerritoryController {

    @Autowired
    TerritoryServiceAgent territoryServiceAgent;
    /**
     * 删除战区
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody String deleteTerritory(@RequestParam int territoryId,@RequestParam int operatorId){

        boolean result = territoryServiceAgent.deleteTerritory(territoryId,operatorId);
        return StringUtils.EMPTY;
    }
}
