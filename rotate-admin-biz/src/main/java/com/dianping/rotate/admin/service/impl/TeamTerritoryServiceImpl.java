package com.dianping.rotate.admin.service.impl;

import com.dianping.apollobase.api.Group;
import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
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
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
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

        List<Team> teams = rotateTeamService.getLeafTeamsByDepartmentId(departmentId);

        return buildTeamTerritories(teams);


    }

    private List<TeamTerritoryDTO> buildTeamTerritories(List<Team> teams) {
        if (CollectionUtils.isEmpty(teams)) {
            return Lists.newArrayList();
        }

        final Map<Integer, TerritoryDto> teamIdToTerritoryMap = rotateTeamTerritoryService.getTeamToTerritoryMap(Lists.transform(teams, new Function<Team, Integer>() {
            @Override
            public Integer apply(Team input) {
                return input.getTeamID();
            }
        }));

        final Map<Integer, Integer> territoryIdToChiefIdMap = rotateTerritoryChiefService.getTerritoryToChiefMap(Lists.transform(Lists.newArrayList(teamIdToTerritoryMap.values()), new Function<TerritoryDto, Integer>() {
            @Override
            public Integer apply(TerritoryDto input) {
                return input.getId();
            }
        }));

        List<UserDto> users = userService.queryUserByLoginIDs(Lists.newArrayList(territoryIdToChiefIdMap.values()));

        final Map<Integer, UserDto> chiefIdToChiefMap = Maps.newHashMap();
        for (UserDto user: users) {
            chiefIdToChiefMap.put(user.getLoginId(), user);
        }

        return Lists.transform(teams, new Function<Team, TeamTerritoryDTO>() {
            @Override
            public TeamTerritoryDTO apply(Team input) {
                TeamTerritoryDTO dto = new TeamTerritoryDTO();
                dto.setTeamId(input.getTeamID());
                dto.setTeamName(input.getTeamName());

                TerritoryDto territory = teamIdToTerritoryMap.get(dto.getTeamId());
                if (territory != null) {
                    dto.setTerritoryId(territory.getId());
                    dto.setTerritoryName(territory.getTerritoryName());

                    Integer chiefId = territoryIdToChiefIdMap.get(territory.getId());

                    if (chiefId != null) {
                        UserDto chief = chiefIdToChiefMap.get(chiefId);
                        if (chief != null) {
                            dto.setTerritoryChiefName(chief.getRealName());
                        }
                    }
                }
                return dto;
            }
        });

    }
}
