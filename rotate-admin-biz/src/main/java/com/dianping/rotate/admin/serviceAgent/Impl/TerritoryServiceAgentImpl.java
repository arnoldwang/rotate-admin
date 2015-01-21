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
    public TerritoryTreeDto loadFullTerritoryTree() {
        return territoryService.loadFullTerritoryTree();
    }

    @Override
    public boolean deleteTerritory(int territoryId) {
        boolean result = false;
        try {

            //TODO:01.查询长官

            //02.删除战区
            result = territoryService.delete(territoryId);

            //TODO:03.删除长官权限

        } catch (Exception ex) {
            throw new ApplicationException("战区服务异常,delete");
        }
        return result;
    }

    @Override
    public Integer create(TerritoryForWebDto territoryForWebDto) {
        try {
            Response<Integer> response = territoryService.create(territoryForWebDto);
            if(!response.isSuccess()) return null;
            return response.getObj();
        }
        catch (Exception e) {
            throw new ApplicationException("战区服务异常,create");
        }
    }
}
