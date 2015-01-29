package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.combiz.service.CityService;
import com.dianping.rotate.admin.exceptions.ApplicationException;
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

        Integer v = (Integer)((Map)o).get("cityId");
        if(v==null){
            throw new ApplicationException("请选择城市");
        }
        return  v;
    }
}
