package com.dianping.rotate.admin.controller;

import com.dianping.remote.share.Translator;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryRuleServiceAgent;
import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import com.dianping.rotate.admin.translator.ruleItem.*;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryRuleDto;
import com.dianping.rotate.territory.dto.TerritoryRuleItemDto;
import com.dianping.rotate.territory.enums.TerritoryRulePropertyEnum;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by dev_wzhang on 15-1-22.
 */
@Controller
@RequestMapping("/territory")
public class TerritoryRuleController {

    @Autowired
    private TerritoryRuleServiceAgent territoryRuleServiceAgent;


    @Autowired
    SimpleTranslator simpleTranslator;


    @Autowired
    CityTranslator cityTranslator;


    @Autowired
    DistrictTranslator districtTranslator;


    @Autowired
    MainCategoryTranslator mainCategoryTranslator;


    @Autowired
    MainRegionTranslator mainRegionTranslator;


    @Autowired
    TerritoryServiceAgent territoryServiceAgent;


    /**
     * 查询战区继承的规则
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryExtendsTerritoryRule",method = RequestMethod.GET)
    @ResponseBody
    public Object queryExtendsTerritoryRule(@RequestParam Integer territoryId){


        return territoryRuleServiceAgent.getExtendsRuleByTerritoryId(territoryId);


    }

    private Object buildTerritoryRuleResult(TerritoryRuleDto t) {
//
//        "id": 1,
//                "rule": "1",
//                "ruleName": "123",
//                "territoryId": 1,
//                "items": [
        Map result = Maps.newHashMap();

        result.put("id",t.getId());
        result.put("rule",t.getRule());
        result.put("territoryId",t.getTerritoryId());
        result.put("ruleName",t.getRuleName());

        result.put("items",buildTerritoryRuleItemsResult(t.getItems()));

        return result;
    }

    private Object buildTerritoryRuleItemsResult(List<TerritoryRuleItemDto> items) {
        if(CollectionUtils.isNotEmpty(items)){
            Collections.sort(items, new Comparator<TerritoryRuleItemDto>() {
                @Override
                public int compare(TerritoryRuleItemDto t, TerritoryRuleItemDto t1) {
                    return t.getSequence()-t1.getSequence();
                }
            });

            List result = new ArrayList();

            for(TerritoryRuleItemDto item:items){
                result.add(getRuleItemTranslator(item.getField()).toVO(item));
            }
            return result;
        }

        return Collections.emptyList();
    }


    public RuleItemTranslator getRuleItemTranslator(String field){
        for(TerritoryRulePropertyEnum t : TerritoryRulePropertyEnum.values()){
            if(t.getKey().equals(field)){
                String s = CommonDataController.SimpleTerritoryRulePropertyTypeMap.get(t);
                if(s!=null){
                    return simpleTranslator;
                }else{
                    if(t==TerritoryRulePropertyEnum.CityID){
                        return cityTranslator;
                    }else if(t==TerritoryRulePropertyEnum.MainRegionID){
                        return mainRegionTranslator;
                    }else if(t==TerritoryRulePropertyEnum.District){
                        return districtTranslator;
                    }else if(t==TerritoryRulePropertyEnum.MainCategoryId){

                        return mainCategoryTranslator;
                    }
                }
            }
        }
        throw new ApplicationException(String.format("根据%s找不到RuleItemTranslator",field));
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
    public Object queryTerritoryRuleWithItem(@RequestParam Integer territoryId){
        List<TerritoryRuleDto> dtos = territoryRuleServiceAgent.getOwnerRuleWithItems(territoryId);
        if(CollectionUtils.isNotEmpty(dtos)){
            TerritoryRuleDto t = dtos.get(0);
            return buildTerritoryRuleResult(t);
        }else{
            TerritoryForWebDto t = territoryServiceAgent.loadTerritoryInfoForWeb(territoryId);
            if(t==null){
                throw new ApplicationException("战区%s不存在",territoryId);
            }
            Map result = Maps.newHashMap();
            result.put("territoryId",t.getId());
            result.put("ruleName",t.getTerritoryName());
            return result;
        }
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

    /**
     * 删除规则
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/deleteTerritoryRule",method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteTerritoryRule(@RequestParam Integer territoryId){
        return  territoryRuleServiceAgent.deleteTerritoryRule(territoryId);
    }

    /**
     * 保存规则
     * @return
     */
    @RequestMapping(value = "/saveTerritoryRule",method = RequestMethod.POST)
    @ResponseBody
    public Integer saveTerritoryRule(@RequestBody Map map){
        TerritoryRuleDto t = new TerritoryRuleDto();

        t.setTerritoryId((Integer) map.get("territoryId"));
        t.setRule((String) map.get("rule"));
        t.setId((Integer) map.get("id"));
        List<TerritoryRuleItemDto> items = new ArrayList<TerritoryRuleItemDto>();
        int i=1;
        for(Map item:(List<Map>)map.get("items")){
            TerritoryRuleItemDto t1 = getRuleItemTranslator((String) item.get("field")).toDto(item);
            t1.setSequence(i++);
            items.add(t1);
        }
        t.setItems(items);
        return  territoryRuleServiceAgent.saveTerritoryRule(t, LoginUtils.getUserLoginId());
    }
}
