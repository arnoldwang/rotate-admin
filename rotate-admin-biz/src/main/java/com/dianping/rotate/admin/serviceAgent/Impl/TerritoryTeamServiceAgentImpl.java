package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryTeamServiceAgent;
import com.dianping.rotate.territory.api.TeamTerritoryService;
import com.dianping.rotate.territory.dto.TerritoryTeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dev_wzhang on 15-1-29.
 */
@Component
public class TerritoryTeamServiceAgentImpl implements TerritoryTeamServiceAgent {

    @Autowired
    private TeamTerritoryService teamTerritoryService;


    @Override
    public List<TerritoryTeamDto> getTeamByTerritoryId(Integer territoryId) {
        try {
            return teamTerritoryService.getTeamByTerritoryId(territoryId);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
