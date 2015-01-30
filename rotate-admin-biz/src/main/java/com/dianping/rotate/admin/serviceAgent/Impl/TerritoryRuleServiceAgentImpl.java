package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.combiz.entity.Category;
import com.dianping.combiz.entity.City;
import com.dianping.combiz.entity.Region;
import com.dianping.combiz.service.CategoryService;
import com.dianping.combiz.service.CityService;
import com.dianping.combiz.service.ProvinceService;
import com.dianping.combiz.service.RegionService;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryRuleServiceAgent;
import com.dianping.rotate.shop.constants.ApolloShopStatusEnum;
import com.dianping.rotate.shop.constants.ApolloShopTypeEnum;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.api.TerritoryRuleService;
import com.dianping.rotate.territory.dto.TerritoryRuleDto;
import com.dianping.rotate.territory.dto.TerritoryRuleItemDto;
import com.dianping.rotate.territory.enums.RuleTypeEnum;
import com.dianping.rotate.territory.enums.TerritoryRulePropertyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dev_wzhang on 15-1-22.
 */
//@Log4j
@Component
public class TerritoryRuleServiceAgentImpl implements TerritoryRuleServiceAgent {

    @Autowired
    private TerritoryRuleService territoryRuleService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProvinceService provinceService;


    @Override
    public List<TerritoryRuleDto> getExtendsRuleByTerritoryId(Integer territoryId) {
        if (territoryId == null || territoryId == 0) {
            throw new ApplicationException("战区ID未指定!");
        }
        return territoryRuleService.getExtendsRuleByTerritoryId(territoryId);
    }

    @Override
    public List<TerritoryRuleDto> getOwnerRuleWithItems(Integer territoryId) {
        if (territoryId == null || territoryId == 0) {
            throw new ApplicationException("战区ID未指定!");
        }
        return territoryRuleService.getOwnerRuleWithItems(territoryId);
    }

    @Override
    public List<TerritoryRuleDto> getOwnerRuleByTerritoryId(Integer territoryId) {
        if (territoryId == null || territoryId == 0) {
            throw new ApplicationException("战区ID未指定!");
        }

        return territoryRuleService.getOwnerRuleByTerritoryId(territoryId);
    }



    @Override
    public TerritoryRuleDto queryTerritoryRuleTips(Integer territoryId) {

        //01.查出规则
        List<TerritoryRuleDto> ruleDtoList = territoryRuleService.getOwnerRuleWithItems(territoryId);
        TerritoryRuleDto ruleDto = null;
        if (ruleDtoList != null && ruleDtoList.size() > 0) {
            ruleDto = ruleDtoList.get(0);
        }

        //02.替换规则内容
        String resultStr = replaceRuleItemContent(ruleDto);
        ruleDto.setRule(resultStr);
        ruleDto.setItems(null);
        return ruleDto;
    }

    @Override
    public Integer saveTerritoryRule(TerritoryRuleDto territoryRuleDto, int operatorId) {

        try {
            Response<Integer> r =  territoryRuleService.saveTerritoryRule(territoryRuleDto, operatorId);
            if(r.isSuccess()){
                return r.getObj();
            }
            throw new ApplicationException(r.getComment());
        } catch (Exception ex) {

            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public Boolean deleteTerritoryRule(int territoryId) {
        try {
            return territoryRuleService.deleteTerritoryRule(territoryId);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public Boolean runTerritoryRule(int territoryId,int operatorId) {
        try {
            territoryRuleService.saveTerritoryRunRule(territoryId,operatorId);

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return  Boolean.TRUE;
    }


    private String replaceRuleItemContent(TerritoryRuleDto ruleDto) {
        List<TerritoryRuleItemDto> ruleItemDtoList = ruleDto.getItems();
        Map<Integer, String> ruleItemMap = new HashMap<Integer, String>();

        for (TerritoryRuleItemDto item : ruleItemDtoList) {//循环替换每一个ruleitem
            TerritoryRulePropertyEnum enumItem = TerritoryRulePropertyEnum.getByKey(item.getField());

            item.setField(enumItem.getText());
            String concatStr = concatRuleItemStr(item, enumItem);
            ruleItemMap.put(item.getSequence(), concatStr);
        }

        String resultRuleStr = ruleDto.getRule();
        for (Integer key : ruleItemMap.keySet()) {
            if (resultRuleStr.contains(key.toString())) {
                resultRuleStr = resultRuleStr.replaceAll(key.toString(), ruleItemMap.get(key));
            }
        }

        return resultRuleStr;
    }

    private String concatRuleItemStr(TerritoryRuleItemDto item, TerritoryRulePropertyEnum enumItem) {
        String concatStr = "( " + enumItem.getText();
        String[] valueArr = new String[]{};
        if (item.getType() == RuleTypeEnum.Equals.getId()) {//Equal操作
            valueArr = item.getValue().split(",");
            concatStr += " " + RuleTypeEnum.Equals.getName() + " ";
        }

        for (String val : valueArr) {
            switch (enumItem) {
                case CityID:
                    City city = cityService.loadCity(Integer.parseInt(val));
                    if (city == null) {
                        throw new ApplicationException("无法解析城市：id=" + val);
                    }

                    concatStr += city.getCityName();
                    break;
                case MainRegionID:
                case District:
                    Region region = regionService.loadRegion(Integer.parseInt(val));
                    if (region == null) {
                        throw new ApplicationException("无法解析主区域：id=" + val);
                    }
                    concatStr += region.getRegionName();
                    break;
                case MainCategoryId:
                    Category category = categoryService.loadCategory(1, Integer.parseInt(val));
                    if (category == null) {
                        throw new ApplicationException("无法解析主分类：id=" + val);
                    }
                    concatStr += category.getName();
                    break;
                case Type:
                    concatStr += ApolloShopTypeEnum.getDescByCode(Integer.parseInt(val));
                    break;
                case Province:
                    concatStr +=    provinceService.loadById(Integer.parseInt(val)).getProvinceName();
                    break;
                case ShopStatus:
                    concatStr +=    ApolloShopStatusEnum.getDescByCode(Integer.parseInt(val));
                    break;

            }
            concatStr += ",";
        }

        return concatStr.substring(0, concatStr.length() - 1) + " )";
    }


}
