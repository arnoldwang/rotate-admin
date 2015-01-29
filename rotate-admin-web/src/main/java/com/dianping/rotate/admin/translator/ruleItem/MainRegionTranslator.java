package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.combiz.entity.Region;
import com.dianping.combiz.service.CityService;
import com.dianping.combiz.service.RegionService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
@Component
public class MainRegionTranslator extends AbstractRuleItemTranslator {
    @Autowired
    private CityService cityService;
    @Autowired
    private RegionService regionService;

    @Override
    public Object encode(Integer v) {
        Map map = Maps.newHashMap();
        Region district = regionService.loadMainParentDistrict(v, true);
        Region region = regionService.loadRegion(v);
        map.put("districtId",district.getRegionId());
        map.put("districtName",district.getRegionName());
        map.put("mainRegionId",region.getRegionId());
        map.put("mainRegionName",region.getRegionName());
        map.put("cityId",region.getCityId());
        map.put("cityName",cityService.loadCity(district.getCityId()).getCityName());
        return map;
    }

    @Override
    public Integer decode(Object o) {
        return (Integer)((Map)o).get("mainRegionId");
    }
}
