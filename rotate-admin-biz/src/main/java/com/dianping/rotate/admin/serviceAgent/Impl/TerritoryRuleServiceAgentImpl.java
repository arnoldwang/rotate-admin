package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryRuleServiceAgent;
import com.dianping.rotate.territory.api.TerritoryRuleService;
import com.dianping.rotate.territory.dto.TerritoryRuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dev_wzhang on 15-1-22.
 */
@Component
public class TerritoryRuleServiceAgentImpl implements TerritoryRuleServiceAgent {

    @Autowired
    private TerritoryRuleService territoryRuleService;

    @Override
    public List<TerritoryRuleDto> getExtendsRuleByTerritoryId(Integer territoryId) {
        if(territoryId==null || territoryId==0){
            throw new ApplicationException("战区ID未指定!");
        }

        return territoryRuleService.getExtendsRuleByTerritoryId(territoryId);
    }

    @Override
    public List<TerritoryRuleDto> getOwnerRuleByTerritoryId(Integer territoryId) {
        if(territoryId==null || territoryId==0){
            throw new ApplicationException("战区ID未指定!");
        }

        return territoryRuleService.getOwnerRuleByTerritoryId(territoryId);
    }
}
