package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.territory.dto.TerritoryRuleDto;

import java.util.List;

/**
 * Created by dev_wzhang on 15-1-22.
 */
public interface TerritoryRuleServiceAgent {

    /**
     * 查询战区继承的规则
     * @param territoryId
     * @return
     */
    List<TerritoryRuleDto> getExtendsRuleByTerritoryId(Integer territoryId);

    /**
     * 查询指定战区的规则(不包含继承的规则)
     * @param territoryId
     * @return
     */
    List<TerritoryRuleDto> getOwnerRuleByTerritoryId(Integer territoryId);
}
