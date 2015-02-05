package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryRunStatusServiceAgent;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.api.TerritoryRunStatusService;
import com.dianping.rotate.territory.dto.TerritoryRunStatusForWebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenyoujun on 15/2/3.
 */
@Component
public class TerritoryRunStatusServiceAgentImpl implements TerritoryRunStatusServiceAgent {

    @Autowired
    private TerritoryRunStatusService territoryRunStatusService;
    @Override
    public List<TerritoryRunStatusForWebDto> getTerritoryRunListsByHistoryId(Integer runHistoryId) {
        try {
            return territoryRunStatusService.getTerritoryRunListsByHistoryId(runHistoryId);
        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,getTerritoryRunListsByHistoryId:"+e.getMessage());
        }
    }

    @Override
    public void reRunTerritoryRule(Integer id,Integer operatorId) {

        try {
            Response response = territoryRunStatusService.reRunLeafTerritory(id, operatorId);

            if(!response.isSuccess()) {
                throw new ApplicationException("重试任务失败_errorMsg:"+response.getComment());
            }
        } catch (ApplicationException e) {
            throw new ApplicationException("战区服务异常,reRunTerritoryRule:"+e.getMessage());

        }
    }
}
