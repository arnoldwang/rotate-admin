package com.dianping.rotate.admin.controller;

import com.dianping.apollobase.api.Group;
import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.combiz.entity.Category;
import com.dianping.combiz.entity.City;
import com.dianping.combiz.entity.Province;
import com.dianping.combiz.service.CategoryService;
import com.dianping.combiz.service.CityService;
import com.dianping.combiz.service.ProvinceService;
import com.dianping.combiz.service.RegionService;
import com.dianping.combiz.service.filter.CityFilter;
import com.dianping.combiz.util.CodeConstants;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import com.dianping.rotate.admin.serviceAgent.CategoryServiceAgent;
import com.dianping.rotate.admin.serviceAgent.UserServiceAgent;
import com.dianping.rotate.shop.constants.ApolloShopStatusEnum;
import com.dianping.rotate.shop.constants.ApolloShopTypeEnum;
import com.dianping.rotate.territory.enums.RuleTypeEnum;
import com.dianping.rotate.territory.enums.RunStatusEnum;
import com.dianping.rotate.territory.enums.TerritoryOperateType;
import com.dianping.rotate.territory.enums.TerritoryRulePropertyEnum;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by shenyoujun on 15/1/21.
 */
@Controller
@RequestMapping("/common")
public class CommonDataController {

    public static Map<TerritoryRulePropertyEnum,String> SimpleTerritoryRulePropertyTypeMap =  Maps.newHashMap();

    static {
        SimpleTerritoryRulePropertyTypeMap.put(TerritoryRulePropertyEnum.Province,"province");
        SimpleTerritoryRulePropertyTypeMap.put(TerritoryRulePropertyEnum.ShopStatus,"apolloShopStatus");
        SimpleTerritoryRulePropertyTypeMap.put(TerritoryRulePropertyEnum.Type,"apolloShopType");
    }




    public static final Integer  SimpleTerritoryRulePropertyType = 1;
    public static final Integer  ComplexTerritoryRulePropertyType = 2;


    public static final int CATEGORY_CITY_ID = 1;

    @Autowired
    private UserServiceAgent userServiceAgent;

    @Autowired
    private CategoryServiceAgent categoryServiceAgent;

    @Autowired
    private ApolloBaseServiceAgent apolloBaseServiceAgent;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;


    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    @ResponseBody
    public List<UserDto> searchUser(@RequestParam("userName") String userName){
        return userServiceAgent.searchUser(userName);
    }

    @RequestMapping(value = "/fuzzySearchCitiesByName", method = RequestMethod.GET)
    @ResponseBody
    public List<City> fuzzySearchCitiesByName(final String cityName) {
        CityFilter filter = new CityFilter() {
            @Override
            public boolean accept(City city) {

                return (StringUtils.isNotEmpty(city.getCityName()) && StringUtils.isNotEmpty(cityName) &&
                        city.getCityName().contains(cityName)) || (
                        StringUtils.isNotEmpty(city.getCityEnName()) && StringUtils.isNotEmpty(cityName) &&
                                city.getCityEnName().contains(cityName)
                );
            }
        };
        Comparator<City> sort = new
                Comparator<City>() {
                    @Override
                    public int compare(City city, City city2) {
                        return city.getCityOrderID() - city2.getCityOrderID();
                    }
                };
        return cityService.findCityList(filter, sort);
    }


    private List<Map<String, Object>> getAllBizInfo() {
        List<Map<String, Object>> result = Lists.newArrayList();
        Map<Integer, Group> bizs = apolloBaseServiceAgent.getBizGroups();

        for (Integer bizId: bizs.keySet() ) {
            Map<String, Object> o = Maps.newHashMap();
            o.put("value", bizId);
            o.put("text", bizs.get(bizId).getGroupName());
            result.add(o);
        }

        return result;
    }

    @RequestMapping(value = "/enums")
    @ResponseBody
    public Object getEnum() {
        Map result = new HashMap();


        result.put("biz", getAllBizInfo());
        result.put("runStatus",getRunStatusEnums());
        result.put("operateType",getOperateTypeEnums());
        result.put("categoryList",Lists.transform(categoryServiceAgent.getDistinctCategories(), new Function<Category, Object>() {
            @Override
            public Object apply(Category category) {
                Map map = Maps.newHashMap();
                map.put("text",category.getName());
                map.put("value",category.getId());
                return map;
            }
        }))     ;

        buildTerritoryRuleProperty(result);

        return result;

    }

