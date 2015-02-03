package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.territory.dto.TerritoryRunStatusForWebDto;

import java.util.List;

/**
 * Created by shenyoujun on 15/2/3.
 */
public interface TerritoryRunStatusServiceAgent {
    List<TerritoryRunStatusForWebDto> getTerritoryRunListsByHistoryId(Integer runHistoryId);
}
