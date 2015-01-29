package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.dto.TeamTerritoryDTO;
import com.dianping.rotate.admin.service.TeamTerritoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yangjie on 1/27/15.
 */
@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamTerritoryService teamTerritoryService;

    @RequestMapping(value="/query", method = RequestMethod.GET)
    @ResponseBody
    public List<TeamTerritoryDTO> queryTeamTerritory(@RequestParam("bizId") Integer bizId) {
        return teamTerritoryService.getTeamsByBizId(bizId);
    }
}