    private Object getOperateTypeEnums() {
        List result = new ArrayList();
        for(TerritoryOperateType a: TerritoryOperateType.values()){
            Map map = Maps.newHashMap();
            map.put("text",a.getText());
            map.put("value",a.getCode());
            result.add(map);
        }
        return result;
    }

    private Object getRunStatusEnums() {
        List result = new ArrayList();
        for(RunStatusEnum a: RunStatusEnum.values()){
            Map map = Maps.newHashMap();
            map.put("text",a.getDesc());
            map.put("value",a.getCode());
            result.add(map);
        }
        return result;
    }

    private void buildTerritoryRuleProperty(Map result) {

        List<Map> list = new ArrayList<Map>();
        for(TerritoryRulePropertyEnum t:TerritoryRulePropertyEnum.values()){
            Map map = Maps.newHashMap();
            map.put("text",t.getText());
            map.put("value",t.getKey());

            if(SimpleTerritoryRulePropertyTypeMap.get(t)!=null){
                map.put("type",SimpleTerritoryRulePropertyType);
                map.put("enumKey",SimpleTerritoryRulePropertyTypeMap.get(t));
            }else{
                map.put("type",ComplexTerritoryRulePropertyType);
            }
            list.add(map);
        }


        result.put("territoryRuleProperty",list);
        result.put(SimpleTerritoryRulePropertyTypeMap.get(TerritoryRulePropertyEnum.Province), getProvince());
        result.put(SimpleTerritoryRulePropertyTypeMap.get(TerritoryRulePropertyEnum.Type), getApolloShopType());
        result.put(SimpleTerritoryRulePropertyTypeMap.get(TerritoryRulePropertyEnum.ShopStatus), getApolloShopStatus());
        result.put("ruleType", getRuleType());
    }

    private Object getApolloShopStatus() {
        List result = new ArrayList();
        for(ApolloShopStatusEnum a: ApolloShopStatusEnum.values()){
            Map map = Maps.newHashMap();
            map.put("text",a.getDesc());
            map.put("value",a.getCode());
            result.add(map);
        }
        return result;
    }

    private Object getApolloShopType() {
        List result = new ArrayList();
        for(ApolloShopTypeEnum a: ApolloShopTypeEnum.values()){
            Map map = Maps.newHashMap();
            map.put("text",a.getDesc());
            map.put("value",a.getCode());
            result.add(map);
        }
        return result;
    }


    private Object getProvince() {
        return Lists.transform(provinceService.findProvinceList(null, null), new Function<Province, Object>() {
            @Override
            public Object apply(Province province) {
                Map map = Maps.newHashMap();
                map.put("text",province.getProvinceName());
                map.put("value",province.getProvinceID());
                return map;
            }
        });
    }


    @RequestMapping(value = "/getRegionListByCityId", method = RequestMethod.GET)
    @ResponseBody
    public Object getRegionListByCityId(Integer cityId) {
        return regionService.findRegionListByCityId(cityId, CodeConstants.RegionType.District);
    }

    @RequestMapping(value = "/getMainRegionListByRegionId", method = RequestMethod.GET)
    @ResponseBody
    public Object getMainRegionListByRegionId(Integer districtId) {
        return regionService.findChildRegionList(districtId, CodeConstants.RegionType.BusinessDistrict,true);
    }


    @RequestMapping(value = "/getChildrenCategoryListByCategoryId", method = RequestMethod.GET)
    @ResponseBody
    public Object getChildrenCategoryListByCategoryId(Integer categoryId) {
        if(categoryId==null){
            return categoryService.getRootCategories(CATEGORY_CITY_ID);
        }
        return categoryService.getCategoryListByParentCategoryId(categoryId,CATEGORY_CITY_ID);
    }

    public Object getRuleType() {
        List result = new ArrayList();
        for(RuleTypeEnum ruleTypeEnum:RuleTypeEnum.values()){
            Map map = Maps.newHashMap();
            map.put("text",ruleTypeEnum.getName());
            map.put("value",ruleTypeEnum.getId());
            result.add(map);
        }
        return result;
    }


}
