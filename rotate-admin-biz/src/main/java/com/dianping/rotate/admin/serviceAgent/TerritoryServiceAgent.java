package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryTreeDto;

import java.util.List;

/**
 * Created by shenyoujun on 15/1/20.
 */
public interface TerritoryServiceAgent {

    List<TerritoryDto> queryChildTerritoriesByTerritoryId(Integer territoryId);

    /**
     * 查询给定节点的战区面包屑
     * @param territoryId
     * @return
     */
    List<TerritoryDto> queryTerritoriyBreadCrumbsByTerritoryId(Integer territoryId);

    TerritoryTreeDto loadFullTerritoryTree();

    boolean deleteTerritory(Integer territoryId,int operatorId);


    Response<Integer> create(TerritoryForWebDto territoryForWebDto);

    List<TerritoryDto> queryAllValidTerritory();

    Response<Integer> update(TerritoryForWebDto territoryForWebDto);
}
