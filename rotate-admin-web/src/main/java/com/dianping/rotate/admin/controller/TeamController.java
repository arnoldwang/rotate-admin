package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.dto.TeamTerritoryDTO;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.service.TeamTerritoryService;
import com.dianping.rotate.smt.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yangjie on 1/27/15.
 */
@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamTerritoryService teamTerritoryService;

    @Autowired
    com.dianping.rotate.territory.api.TeamTerritoryService rotateTeamTerritoryService;

    @RequestMapping(value="/query")
    @ResponseBody
    public List<TeamTerritoryDTO> queryTeamTerritory(@RequestParam("bizId") Integer bizId) {
        return teamTerritoryService.getTeamsByBizId(bizId);
    }

    @RequestMapping(value="/bindTerritory", method = RequestMethod.POST)
    @ResponseBody
    public Boolean bindTerritory(@RequestBody TeamTerritoryDTO dto) {
        Response<Void> r = rotateTeamTerritoryService.bind(dto.getTeamId(), dto.getTerritoryId());
        if (!r.isSuccess()) {
            throw new ApplicationException(r.getComment());
        }

        return true;
    }
}
