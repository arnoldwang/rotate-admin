package com.dianping.rotate.admin.service;

import com.dianping.rotate.admin.dto.TeamTerritoryDTO;
import com.dianping.rotate.org.dto.Team;

import java.util.List;

/**
 * Created by dev_wzhang on 15-1-19.
 */
public interface TeamTerritoryService {
    List<TeamTerritoryDTO> getTeamsByBizId(Integer bizId);


    List<Team> getAllTeamsByBizId(Integer bizId);
}