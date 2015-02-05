package com.dianping.rotate.admin.translator.ruleItem;

import com.dianping.rotate.territory.dto.TerritoryRuleItemDto;

import java.util.Map;

/**
 * Created by feipeng on 15/1/28.
 */
public interface RuleItemTranslator {

    Map toVO(TerritoryRuleItemDto t);

    TerritoryRuleItemDto toDto(Map t);
}
