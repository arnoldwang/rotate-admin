package com.dianping.rotate.admin.service;

import com.dianping.rotate.admin.dto.TerritoryExtendDTO;

import java.util.List;

/**
 * Created by yangjie on 2/6/15.
 */
public interface TerritoryService {
    List<TerritoryExtendDTO> queryAllLeafTerritoriesWithExtend(Integer id);
}
