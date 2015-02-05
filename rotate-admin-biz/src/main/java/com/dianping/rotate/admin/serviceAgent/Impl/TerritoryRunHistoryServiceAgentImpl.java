package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.core.type.PageModel;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryRunHistoryServiceAgent;
import com.dianping.rotate.territory.api.TerritoryRunHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenyoujun on 15/2/3.
 */
@Component
public class TerritoryRunHistoryServiceAgentImpl implements TerritoryRunHistoryServiceAgent {

    @Autowired
    private TerritoryRunHistoryService territoryRunHistoryService;
    @Override
    public PageModel queryTerritoryRunHistoryLists(int pageIndex, int pageSize) {

        try {
            return territoryRunHistoryService.queryTerritoryRunHistoryLists(pageIndex,pageSize);
        } catch (Exception e) {
            throw new ApplicationException("战区服务异常,queryTerritoryRunHistoryLists:"+e.getMessage());
        }
    }
}
