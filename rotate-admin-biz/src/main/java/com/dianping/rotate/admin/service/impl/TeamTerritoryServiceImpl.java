package com.dianping.rotate.admin.service.impl;

import com.dianping.apollobase.api.Group;
import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.rotate.admin.dto.TeamTerritoryDTO;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.service.TeamTerritoryService;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import com.dianping.rotate.org.api.TeamService;
import com.dianping.rotate.org.dto.Team;
import com.dianping.rotate.territory.api.TerritoryChiefService;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 1/27/15.
 */
@Service
public class TeamTerritoryServiceImpl implements TeamTerritoryService {
    @Autowired
    private TeamService rotateTeamService;

    @Autowired
    private ApolloBaseServiceAgent apolloBaseServiceAgent;

    @Autowired
    private com.dianping.rotate.territory.api.TeamTerritoryService rotateTeamTerritoryService;

    @Autowired
    private TerritoryChiefService rotateTerritoryChiefService;

    @Autowired
    private UserService userService;

    private Integer getTeamIdByBizId(Integer bizId) {
        Map<Integer, Group> bizes =  apolloBaseServiceAgent.getBizGroups();
        try {
            return bizes.get(bizId).getDepartmentId();
        } catch (NullPointerException e) {
            throw new ApplicationException("biz id 不存在:" + bizId);
        }
    }

    @Override
    public List<TeamTerritoryDTO> getTeamsByBizId(Integer bizId) {
        Integer departmentId = getTeamIdByBizId(bizId);

        if (departmentId == null) {
            return Lists.newArrayList();
        }

        return Lists.transform(rotateTeamService.getLeafTeamsByDepartmentId(departmentId), new Function<Team, TeamTerritoryDTO>() {
            @Override
            public TeamTerritoryDTO apply(Team team) {
                return buildTeamTerritory(team);
            }
        });
    }

    private TeamTerritoryDTO buildTeamTerritory(Team team) {
        TeamTerritoryDTO dto = new TeamTerritoryDTO();

        dto.setTeamName(team.getTeamName());

        TerritoryDto territory = rotateTeamTerritoryService.getTerritoryByTeamId(team.getTeamID());

        // 可能还没有绑定战区
        if (territory != null) {
            dto.setTerritoryId(territory.getId());
            dto.setTerritoryName(territory.getTerritoryName());

            Integer userId = rotateTerritoryChiefService.getUserByTerritoryId(territory.getId());

            // 可能还没有战区长官
            if (userId != null) {
                dto.setTerritoryChiefName(userService.queryUserByLoginID(userId).getRealName());
            }
        }

        return dto;
    }
}
