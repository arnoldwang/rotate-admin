package com.dianping.rotate.admin.controller;

import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.combiz.entity.City;
import com.dianping.combiz.entity.Province;
import com.dianping.combiz.service.CategoryService;
import com.dianping.combiz.service.CityService;
import com.dianping.combiz.service.ProvinceService;
import com.dianping.combiz.service.RegionService;
import com.dianping.combiz.service.filter.CityFilter;
import com.dianping.combiz.util.CodeConstants;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import com.dianping.rotate.admin.serviceAgent.UserServiceAgent;
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

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/1/21.
 */
@Controller
@RequestMapping("/territory")
public class CommonDataController {


    public static final int CATEGORY_CITY_ID = 1;

    @Autowired
    private UserServiceAgent userServiceAgent;

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

    @RequestMapping(value = "/getBizInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map getBizInfo(){
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("bizInfo",apolloBaseServiceAgent.getAllBizInfo());
        return result;
    }


//    @RequestMapping(value = "/getEnum", method = RequestMethod.GET)
//    @ResponseBody
//    public Object getEnum(){
//       return cityService.findCities();
//    }




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



    @RequestMapping(value = "/getEnum", method = RequestMethod.GET)
    @ResponseBody
    public Object getEnum() {
        Map result = new HashMap();

        result.put("province",getProvince());

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


    @RequestMapping(value = "/getChildrenCategoryListByCategoryId", method = RequestMethod.GET)
    @ResponseBody
    public Object getChildrenCategoryListByCategoryId(Integer categoryId) {
        if(categoryId==null){
            return categoryService.getRootCategories(CATEGORY_CITY_ID);
        }
        return categoryService.getCategoryListByParentCategoryId(categoryId,CATEGORY_CITY_ID);
    }

}
