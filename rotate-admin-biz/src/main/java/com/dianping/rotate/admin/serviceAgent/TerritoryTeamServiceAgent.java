package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.territory.dto.TerritoryTeamDto;

import java.util.List;

/**
 * Created by dev_wzhang on 15-1-29.
 */
public interface TerritoryTeamServiceAgent {
    /**
     * 根据战区id获取小组
     * @param territoryId
     * @return
     */
    List<TerritoryTeamDto> getTeamByTerritoryId(Integer territoryId);
}
