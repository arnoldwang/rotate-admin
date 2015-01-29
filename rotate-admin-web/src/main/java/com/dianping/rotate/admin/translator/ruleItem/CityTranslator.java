package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.combiz.service.CityService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
@Component
public class CityTranslator extends AbstractRuleItemTranslator {
    @Autowired
    private CityService cityService;

    @Override
    public Object encode(Integer v) {

        Map map = Maps.newHashMap();
        map.put("cityId",v);
        map.put("cityName",cityService.loadCity(v).getCityName());
        return map;
    }

    @Override
    public Integer decode(Object o) {
        return (Integer)((Map)o).get("cityId");
    }
}
