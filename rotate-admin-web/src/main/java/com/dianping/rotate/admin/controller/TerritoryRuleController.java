package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.serviceAgent.TerritoryRuleServiceAgent;
import com.dianping.rotate.territory.dto.TerritoryRuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by dev_wzhang on 15-1-22.
 */
@Controller
@RequestMapping("/territory")
public class TerritoryRuleController {

    @Autowired
    private TerritoryRuleServiceAgent territoryRuleServiceAgent;

    /**
     * 查询战区继承的规则
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryExtendsTerritoryRule",method = RequestMethod.POST)
    @ResponseBody
    public List<TerritoryRuleDto> queryExtendsTerritoryRule(@RequestParam Integer territoryId){

        return territoryRuleServiceAgent.getExtendsRuleByTerritoryId(territoryId);
    }

    /**
     * 查询战区自身定义的规则
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryOwnerTerritoryRule",method = RequestMethod.POST)
    @ResponseBody
    public List<TerritoryRuleDto> queryOwnerTerritoryRule(@RequestParam Integer territoryId){

        return territoryRuleServiceAgent.getOwnerRuleByTerritoryId(territoryId);
    }
}
