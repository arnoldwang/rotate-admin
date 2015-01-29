package com.dianping.rotate.admin.translator.ruleItem;

import org.springframework.stereotype.Component;

/**
 * Created by feipeng on 15/1/28.
 */
@Component
public class SimpleTranslator extends AbstractRuleItemTranslator {

    @Override
    public Object encode(Integer v) {
        return v;
    }

    @Override
    public Integer decode(Object o) {
        return (Integer)o;
    }
}
