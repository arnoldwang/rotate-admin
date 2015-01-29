package com.dianping.rotate.admin.translator.ruleItem;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
@Component
public class SimpleTranslator extends AbstractRuleItemTranslator {

    @Override
    public Object encode(Integer v) {

        Map map =  Maps.newHashMap();
        map.put("id", v);
        return map;
    }

    @Override
    public Integer decode(Object o) {
        return (Integer)((Map)o).get("id");
    }
}
