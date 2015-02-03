package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.territory.dto.TerritoryRuleItemDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
public abstract class AbstractRuleItemTranslator implements RuleItemTranslator {
    @Override
    public Map toVO(TerritoryRuleItemDto t) {
        Map result = Maps.newHashMap();
        result.put("type",t.getType());
        result.put("field",t.getField());
        String[] value = t.getValue().split(",");
        List values = Lists.newArrayList();
        for(String v:value){
            values.add(encode(Integer.parseInt(v)));
        }
        result.put("value",values);
        return result;
    }

    public abstract Object encode(Integer v);

    @Override
    public TerritoryRuleItemDto toDto(Map t) {
        TerritoryRuleItemDto r = new TerritoryRuleItemDto();
        r.setField(t.get("field").toString());
        r.setType((Integer)t.get("type"));
        List list = (List)t.get("value");
        if(CollectionUtils.isEmpty(list)){
            throw new ApplicationException("请至少选择一个选项");
        }

        List<Integer> value = Lists.newArrayList();
        for(Object o:list){
            Integer rr = decode(o);
            if(value.contains(rr)){
                throw new ApplicationException("一个选项里面有重复的值");
            }
            value.add(rr);
        }
        r.setValue(StringUtils.join(value,","));
        return r;
    }

    public abstract Integer decode(Object o);
}
