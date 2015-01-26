package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.serviceAgent.TerritoryRuleServiceAgent;
import com.dianping.rotate.territory.dto.TerritoryRuleDto;
import com.dianping.rotate.territory.dto.TerritoryRuleItemDto;
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
    @RequestMapping(value = "/queryExtendsTerritoryRule",method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryRuleDto> queryExtendsTerritoryRule(@RequestParam Integer territoryId){

        return territoryRuleServiceAgent.getExtendsRuleByTerritoryId(territoryId);
    }

    /**
     * 查询战区自身定义的规则
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryOwnerTerritoryRule",method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryRuleDto> queryOwnerTerritoryRule(@RequestParam Integer territoryId){

        return territoryRuleServiceAgent.getOwnerRuleByTerritoryId(territoryId);
    }

    /**
     * 查询规则明细
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryTerritoryRuleWithItem",method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryRuleDto> queryTerritoryRuleWithItem(@RequestParam Integer territoryId){
        return  territoryRuleServiceAgent.getOwnerRuleByTerritoryId(territoryId);
    }



    /**
     * 查询规则明细tips
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryTerritoryRuleTips",method = RequestMethod.GET)
    @ResponseBody
    public TerritoryRuleDto queryTerritoryRuleTips(@RequestParam Integer territoryId){
        return  territoryRuleServiceAgent.queryTerritoryRuleTips(territoryId);
    }


}
