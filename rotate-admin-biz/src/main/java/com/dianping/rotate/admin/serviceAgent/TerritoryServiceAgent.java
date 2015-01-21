package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryTreeDto;

import java.util.List;

/**
 * Created by shenyoujun on 15/1/20.
 */
public interface TerritoryServiceAgent {

    List<TerritoryDto> queryChildTerritoriesByTerritoryId(Integer territoryId);

    TerritoryTreeDto loadFullTerritoryTree();

    boolean deleteTerritory(int territoryId);

    Integer create(TerritoryForWebDto territoryForWebDto);
}
