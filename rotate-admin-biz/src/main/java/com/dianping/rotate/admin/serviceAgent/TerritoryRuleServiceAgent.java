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


    List<TerritoryRuleDto> getOwnerRuleWithItems(Integer territoryId);
    /**
     * 查询指定战区的规则(不包含继承的规则)
     * @param territoryId
     * @return
     */
    List<TerritoryRuleDto> getOwnerRuleByTerritoryId(Integer territoryId);

    /**
     * 查询战区的规则描述
     * @param territoryId
     * @return
     */
    public TerritoryRuleDto queryTerritoryRuleTips(Integer territoryId);

    /**
     * 保存战区规则
     * @param territoryRuleDto
     * @return
     */
    public TerritoryRuleDto saveTerritoryRule(TerritoryRuleDto territoryRuleDto,int operatorId);

    /**
     * 删除战区规则
     * @param territoryId:战区id
     * @return
     */
    public Boolean deleteTerritoryRule(int territoryId);

    /**
     * 运行战区规则
     * @param territoryId
     * @return
     */
    public Boolean runTerritoryRule(int territoryId,int operatorId);
}
