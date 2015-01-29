package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryTeamServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.org.api.TeamService;
import com.dianping.rotate.org.dto.Team;
import com.dianping.rotate.territory.dto.TerritoryTeamDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by dev_wzhang on 15-1-29.
 */
@Controller
@RequestMapping("/territory")
public class TerritoryTeamController  {

    @Autowired
    private TerritoryTeamServiceAgent territoryTeamServiceAgent;

    private TeamService teamService;

    /**
     * 查询战区里的小组
     *
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryTerritoryTeam", method = RequestMethod.DELETE)
    @ResponseBody
    public Object queryTerritoryTeam(@RequestParam int territoryId) {
        List<TerritoryTeamDto> territoryTeamDtoList = territoryTeamServiceAgent.getTeamByTerritoryId(territoryId);
        return buildTerritoryTeamMap(territoryTeamDtoList);
    }

    private Object buildTerritoryTeamMap(List<TerritoryTeamDto> territoryTeamDtoList){
        //格式:[{id,teamId,teamName,bu,territoryId,cityName},{...}]
        List<Map> result = Lists.newArrayList();
        for(TerritoryTeamDto item : territoryTeamDtoList){
            Map map = Maps.newHashMap() ;
            map.put("teamId",item.getTeamId());
            map.put("territoryId",item.getTerritoryId());
            map.put("teamName",getTeamName(item.getTeamId()));
            map.put("cityName","");
        }
        return result;
    }

    private String getTeamName(Integer teamId){
        Team team = teamService.getTeam(teamId);
        return  team == null? StringUtils.EMPTY:team.getTeamName();
    }

}
