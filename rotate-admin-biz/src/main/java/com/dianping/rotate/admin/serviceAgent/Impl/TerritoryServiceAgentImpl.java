package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.api.TerritoryService;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryTreeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyoujun on 15/1/20.
 */
@Component
public class TerritoryServiceAgentImpl implements TerritoryServiceAgent {

    @Autowired
    private TerritoryService territoryService;

    @Override
    public List<TerritoryDto> queryChildTerritoriesByTerritoryId(Integer territoryId) {
        try {
            return territoryService.queryChildTerritoriesByTerritoryId(territoryId);

        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,queryChildTerritoriesByTerritoryId");
        }
    }

    @Override
    public List<TerritoryDto> queryTerritoriyBreadCrumbsByTerritoryId(Integer territoryId) {
        List<TerritoryDto> parentNodes = territoryService.queryParentTerritoriesByTerritoryId(territoryId);

        TerritoryDto currentNode = territoryService.query(territoryId);
        if (parentNodes == null) {
            parentNodes = new ArrayList<TerritoryDto>();
        }
        parentNodes.add(currentNode);
        return parentNodes;
    }

    @Override
    public TerritoryTreeDto loadFullTerritoryTree() {
        return territoryService.loadFullTerritoryTree();
    }

    @Override
    public boolean deleteTerritory(Integer territoryId, int operatorId) {
        boolean result = Boolean.FALSE;
        try {

            //TODO:01.查询长官

            //02.删除战区
            result = territoryService.delete(territoryId, operatorId);

            //TODO:03.删除长官权限

        } catch (Exception ex) {
            throw new ApplicationException("战区服务异常,delete");
        }
        return result;
    }

    @Override
    public Response<Integer> create(TerritoryForWebDto territoryForWebDto) {
        try {
            return territoryService.create(territoryForWebDto);
        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,create");
        }
    }

    @Override
    public List<TerritoryDto> queryAllValidTerritory() {
        try {
            return territoryService.queryAllValidTerritories();
        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,queryAllValidTerritory");
        }
    }

    @Override
    public Response<Integer> update(TerritoryForWebDto territoryForWebDto) {
        try {
            return territoryService.update(territoryForWebDto);
        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,update");
        }
    }

    @Override
    public TerritoryForWebDto loadTerritoryInfoForWeb(Integer territoryId) {
        try {
            return territoryService.loadTerritoryInfoForWeb(territoryId);
        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,loadTerritoryInfoForWeb");
        }
    }
}
