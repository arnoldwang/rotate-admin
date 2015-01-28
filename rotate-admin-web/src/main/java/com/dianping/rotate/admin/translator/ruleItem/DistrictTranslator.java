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
public class DistrictTranslator extends AbstractRuleItemTranslator {
    @Autowired
    private CityService cityService;
    @Autowired
    private RegionService regionService;

    @Override
    public Object encode(Integer v) {
        Map map = Maps.newHashMap();
        Region region = regionService.loadRegion(v);
        map.put("districtId",region.getRegionId());
        map.put("districtName",region.getRegionName());
        map.put("cityId",region.getCityId());
        map.put("cityName",cityService.loadCity(region.getCityId()).getCityName());
        return map;
    }

    @Override
    public Integer decode(Object o) {
        return (Integer)((Map)o).get("districtId");
    }
}
